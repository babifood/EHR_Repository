package com.babifood.service.salary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.constant.SalaryConstant;
import com.babifood.dao.CalculationFormulaDao;
import com.babifood.dao.PerformanceDao;
import com.babifood.dao.PersonInFoDao;
import com.babifood.dao.SalaryCalculationDao;
import com.babifood.entity.BaseFieldsEntity;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.SalaryDetailEntity;
import com.babifood.service.InitAttendanceService;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;

@Service
public class PerformanceCalculationService {

	private static Logger logger = Logger.getLogger(PerformanceCalculationService.class);
	
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private PersonInFoDao personInFoDao;

	@Autowired
	private InitAttendanceService initAttendanceService;

	@Autowired
	private SalaryCalculationDao salaryCalculationDao;

	@Autowired
	private PerformanceDao performanceDao;
	
	@Autowired
	private CalculationFormulaDao calculationFormulaDao;
	
	private Map<String, String> formulaList;
	
	private String year;
	
	private String month;
	
	private int days;
	
	private ScriptEngine scriptEngine;
	
	private void init(String cyear, String cmonth) throws Exception{
		formulaList = getFormula();
		if(UtilString.isEmpty(cyear)){
			year = UtilDateTime.getYearOfPreMonth();// 当前年
		} else {
			year = cyear;
		}
		if(UtilString.isEmpty(cmonth)){
			month = UtilDateTime.getPreMonth();// 当前月
		} else {
			month = cmonth;
		}
		days = UtilDateTime.getDaysOfCurrentMonth(Integer.valueOf(year), Integer.valueOf(month));
		scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
		logger.info("绩效薪资计算========>绩效薪资计算年份："+year+",月份："+month);
	}

	@LogMethod(module = ModuleConstant.CALCULATIONSALARY)
	public Map<String, Object> performanceCalculation(Integer type, String resourceCode, String cyear, String cmonth){
		Map<String, Object> result = new HashMap<String, Object>();
		if(UtilString.isEmpty(resourceCode)){
			result.put("code", "2");
			result.put("msg", "请选择所属机构");
			return result;
		}
		String calculationType = type == 1 ? "绩效薪资计算" : "绩效薪资归档";
		try {
			init(cyear, cmonth);
			Subject subject = SecurityUtils.getSubject();
			LoginEntity login = (LoginEntity) subject.getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_CALCULATION);
			Integer currentType = salaryCalculationDao.findSalaryCalculationStatus(year, month, resourceCode,"performance");
			logger.info("绩效薪资计算========>操作人userId："+login.getUser_id()+",用户名："+login.getUser_name()+",currentType:"+currentType+"type:"+type);
			if(type < currentType){
				result.put("code", "2");
				result.put("msg", "绩效已归档，不能重新计算");
				return result;
			}
			if(type == 1){
				Integer count = personInFoDao.getPersonCount(year+"-"+month+"-01", resourceCode);
				final int threadCount = 50;// 每个线程初始化的员工数量
				int num = count % threadCount == 0 ? count / threadCount : count / threadCount + 1;// 线程数
				logger.info("绩效绩效薪资计算========>使用线程数量，num=" + num);
				List<Future<String>> list = new ArrayList<>();
				for (int i = 0; i < num; i++) {
					final int index = i;
					Callable<String> c1 = new Callable<String>() {
						@Override
						public String call() throws Exception {
							ThreadContext.bind(subject);
							String result = calculationEmployeesPerformance(index, threadCount, resourceCode);
							ThreadContext.unbindSubject();
							return result;
						}
					};
					//执行任务并获取Future对象
					Future<String> f1 = threadPoolTaskExecutor.submit(c1);
					list.add(f1);
				}
				for(int i = 0; i < num; i++){
					logger.info("绩效绩效薪资计算========>使用线程编号：i=" + i + ",执行结果：" + list.get(i).get());
				}
			}
			salaryCalculationDao.updateSalaryCalculationStatus(year, month, type, resourceCode, "performance");
			result.put("code", "1");
			result.put("msg", calculationType+"成功");
			LogManager.putContectOfLogInfo(calculationType);
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", calculationType+"失败");
			logger.error("绩效薪资计算失败", e);
			LogManager.putContectOfLogInfo(calculationType+"失败========>错误信息:" + e.getMessage());
		}
		return result;
	}

	/**
	 * 计算薪资-线程
	 * 
	 * @param index
	 * @param threadCount
	 * @param year
	 * @param month
	 * @param days
	 * @return 
	 */
	public String calculationEmployeesPerformance(int index, int threadCount, String resourceCode) {
		List<Map<String, Object>> employeeList = personInFoDao.findPagePersonInfo(index * threadCount ,threadCount, year+"-"+month+"-01", resourceCode);// 查询员工列表
		List<Map<String, Object>> performanceDetails = new ArrayList<Map<String, Object>>();
		if (employeeList != null && employeeList.size() > 0) {
			logger.info("绩效薪资计算========>绩效薪资计算index："+index+",员工数量："+employeeList.size());
			for (Map<String, Object> employee : employeeList) {
				try {
					Map<String, Object> performanceDerail = calculationPerformanceByEmployee(employee);
					performanceDetails.add(performanceDerail);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		performanceDao.savePerformancList(performanceDetails);
		return "计算绩效薪资完成";
	}

	/**
	 * 计算单个员工的薪资
	 * 
	 * @param employee
	 * @param year
	 * @param month
	 * @param days
	 * @throws Exception
	 */
	public Map<String, Object> calculationPerformanceByEmployee(Map<String, Object> employee)throws Exception {
		Map<String, Object> performance = new HashMap<String, Object>();
		String pNumber = employee.get("pNumber") + "";// 员工编号
		logger.info("绩效薪资计算========>员工绩效薪资计算开始，员工编号:"+pNumber);
		// 考勤汇总
		Map<String, Object> arrangementSummary = initAttendanceService.summaryArrangementInfo(year, month, pNumber);
		if (arrangementSummary == null || arrangementSummary.size() <= 0) {
			logger.error("绩效薪资计算========>考勤汇总数据异常, employee:"+employee);
			throw new Exception("考勤信息异常");
		}
		String lastDay = year + "-" + month + "-" + days;// 该月最后一天
		Map<String, Object> salary = salaryCalculationDao.findBaseSalary(lastDay, pNumber);
		if (salary == null || salary.size() <= 0) {
			logger.error("绩效薪资计算========>基本薪资信息异常, employee:"+employee);
			throw new Exception("基本薪资信息异常");
		}
		Map<String, Object> fees = salaryCalculationDao.findPersonFee(year, month, pNumber);
		if (fees == null || fees.size() <= 0) {
			logger.error("绩效薪资计算========>费项数据异常, employee:"+employee);
			throw new Exception("费项数据异常，请检查数据");
		}
		BaseFieldsEntity baseFields = getBaseFields(employee, arrangementSummary, salary, fees);
		performance.put("year", year);
		performance.put("month", month);
		performance.put("pNumber", pNumber);
		performance.put("performanceScore", fees.get("performanceScore"));
		performance.put("performanceSalary", fees.get("pSalary"));
		logger.info("绩效薪资计算========>员工绩效薪资计算:奖金数据计算");
		String performanceFormula = formulaList.get("performanceFormula");
		if(UtilString.isEmpty(performanceFormula)){
			performanceFormula = SalaryConstant.PERFORMANCE_FORMULA;
		}
		if("100932".equals(pNumber)){
			System.out.println();
		}
		performanceFormula = replaceStr(performanceFormula);
		Object realySalary = scriptEngine.eval(replaceBaseFields(performanceFormula, baseFields));// 绩效奖金
		performance.put("realySalary", realySalary);
		return performance;
	}

	/**
	 * 替换基础字段值
	 * @param year
	 * @param month
	 * @param employee
	 * @param arrangementSummary
	 * @param salary
	 * @param allowances
	 * @param baseSetting
	 * @return
	 */
	private BaseFieldsEntity getBaseFields(Map<String, Object> employee, Map<String, Object> arrangementSummary, Map<String, Object> salary,
			Map<String, Object> fees) {
		logger.info("绩效薪资计算========>员工绩效薪资计算:获取计算因子");
		BaseFieldsEntity baseFields = new BaseFieldsEntity();
		//基础数据相关
		baseFields.setpNumber(employee.get("pNumber") + "");
		baseFields.setInDate(UtilString.isEmpty(employee.get("inDate") + "") ? UtilDateTime.getCurrentTime("yyyy-MM-dd")
				: employee.get("inDate") + "");
		baseFields.setNationality(UtilString.isEmpty(employee.get("nationality") + "")?"1":employee.get("nationality") + "");
		baseFields.setProperty(employee.get("property") + "");
		//基础薪资相关
		baseFields.setStay(UtilString.isEmpty(salary.get("stay") + "") ? "0.0"
				: salary.get("stay") + "");
		baseFields.setPostSalary(UtilString.isEmpty(salary.get("postSalary") + "") ? "0.0"
				: salary.get("postSalary") + "");
		baseFields.setCallSubsidies(UtilString.isEmpty(salary.get("callSubsidies") + "") ? "0.0"
				: salary.get("callSubsidies") + "");
		baseFields.setCompanySalary(UtilString.isEmpty(salary.get("companySalary") + "") ? "0.0"
				: salary.get("companySalary") + "");
		baseFields.setWorkType(UtilString.isEmpty(salary.get("workType") + "") ? "0" : salary.get("workType") + "");
		baseFields.setWorkPlace(UtilString.isEmpty(salary.get("workPlace") + "") ? "0" : salary.get("workPlace") + "");
		baseFields.setSingelMeal(UtilString.isEmpty(salary.get("singelMeal") + "") ? "0.0"
				: salary.get("singelMeal") + "");
		baseFields.setBaseSalary(UtilString.isEmpty(salary.get("baseSalary") + "") ? "0.0"
				: salary.get("baseSalary") + "");
		baseFields.setPerformanceSalary(UtilString.isEmpty(salary.get("performanceSalary") + "") ? "0.0"
				: salary.get("performanceSalary") + "");
		baseFields.setFixedOverTimeSalary(UtilString.isEmpty(salary.get("fixedOverTimeSalary") + "") ? "0.0"
				: salary.get("fixedOverTimeSalary") + "");
		//费用相关
		baseFields.setAddOther(UtilString.isEmpty(fees.get("addOther") + "") ? "0.0"
				: fees.get("addOther") + "");
		baseFields.setAfterDeduction(UtilString.isEmpty(fees.get("afterDeduction") + "") ? "0.0"
				: fees.get("afterDeduction") + "");
		baseFields.setBeforeDeduction(UtilString.isEmpty(fees.get("beforeDeduction") + "") ? "0.0"
				: fees.get("beforeDeduction") + "");
		baseFields.setPSalary(UtilString.isEmpty(fees.get("pSalary") + "") ? "-1"
				: fees.get("pSalary") + "");
		baseFields.setCompensatory(UtilString.isEmpty(fees.get("compensatory") + "") ? "0.0"
				: fees.get("compensatory") + "");
		baseFields.setDormDeduction(UtilString.isEmpty(fees.get("dormDeduction") + "") ? "0.0"
				: fees.get("dormDeduction") + "");
		baseFields.setDormFee(UtilString.isEmpty(fees.get("dormFee") + "") ? "0.0"
				: fees.get("dormFee") + "");
		baseFields.setDormBonus(UtilString.isEmpty(fees.get("dormBonus") + "") ? "0.0"
				: fees.get("dormBonus") + "");
		baseFields.setElectricityFee(UtilString.isEmpty(fees.get("electricityFee") + "") ? "0.0"
				: fees.get("electricityFee") + "");
		baseFields.setOverSalary(UtilString.isEmpty(fees.get("overSalary") + "") ? "0.0"
				: fees.get("overSalary") + "");
		baseFields.setHighTem(UtilString.isEmpty(fees.get("highTem") + "") ? "0.0"
				: fees.get("highTem") + "");
		baseFields.setInsurance(UtilString.isEmpty(fees.get("insurance") + "") ? "0.0"
				: fees.get("insurance") + "");
		baseFields.setLowTem(UtilString.isEmpty(fees.get("lowTem") + "") ? "0.0"
				: fees.get("lowTem") + "");
		baseFields.setMealDeduction(UtilString.isEmpty(fees.get("meal") + "") ? "0.0"
				: fees.get("meal") + "");
		baseFields.setMorningShift(UtilString.isEmpty(fees.get("morningShift") + "") ? "0.0"
				: fees.get("morningShift") + "");
		baseFields.setNightShift(UtilString.isEmpty(fees.get("nightShift") + "") ? "0.0"
				: fees.get("nightShift") + "");
		baseFields.setOtherAllowance(UtilString.isEmpty(fees.get("otherAllowance") + "") ? "0.0"
				: fees.get("otherAllowance") + "");
		baseFields.setOtherBonus(UtilString.isEmpty(fees.get("otherBonus") + "") ? "0.0"
				: fees.get("otherBonus") + "");
		baseFields.setPerformanceScore(UtilString.isEmpty(fees.get("performanceScore") + "") ? "0.0"
				: fees.get("performanceScore") + "");
		baseFields.setProvidentFund(UtilString.isEmpty(fees.get("providentFund") + "") ? "0.0"
				: fees.get("providentFund") + "");
		baseFields.setReserved1(UtilString.isEmpty(fees.get("reserved1") + "") ? "0.0"
				: fees.get("reserved1") + "");
		baseFields.setReserved2(UtilString.isEmpty(fees.get("reserved2") + "") ? "0.0"
				: fees.get("reserved2") + "");
		baseFields.setReserved3(UtilString.isEmpty(fees.get("reserved3") + "") ? "0.0"
				: fees.get("reserved3") + "");
		baseFields.setReserved4(UtilString.isEmpty(fees.get("reserved4") + "") ? "0.0"
				: fees.get("reserved4") + "");
		baseFields.setReserved5(UtilString.isEmpty(fees.get("reserved5") + "") ? "0.0"
				: fees.get("reserved5") + "");
		baseFields.setReserved6(UtilString.isEmpty(fees.get("reserved6") + "") ? "0.0"
				: fees.get("reserved6") + "");
		baseFields.setReserved7(UtilString.isEmpty(fees.get("reserved7") + "") ? "0.0"
				: fees.get("reserved7") + "");
		baseFields.setReserved8(UtilString.isEmpty(fees.get("reserved8") + "") ? "0.0"
				: fees.get("reserved8") + "");
		baseFields.setReserved9(UtilString.isEmpty(fees.get("reserved9") + "") ? "0.0"
				: fees.get("reserved9") + "");
		baseFields.setReserved10(UtilString.isEmpty(fees.get("reserved10") + "") ? "0.0"
				: fees.get("reserved10") + "");
		baseFields.setSecurity(UtilString.isEmpty(fees.get("security") + "") ? "0.0"
				: fees.get("security") + "");
		//考勤相关
		baseFields.setAbsenceHours(UtilString.isEmpty(arrangementSummary.get("queqin") + "") ? "0.0"
				: arrangementSummary.get("queqin") + "");
		baseFields.setAttendanceHours(UtilString.isEmpty(arrangementSummary.get("standardWorkLength") + "") ? "1.0"
				: arrangementSummary.get("standardWorkLength") + "");
		baseFields.setLateTime(UtilString.isEmpty(arrangementSummary.get("chiDao") + "") ? "0"
				: arrangementSummary.get("chiDao") + "");
		baseFields.setLeaveTime(UtilString.isEmpty(arrangementSummary.get("zaoTui") + "") ? "0"
				: arrangementSummary.get("zaoTui") + "");
		baseFields.setSickHours(UtilString.isEmpty(arrangementSummary.get("bingJia") + "") ? "0.0"
				: arrangementSummary.get("bingJia") + "");
		baseFields.setCompletionHours(UtilString.isEmpty(arrangementSummary.get("kuangGong") + "") ? "0.0"
				: arrangementSummary.get("kuangGong") + "");
		baseFields.setMealnum(UtilString.isEmpty(arrangementSummary.get("canbu") + "") ? "0.0"
				: arrangementSummary.get("canbu") + "");
		baseFields.setOnboardingHours(UtilString.isEmpty(arrangementSummary.get("onboarding") + "") ? "0.0"
				: arrangementSummary.get("onboarding") + "");
		baseFields.setParentalHours(UtilString.isEmpty(arrangementSummary.get("chanJia") + "") ? "0.0"
				: arrangementSummary.get("chanJia") + "");
		baseFields.setThingHours(UtilString.isEmpty(arrangementSummary.get("shiJia") + "") ? "0.0"
				: arrangementSummary.get("shiJia") + "");
		baseFields.setYearHours(UtilString.isEmpty(arrangementSummary.get("year") + "") ? "0.0"
				: arrangementSummary.get("year") + "");
		baseFields.setTrainHours(UtilString.isEmpty(arrangementSummary.get("peixunJia") + "") ? "0.0"
				: arrangementSummary.get("peixunJia") + "");
		baseFields.setMarriageHours(UtilString.isEmpty(arrangementSummary.get("hunJia") + "") ? "0.0"
				: arrangementSummary.get("hunJia") + "");
		baseFields.setFuneralHours(UtilString.isEmpty(arrangementSummary.get("SangJia") + "") ? "0.0"
				: arrangementSummary.get("SangJia") + "");
		baseFields.setRelaxationHours(UtilString.isEmpty(arrangementSummary.get("tiaoXiu") + "") ? "0.0"
				: arrangementSummary.get("tiaoXiu") + "");
		baseFields.setCompanionParentalHours(UtilString.isEmpty(arrangementSummary.get("PeiChanJia") + "") ? "0.0"
				: arrangementSummary.get("PeiChanJia") + "");
		baseFields.setOtherHours(UtilString.isEmpty(arrangementSummary.get("qita") + "") ? "0.0"
				: arrangementSummary.get("qita") + "");
		//默认参数
//		baseFields.setNonContinent("2");
//		baseFields.setMainLand("1");
//		baseFields.setPartTime("0");
//		baseFields.setFullTime("1");
//		baseFields.setPiming("0");
//		baseFields.setPiece("1");  
		return baseFields;
	}
	
	/**
	 * 获取所有计算公式
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getFormula() throws Exception {
		logger.info("绩效薪资计算========>绩效薪资计算，查询所有计算公式");
		List<Map<String, Object>> formulaList = calculationFormulaDao.findListFormulas();
		if(formulaList == null || formulaList.size() <= 0){
			throw new Exception("数据异常");
		}
		Map<String, String> formulaListMap = new HashMap<String, String>();
		Map<String, List<Map<String, Object>>> formulas = new HashMap<String, List<Map<String, Object>>>();
		for(Map<String, Object> map : formulaList){
			List<Map<String, Object>> list = formulas.get(map.get("key"));
			if(list == null){
				list = new ArrayList<Map<String,Object>>();
			}
			list.add(map);
			formulas.put(map.get("key")+"", list);
		}
		if(formulas.size() > 0){
			for(String key : formulas.keySet()){
				List<Map<String, Object>> list = formulas.get(key);
				String formula = "";
				if(list.size() == 1 || "".equals(list.get(0).get("type") + "")){
					formula = list.get(0).get("formulaUse")+ "";
				} else {
					Collections.sort(list,new Comparator<Map<String, Object>>() {
						@Override
						public int compare(Map<String, Object> o1, Map<String, Object> o2) {
							return Integer.valueOf(o1.get("sort")+"") - Integer.valueOf(o2.get("sort")+"");
						}
					});
					Integer num = list.size() - 1;
					for(int i = 0 ; i < num; i++){
						Map<String,Object> formulaMap = list.get(i);
						formula += "" + formulaMap.get("baseField") + formulaMap.get("symbol") + formulaMap.get("condition") + "?" + formulaMap.get("formulaUse") + ":";
					}
					formula += list.get(num).get("formulaUse");
				}
				formulaListMap.put(key, formula);
				logger.info("绩效薪资计算公式:key="+key+",formula="+formula);
			}
		}
		return formulaListMap;
	}
	
	/**
	 * 替换基础字敦
	 * @param formula
	 * @param baseFields
	 * @return
	 * @throws Exception
	 */
	private String replaceBaseFields(String formula,BaseFieldsEntity baseFields) throws Exception {
//		logger.info("绩效薪资计算========>员工绩效薪资计算:替换基础字敦");
		if(UtilString.isEmpty(formula) || baseFields == null){
			throw new Exception("数据异常");
		}
		if(formula.indexOf("baseSalary") > -1){
			formula = formula.replaceAll("baseSalary", "(" + baseFields.getBaseSalary() + ")");
		}
		if(formula.indexOf("fixedOverTimeSalary") > -1){
			formula = formula.replaceAll("fixedOverTimeSalary", "(" + baseFields.getFixedOverTimeSalary() + ")");
		}
		if(formula.indexOf("companySalary") > -1){
			formula = formula.replaceAll("companySalary", "(" + baseFields.getCompanySalary() + ")");
		}
		if(formula.indexOf("companyType") > -1){
			formula = formula.replaceAll("companyType", "(" + baseFields.getCompanyType() + ")");
		}
		if(formula.indexOf("postSalary") > -1){
			formula = formula.replaceAll("postSalary", "(" + baseFields.getPostSalary() + ")");
		}
		if(formula.indexOf("arrangementType") > -1){
			formula = formula.replaceAll("arrangementType", "(" + baseFields.getArrangementType() + ")");
		}
		if(formula.indexOf("attendanceHours") > -1){
			formula = formula.replaceAll("attendanceHours", "(" + baseFields.getAttendanceHours() + ")");
		}
		if(formula.indexOf("absenceHours") > -1){
			formula = formula.replaceAll("absenceHours", "(" + baseFields.getAbsenceHours() + ")");
		}
		if(formula.indexOf("singelMeal") > -1){
			formula = formula.replaceAll("singelMeal", "(" + baseFields.getSingelMeal() + ")");
		}
		if(formula.indexOf("mealnum") > -1){
			formula = formula.replaceAll("mealnum", "(" + baseFields.getMealnum() + ")");
		}
		if(formula.indexOf("morningShift") > -1){
			formula = formula.replaceAll("morningShift", "(" + baseFields.getMorningShift() + ")");
		}
		if(formula.indexOf("nightShift") > -1){
			formula = formula.replaceAll("nightShift", "(" + baseFields.getNightShift() + ")");
		}
		if(formula.indexOf("stay") > -1){
			formula = formula.replaceAll("stay", "(" + baseFields.getStay() + ")");
		}
		if(formula.indexOf("otherAllowance") > -1){
			formula = formula.replaceAll("otherAllowance", "(" + baseFields.getOtherAllowance() + ")");
		}
		if(formula.indexOf("performanceScore") > -1){
			formula = formula.replaceAll("performanceScore", "(" + baseFields.getPerformanceScore() + ")");
		}
		if(formula.indexOf("performanceSalary") > -1){
			formula = formula.replaceAll("performanceSalary", "(" + baseFields.getPerformanceSalary() + ")");
		}
		if(formula.indexOf("pSalary") > -1){
			formula = formula.replaceAll("pSalary", "(" + baseFields.getPSalary() + ")");
		}
		if(formula.indexOf("security") > -1){
			formula = formula.replaceAll("security", "(" + baseFields.getSecurity() + ")");
		}
		if(formula.indexOf("otherBonus") > -1){
			formula = formula.replaceAll("otherBonus", "(" + baseFields.getOtherBonus() + ")");
		}
		if(formula.indexOf("beforeDeduction") > -1){
			formula = formula.replaceAll("beforeDeduction", "(" + baseFields.getBeforeDeduction() + ")");
		}
		if(formula.indexOf("addOther") > -1){
			formula = formula.replaceAll("addOther", "(" + baseFields.getAddOther() + ")");
		}
		if(formula.indexOf("companyAge") > -1){
			formula = formula.replaceAll("companyAge", "(" + baseFields.getCompanyAge() + ")");
		}
		if(formula.indexOf("sickGrandHours") > -1){
			formula = formula.replaceAll("sickGrandHours", "(" + baseFields.getSickGrandHours() + ")");
		}
		if(formula.indexOf("mealDeduction") > -1){
			formula = formula.replaceAll("mealDeduction", "(" + baseFields.getMealDeduction() + ")");
		}
		if(formula.indexOf("dormDeduction") > -1){
			formula = formula.replaceAll("dormDeduction", "(" + baseFields.getDormDeduction() + ")");
		}
		if(formula.indexOf("dormFee") > -1){
			formula = formula.replaceAll("dormFee", "(" + baseFields.getDormFee() + ")");
		}
		if(formula.indexOf("electricityFee") > -1){
			formula = formula.replaceAll("electricityFee", "(" + baseFields.getElectricityFee() + ")");
		}
		if(formula.indexOf("dormBonus") > -1){
			formula = formula.replaceAll("dormBonus", "(" + baseFields.getDormBonus() + ")");
		}
		if(formula.indexOf("insurance") > -1){
			formula = formula.replaceAll("insurance", "(" + baseFields.getInsurance() + ")");
		}
		if(formula.indexOf("providentFund") > -1){
			formula = formula.replaceAll("providentFund", "(" + baseFields.getProvidentFund() + ")");
		}
		if(formula.indexOf("sickHours") > -1){
			formula = formula.replaceAll("sickHours", "(" + baseFields.getSickHours() + ")");
		}
		if(formula.indexOf("parentalHours") > -1){
			formula = formula.replaceAll("parentalHours", "(" + baseFields.getParentalHours() + ")");
		}
		if(formula.indexOf("completionHours") > -1){
			formula = formula.replaceAll("completionHours", "(" + baseFields.getCompletionHours() + ")");
		}
		if(formula.indexOf("onboardingHours") > -1){
			formula = formula.replaceAll("onboardingHours", "(" + baseFields.getOnboardingHours() + ")");
		}
		if(formula.indexOf("overSalary") > -1){
			formula = formula.replaceAll("overSalary", "(" + baseFields.getOverSalary() + ")");
		}
		if(formula.indexOf("thingHours") > -1){
			formula = formula.replaceAll("thingHours", "(" + baseFields.getThingHours() + ")");
		}
		if(formula.indexOf("yearHours") > -1){
			formula = formula.replaceAll("yearHours", "(" + baseFields.getYearHours() + ")");
		}
		if(formula.indexOf("trainHours") > -1){
			formula = formula.replaceAll("trainHours", "(" + baseFields.getTrainHours() + ")");
		}
		if(formula.indexOf("marriageHours") > -1){
			formula = formula.replaceAll("marriageHours", "(" + baseFields.getMarriageHours() + ")");
		}
		if(formula.indexOf("funeralHours") > -1){
			formula = formula.replaceAll("funeralHours", "(" + baseFields.getFuneralHours() + ")");
		}
		if(formula.indexOf("relaxationHours") > -1){
			formula = formula.replaceAll("relaxationHours", "(" + baseFields.getRelaxationHours() + ")");
		}
		if(formula.indexOf("companionParentalHours") > -1){
			formula = formula.replaceAll("companionParentalHours", "(" + baseFields.getCompanionParentalHours() + ")");
		}
		if(formula.indexOf("otherHours") > -1){
			formula = formula.replaceAll("otherHours", "(" + baseFields.getOtherHours() + ")");
		}
		if(formula.indexOf("afterDeduction") > -1){
			formula = formula.replaceAll("afterDeduction", "(" + baseFields.getAfterDeduction() + ")");
		}
		if(formula.indexOf("lateTime") > -1){
			formula = formula.replaceAll("lateTime", "(" + baseFields.getLateTime() + ")");
		}
		if(formula.indexOf("leaveTime") > -1){
			formula = formula.replaceAll("leaveTime", "(" + baseFields.getLeaveTime() + ")");
		}
		if(formula.indexOf("compensatory") > -1){
			formula = formula.replaceAll("compensatory", "(" + baseFields.getCompensatory() + ")");
		}
		if(formula.indexOf("highTem") > -1){
			formula = formula.replaceAll("highTem", "(" + baseFields.getHighTem() + ")");
		}
		if(formula.indexOf("lowTem") > -1){
			formula = formula.replaceAll("lowTem", "(" + baseFields.getLowTem() + ")");
		}
		if(formula.indexOf("workType") > -1){
			formula = formula.replaceAll("workType", "(" + baseFields.getWorkType() + ")");
		}
		if(formula.indexOf("workPlace") > -1){
			formula = formula.replaceAll("workPlace", "(" + baseFields.getWorkPlace() + ")");
		}
		if(formula.indexOf("property") > -1){
			formula = formula.replaceAll("property", "(" + baseFields.getProperty() + ")");
		}
		if(formula.indexOf("nationality") > -1){
			formula = formula.replaceAll("nationality", "(" + baseFields.getNationality() + ")");
		}
//		if(formula.indexOf("partTime") > -1){
//			formula = formula.replaceAll("partTime", "(" + baseFields.getPartTime() + ")");
//		}
		if(formula.indexOf("callSubsidies") > -1){
			formula = formula.replaceAll("callSubsidies", "(" + baseFields.getCallSubsidies() + ")");
		}
		if(formula.indexOf("reserved1") > -1){
			formula = formula.replaceAll("reserved1", "(" + baseFields.getReserved1() + ")");
		}
		if(formula.indexOf("reserved2") > -1){
			formula = formula.replaceAll("reserved2", "(" + baseFields.getReserved2() + ")");
		}
		if(formula.indexOf("reserved3") > -1){
			formula = formula.replaceAll("reserved3", "(" + baseFields.getReserved3() + ")");
		}
		if(formula.indexOf("reserved4") > -1){
			formula = formula.replaceAll("reserved4", "(" + baseFields.getReserved4() + ")");
		}
		if(formula.indexOf("reserved5") > -1){
			formula = formula.replaceAll("reserved5", "(" + baseFields.getReserved5() + ")");
		}
		if(formula.indexOf("reserved6") > -1){
			formula = formula.replaceAll("reserved6", "(" + baseFields.getReserved6() + ")");
		}
		if(formula.indexOf("reserved7") > -1){
			formula = formula.replaceAll("reserved7", "(" + baseFields.getReserved7() + ")");
		}
		if(formula.indexOf("reserved8") > -1){
			formula = formula.replaceAll("reserved8", "(" + baseFields.getReserved8() + ")");
		}
		if(formula.indexOf("reserved9") > -1){
			formula = formula.replaceAll("reserved9", "(" + baseFields.getReserved9() + ")");
		}
		if(formula.indexOf("reserved10") > -1){
			formula = formula.replaceAll("reserved10", "(" + baseFields.getReserved10() + ")");
		}
		if(formula.indexOf("partTime") > -1){
			formula = formula.replaceAll("partTime", "0");
		}
		if(formula.indexOf("fullTime") > -1){
			formula = formula.replaceAll("fullTime", "0");
		}
		if(formula.indexOf("mainLand") > -1){
			formula = formula.replaceAll("mainLand", "1");
		}
		if(formula.indexOf("nonContinent") > -1){
			formula = formula.replaceAll("nonContinent", "2");
		}
		if(formula.indexOf("piece") > -1){
			formula = formula.replaceAll("piece", "1");
		}
		if(formula.indexOf("piming") > -1){
			formula = formula.replaceAll("piming", "0");
		}
		return formula;
	}
	
	private String replaceStr(String formula) throws Exception {
		Set<String> keys = formulaList.keySet();
		for(String key:keys){
			if(formula.indexOf(key) > -1){
				formula = formula.replaceAll(key, "("+ formulaList.get(key) +")");
				formula = replaceStr(formula);
				break;
			}
		}
		return formula;
	}
	
}
