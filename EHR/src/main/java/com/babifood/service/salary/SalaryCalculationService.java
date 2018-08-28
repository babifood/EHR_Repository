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
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.babifood.constant.SalaryConstant;
import com.babifood.dao.CalculationFormulaDao;
import com.babifood.dao.PerformanceDao;
import com.babifood.dao.SalaryCalculationDao;
import com.babifood.entity.BaseFieldsEntity;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.SalaryDetailEntity;
import com.babifood.service.AllowanceService;
import com.babifood.service.InitAttendanceService;
import com.babifood.service.PersonInFoService;
import com.babifood.service.SalaryDetailService;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;

@Service
public class SalaryCalculationService {

	private static Logger logger = Logger.getLogger(SalaryCalculationService.class);
	
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private PersonInFoService personInfoService;

	@Autowired
	private InitAttendanceService initAttendanceService;

	@Autowired
	private SalaryCalculationDao salaryCalculationDao;

	@Autowired
	private AllowanceService allowanceService;

	@Autowired
	private SalaryDetailService salaryDetailService;

	@Autowired
	private CalculationFormulaDao calculationFormulaDao;
	
	@Autowired
	private PerformanceDao performanceDao;
	
	private Map<String, String> formulaList;
	
	private String year;
	
	private String month;
	
	private int days;
	
	private ScriptEngine scriptEngine;
	
	private void init() throws Exception{
		formulaList = getFormula();
		year = UtilDateTime.getCurrentYear();
//			UtilDateTime.getYearOfPreMonth();// 当前年
		month = UtilDateTime.getCurrentMonth(); 
//			UtilDateTime.getPreMonth();// 当前月
		days = UtilDateTime.getDaysOfCurrentMonth(Integer.valueOf(year), Integer.valueOf(month));
		scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
		logger.info("薪资计算年份："+year+",月份："+month);
	}

	public Map<String, Object> salaryCalculation(Integer type){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			init();
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			Integer currentType = salaryCalculationDao.findSalaryCalculationStatus(year, month);
			logger.info("操作人userId："+login.getUser_id()+",用户名："+login.getUser_name()+",currentType:"+currentType+"type:"+type);
			if(type < currentType || (type == 3 && (currentType == 1 || currentType == 3))){
				return getSalaryCalculation(type, currentType);
			}
			if(type < 3){
				Integer count = personInfoService.getPersonCount();
				final int threadCount = 200;// 每个线程初始化的员工数量
				int num = count % threadCount == 0 ? count / threadCount : count / threadCount + 1;// 线程数
				List<Future<Boolean>> list = new ArrayList<>();
				for (int i = 0; i < num; i++) {
					final int index = i;
					Callable<Boolean> c1 = new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return calculationEmployeesSalary(index, threadCount);
						}
					};
					//执行任务并获取Future对象
					Future<Boolean> f1 = threadPoolTaskExecutor.submit(c1);
					list.add(f1);
				}
				for(int i = 0; i < num; i++){
					System.out.println(list.get(i).get());
				}
			}
			salaryCalculationDao.updateSalaryCalculationStatus(year, month, type);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
		}
		return result;
	}

	private Map<String, Object> getSalaryCalculation(Integer type, Integer currentType) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "2");
		if(type == 1 && currentType == 2){
			result.put("msg", "该月薪资已核算，不能重新试算");
		} else if (type == 1 && currentType == 3) {
			result.put("msg", "该月薪资已归档，不能重新试算");
		} else if (type == 2 && currentType == 3) {
			result.put("msg", "该月薪资已归档，不能重新核算");
		} else if (type == 3 && currentType == 3) {
			result.put("msg", "该月薪资已归档，不能重复归档");
		} else if (type == 3 && currentType <= 1) {
			result.put("msg", "该月薪资未核算，不能归档");
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
	public Boolean calculationEmployeesSalary(int index, int threadCount) {
		List<Map<String, Object>> employeeList = personInfoService.findPagePersonInfo(index, threadCount);// 查询员工列表
		if (employeeList != null && employeeList.size() > 0) {
			logger.info("薪资计算index："+index+",员工数量："+employeeList.size());
			for (Map<String, Object> employee : employeeList) {
				try {
					calculationSalaryByEmployee(employee);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
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
	public void calculationSalaryByEmployee(Map<String, Object> employee)throws Exception {
		String pNumber = employee.get("pNumber") + "";// 员工编号
		logger.info("员工薪资计算开始，员工编号:"+pNumber);
		if(formulaList == null || formulaList.size() <= 0){
			formulaList = getFormula();
		}
		// 考勤汇总
		Map<String, Object> arrangementSummary = initAttendanceService.summaryArrangementInfo(year, month, pNumber);
		if (arrangementSummary == null || arrangementSummary.size() <= 0) {
			// log
			throw new Exception("考勤信息异常");
		}
		String lastDay = year + "-" + month + "-" + days;// 该月最后一天
		Map<String, Object> salary = salaryCalculationDao.findBaseSalary(lastDay, pNumber);
		if (salary == null || salary.size() <= 0) {
			throw new Exception("基本薪资信息异常");
		}
		Map<String, Object> allowances = allowanceService.findEmployAllowance(year, month, pNumber);
		if (allowances == null || allowances.size() <= 0) {
			allowances = new HashMap<String, Object>();
		}
		Map<String, Object> performanceInfo = performanceDao.getPerformanceInfo(year, month, pNumber);
		if(performanceInfo == null){
			performanceInfo = new HashMap<String, Object>();
		}
		BaseFieldsEntity baseFields = getBaseFields(employee, arrangementSummary, salary, allowances, performanceInfo);
		
		SalaryDetailEntity salaryDerail = new SalaryDetailEntity();

		salaryDerail.setpNumber(pNumber);// 员工工号
		salaryDerail.setYear(year);
		salaryDerail.setMonth(month);
		// 基础信息
		setBaseInfo(salaryDerail, employee);
		// 基础薪资信息
		setBaseSalaryInfo(salaryDerail, baseFields);
		
		// 考勤相关信息
		setInfoAboutAttendance(salaryDerail, baseFields);
		// 计算各类补贴
		calculationAllowance(salaryDerail, baseFields);
		// 计算各类奖金
		calculationBonus(salaryDerail, baseFields);
		// 计算各类扣款
		calculationDeduction(salaryDerail, baseFields);
		// 计算薪资
		calculationSalary(salaryDerail, baseFields);

		salaryDetailService.saveCurrentMonthSalary(salaryDerail);
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
			Map<String, Object> allowances, Map<String, Object> performanceInfo) {
		logger.info("员工薪资计算:获取计算因子");
		String pNumber = employee.get("pNumber") + "";// 员工编号
		BaseFieldsEntity baseFields = new BaseFieldsEntity();
		Double sickGrandHours = initAttendanceService.findYearSickHours(year, month, pNumber);
		baseFields.setAbsenceHours(UtilString.isEmpty(arrangementSummary.get("queqin") + "") ? "0.0"
				: arrangementSummary.get("queqin") + "");
		baseFields.setAddOther(UtilString.isEmpty(allowances.get("addOther") + "") ? "0.0"
				: allowances.get("addOther") + "");
		baseFields.setAfterDeduction(UtilString.isEmpty(allowances.get("afterDeduction") + "") ? "0.0"
				: allowances.get("afterDeduction") + "");
		baseFields.setAttendanceHours(UtilString.isEmpty(arrangementSummary.get("standardWorkLength") + "") ? "1.0"
				: arrangementSummary.get("standardWorkLength") + "");
		baseFields.setBaseSalary(UtilString.isEmpty(salary.get("baseSalary") + "") ? "0.0"
				: salary.get("baseSalary") + "");
		baseFields.setCompanyAge(UtilString.isEmpty(employee.get("companyAge") + "") ? "0"
				: employee.get("companyAge") + "");
		baseFields.setBeforeDeduction(UtilString.isEmpty(allowances.get("beforeDeduction") + "") ? "0.0"
				: allowances.get("beforeDeduction") + "");
		baseFields.setCallSubsidies(UtilString.isEmpty(salary.get("callSubsidies") + "") ? "0.0"
				: salary.get("callSubsidies") + "");
		baseFields.setCompanySalary(UtilString.isEmpty(salary.get("companySalary") + "") ? "0.0"
				: salary.get("companySalary") + "");
		baseFields.setCompensatory(UtilString.isEmpty(allowances.get("compensatory") + "") ? "0.0"
				: allowances.get("compensatory") + "");
		baseFields.setCompletionHours(UtilString.isEmpty(arrangementSummary.get("kuangGong") + "") ? "0.0"
				: arrangementSummary.get("kuangGong") + "");
		baseFields.setDormDeduction(UtilString.isEmpty(allowances.get("dorm") + "") ? "0.0"
				: allowances.get("dorm") + "");
		baseFields.setFixedOverTimeSalary(UtilString.isEmpty(salary.get("fixedOverTimeSalary") + "") ? "0.0"
				: salary.get("fixedOverTimeSalary") + "");
		baseFields.setOverSalary(UtilString.isEmpty(allowances.get("overSalary") + "") ? "0.0"
				: allowances.get("overSalary") + "");
		baseFields.setFullTime("1");
		baseFields.setHighTem(UtilString.isEmpty(allowances.get("highTem") + "") ? "0.0"
				: allowances.get("highTem") + "");
		baseFields.setInsurance(UtilString.isEmpty(allowances.get("insurance") + "") ? "0.0"
				: allowances.get("insurance") + "");
		baseFields.setLateTime(UtilString.isEmpty(arrangementSummary.get("chiDao") + "") ? "0"
				: arrangementSummary.get("chiDao") + "");
		baseFields.setLeaveTime(UtilString.isEmpty(arrangementSummary.get("zaoTui") + "") ? "0"
				: arrangementSummary.get("zaoTui") + "");
		baseFields.setLowTem(UtilString.isEmpty(allowances.get("lowTem") + "") ? "0.0"
				: allowances.get("lowTem") + "");
		baseFields.setMainLand("1");
		baseFields.setMealDeduction(UtilString.isEmpty(allowances.get("meal") + "") ? "0.0"
				: allowances.get("meal") + "");
		baseFields.setMealnum(UtilString.isEmpty(arrangementSummary.get("canbu") + "") ? "0.0"
				: arrangementSummary.get("canbu") + "");
		baseFields.setMorningShift(UtilString.isEmpty(allowances.get("morningShift") + "") ? "0.0"
				: allowances.get("morningShift") + "");
		baseFields.setNationality(UtilString.isEmpty(employee.get("nationality") + "")?"1":employee.get("nationality") + "");
		baseFields.setNightShift(UtilString.isEmpty(allowances.get("nightShift") + "") ? "0.0"
				: allowances.get("nightShift") + "");
		baseFields.setNonContinent("2");
		baseFields.setOnboardingHours(UtilString.isEmpty(arrangementSummary.get("onboarding") + "") ? "0.0"
				: arrangementSummary.get("onboarding") + "");
		baseFields.setOtherAllowance(UtilString.isEmpty(allowances.get("otherAllowance") + "") ? "0.0"
				: allowances.get("otherAllowance") + "");
		baseFields.setOtherBonus(UtilString.isEmpty(allowances.get("otherBonus") + "") ? "0.0"
				: allowances.get("otherBonus") + "");
//		baseFields.setOverTimeSalary(overTimeSalary);
		baseFields.setParentalHours(UtilString.isEmpty(arrangementSummary.get("chanJia") + "") ? "0.0"
				: arrangementSummary.get("chanJia") + "");
		baseFields.setPartTime("0");
		baseFields.setPerformanceScore(UtilString.isEmpty(performanceInfo.get("performanceScore") + "") ? "0.0"
				: performanceInfo.get("performanceScore") + "");
		baseFields.setPerformanceSalary(UtilString.isEmpty(salary.get("performanceSalary") + "") ? "0.0"
				: salary.get("performanceSalary") + "");
		baseFields.setPiece("1");
		baseFields.setPiming("0");
		baseFields.setPostSalary(UtilString.isEmpty(salary.get("postSalary") + "") ? "0.0"
				: salary.get("postSalary") + "");
		baseFields.setProperty(employee.get("property") + "");
		baseFields.setProvidentFund(UtilString.isEmpty(allowances.get("providentFund") + "") ? "0.0"
				: allowances.get("providentFund") + "");
		baseFields.setReserved1(UtilString.isEmpty(allowances.get("reserved1") + "") ? "0.0"
				: allowances.get("reserved1") + "");
		baseFields.setReserved2(UtilString.isEmpty(allowances.get("reserved2") + "") ? "0.0"
				: allowances.get("reserved2") + "");
		baseFields.setReserved3(UtilString.isEmpty(allowances.get("reserved3") + "") ? "0.0"
				: allowances.get("reserved3") + "");
		baseFields.setReserved4(UtilString.isEmpty(allowances.get("reserved4") + "") ? "0.0"
				: allowances.get("reserved4") + "");
		baseFields.setReserved5(UtilString.isEmpty(allowances.get("reserved5") + "") ? "0.0"
				: allowances.get("reserved5") + "");
		baseFields.setReserved6(UtilString.isEmpty(allowances.get("reserved6") + "") ? "0.0"
				: allowances.get("reserved6") + "");
		baseFields.setReserved7(UtilString.isEmpty(allowances.get("reserved7") + "") ? "0.0"
				: allowances.get("reserved7") + "");
		baseFields.setReserved8(UtilString.isEmpty(allowances.get("reserved8") + "") ? "0.0"
				: allowances.get("reserved8") + "");
		baseFields.setReserved9(UtilString.isEmpty(allowances.get("reserved9") + "") ? "0.0"
				: allowances.get("reserved9") + "");
		baseFields.setReserved10(UtilString.isEmpty(allowances.get("reserved10") + "") ? "0.0"
				: allowances.get("reserved10") + "");
		baseFields.setSecurity(UtilString.isEmpty(allowances.get("security") + "") ? "0.0"
				: allowances.get("security") + "");
		baseFields.setSickGrandHours(sickGrandHours + "");
		baseFields.setSickHours(UtilString.isEmpty(arrangementSummary.get("bingJia") + "") ? "0.0"
				: arrangementSummary.get("bingJia") + "");
		baseFields.setSingelMeal(UtilString.isEmpty(salary.get("singelMeal") + "") ? "0.0"
				: salary.get("singelMeal") + "");
		baseFields.setStay(UtilString.isEmpty(salary.get("stay") + "") ? "0.0"
				: salary.get("stay") + "");
		baseFields.setThingHours(UtilString.isEmpty(arrangementSummary.get("shiJia") + "") ? "0.0"
				: arrangementSummary.get("shiJia") + "");
		baseFields.setWorkType(UtilString.isEmpty(salary.get("workType") + "") ? "0" : salary.get("workType") + "");
		baseFields.setWorkPlace(UtilString.isEmpty(salary.get("workPlace") + "") ? "0" : salary.get("workPlace") + "");
		return baseFields;
	}

	/**
	 * 计算薪资
	 * 
	 * @param salaryDerail
	 * @param baseFields 
	 * @param employee
	 * @param workPlace
	 * @throws Exception 
	 * @throws ScriptException 
	 */
	private void calculationSalary(SalaryDetailEntity salaryDerail, BaseFieldsEntity baseFields) throws Exception {
		logger.info("员工薪资计算:计算个税，应发工资/实发工资");
		String wagePayableFormula = formulaList.get("wagePayableFormula");
		String personalTaxFormula = formulaList.get("personalTaxFormula");
		String realWagesFormula = formulaList.get("realWagesFormula");
		if(UtilString.isEmpty(wagePayableFormula)){
			wagePayableFormula = SalaryConstant.WAGE_PAYABLE_FORMULA;
		}
		if(UtilString.isEmpty(personalTaxFormula)){
			personalTaxFormula = SalaryConstant.PERSONAL_TAX_FORMULA;
		}
		if(UtilString.isEmpty(realWagesFormula)){
			realWagesFormula = SalaryConstant.REAL_WAGES_FORMULA;
		}
		salaryDerail.setWagePayable(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(wagePayableFormula, salaryDerail, baseFields, formulaList))+""));// 应发工资
		salaryDerail.setPersonalTax(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(personalTaxFormula, salaryDerail, baseFields, formulaList))+""));// 个税
		salaryDerail.setRealWages(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(realWagesFormula, salaryDerail, baseFields, formulaList))+""));// 实发工资
	}

	/**
	 * 计算各类扣款
	 * 
	 * @param salaryDerail
	 * @param allowances
	 */
	private void calculationDeduction(SalaryDetailEntity salaryDerail, BaseFieldsEntity baseFields) {
		logger.info("员工薪资计算:扣款数据计算");
		salaryDerail.setMealDeduction(BASE64Util.getStringTowDecimal(baseFields.getMealDeduction()));// 餐费扣款
		salaryDerail.setDormDeduction(BASE64Util.getStringTowDecimal(baseFields.getDormDeduction()));// 住宿扣款
		salaryDerail.setBeforeDeduction(BASE64Util.getStringTowDecimal(baseFields.getBeforeDeduction()));// 税前其它扣款
		salaryDerail.setInsurance(BASE64Util.getStringTowDecimal(baseFields.getInsurance()));// 社保扣款
		salaryDerail.setProvidentFund(BASE64Util.getStringTowDecimal(baseFields.getProvidentFund()));// 公积金扣款
		salaryDerail.setAfterDeduction(BASE64Util.getStringTowDecimal(baseFields.getAfterDeduction()));// 其它扣款（税后）
	}

	/**
	 * 计算各类奖金
	 * 
	 * @param salaryDerail
	 * @param allowances
	 */
	private void calculationBonus(SalaryDetailEntity salaryDerail, BaseFieldsEntity baseFields) throws Exception {
		logger.info("员工薪资计算:奖金数据计算");
		String performanceFormula = formulaList.get("performanceFormula");
		if(UtilString.isEmpty(performanceFormula)){
			performanceFormula = SalaryConstant.PERFORMANCE_FORMULA;
		}
		salaryDerail.setPerformanceBonus(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(performanceFormula, salaryDerail, baseFields, formulaList))+""));// 绩效奖金
		salaryDerail.setSecurity(BASE64Util.getStringTowDecimal(baseFields.getSecurity()));// 安全奖
		salaryDerail.setCompensatory(BASE64Util.getStringTowDecimal(baseFields.getCompensatory()));// 礼金、补偿金
		salaryDerail.setOtherBonus(BASE64Util.getStringTowDecimal(baseFields.getOtherBonus()));// 其它奖金
		salaryDerail.setAddOther(BASE64Util.getStringTowDecimal(baseFields.getAddOther()));// 加其它
	}

	/**
	 * 计算补贴
	 * 
	 * @param salaryDerail
	 * @param formulaList 
	 * @param salary
	 * @param arrangementSummary
	 * @throws Exception 
	 * @throws  
	 */
	private void calculationAllowance(SalaryDetailEntity salaryDerail, BaseFieldsEntity baseFields) throws Exception {
		logger.info("员工薪资计算:补贴数据计算");
		String riceStickFormula = formulaList.get("riceStickFormula");
		String callSunSalaryFormula = formulaList.get("callSunSalaryFormula");
		String highTemFormula = formulaList.get("highTemFormula");
		String lowTemFormula = formulaList.get("lowTemFormula");
		if(UtilString.isEmpty(riceStickFormula)){
			riceStickFormula = SalaryConstant.RICESTICK_ALLOWANCE_FORMULA;
		}
		if(UtilString.isEmpty(callSunSalaryFormula)){
			callSunSalaryFormula = SalaryConstant.CALLSUNSALARY_ALLOWANCE_FORMULA;
		}
		if(UtilString.isEmpty(highTemFormula)){
			highTemFormula = SalaryConstant.HIGHTEM_ALLOWANCE_FORMULA;
		}
		if(UtilString.isEmpty(lowTemFormula)){
			lowTemFormula = SalaryConstant.LOWTEM_ALLOWANCE_FORMULA;
		}
		salaryDerail.setRiceStick(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(riceStickFormula, salaryDerail, baseFields, formulaList))+""));// 餐补
		salaryDerail.setCallSubsidies(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(callSunSalaryFormula, salaryDerail, baseFields, formulaList))+""));// 话费补贴
		salaryDerail.setHighTem(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(highTemFormula, salaryDerail, baseFields, formulaList))+""));// 高温补贴
		salaryDerail.setLowTem(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(lowTemFormula, salaryDerail, baseFields, formulaList))+""));// 低温补贴
		salaryDerail.setMorningShift(BASE64Util.getStringTowDecimal(baseFields.getMorningShift()));// 早班津贴
		salaryDerail.setNightShift(BASE64Util.getStringTowDecimal(baseFields.getNightShift()));// 夜班津贴
		salaryDerail.setStay(BASE64Util.getStringTowDecimal(baseFields.getStay()));// 住宿补贴
		salaryDerail.setOtherAllowance(BASE64Util.getStringTowDecimal(baseFields.getOtherAllowance()));// 其它津贴
	}

	/**
	 * 考勤相关信息
	 * 
	 * @param salaryDerail
	 * @param formulaList 
	 * @param scriptEngine 
	 * @param arrangementSummary
	 * @throws ScriptException 
	 */
	private void setInfoAboutAttendance(SalaryDetailEntity salaryDerail, BaseFieldsEntity baseFields) throws Exception {
		logger.info("员工薪资计算:考勤数据计算");
		String laterAndLeaveFormula = formulaList.get("laterAndLeaveFormula");
		String completionFormula = formulaList.get("completionFormula");
		String thingFormula = formulaList.get("thingFormula");
		String sickFormula = formulaList.get("sickFormula");
		String parentalFormula = formulaList.get("parentalFormula");
		String onboardingFormula = formulaList.get("onboardingFormula");
		String totalFormula = formulaList.get("totalFormula");
		if(UtilString.isEmpty(laterAndLeaveFormula)){
			laterAndLeaveFormula = SalaryConstant.LATER_AND_LEAVE_FORMULA;
		}
		if(UtilString.isEmpty(completionFormula)){
			completionFormula = SalaryConstant.COMPLETION_DEDUCTION_FORMULA;
		}
		if(UtilString.isEmpty(thingFormula)){
			thingFormula = SalaryConstant.THING_DEDUCTION_FORMULA;
		}
		if(UtilString.isEmpty(sickFormula)){
			sickFormula = SalaryConstant.SICK_DEDUCTION_FORMULA;
		}
		if(UtilString.isEmpty(parentalFormula)){
			parentalFormula = SalaryConstant.PARENTAL_DEDUCTION_FORMULA;
		}
		if(UtilString.isEmpty(onboardingFormula)){
			onboardingFormula = SalaryConstant.ONBOARDING_DEDUCTION_FORMULA;
		}
		if(UtilString.isEmpty(totalFormula)){
			totalFormula = SalaryConstant.TOTAL_DEDUCTION_FORMULA;
		}
		salaryDerail.setLaterAndLeaveDeduction(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(laterAndLeaveFormula, salaryDerail, baseFields, formulaList))+""));// 迟到早退扣除薪资
		salaryDerail.setCompletionDeduction(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(completionFormula, salaryDerail, baseFields, formulaList))+""));// 旷工扣除
		salaryDerail.setAttendanceHours(BASE64Util.getStringTowDecimal(baseFields.getAttendanceHours()));// 应出勤小时数
		salaryDerail.setAbsenceHours(BASE64Util.getStringTowDecimal(baseFields.getAbsenceHours()));// 缺勤小时数
		salaryDerail.setOverSalary(BASE64Util.getStringTowDecimal(baseFields.getOverSalary()));//加班费
		salaryDerail.setThingDeduction(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(thingFormula, salaryDerail, baseFields, formulaList))+""));// 事假
		salaryDerail.setSickDeduction(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(sickFormula, salaryDerail, baseFields, formulaList))+""));// 病假
		salaryDerail.setYearDeduction(BASE64Util.getStringTowDecimal("0"));// 年假
		salaryDerail.setMarriageDeduction(BASE64Util.getStringTowDecimal("0"));// 婚假
		salaryDerail.setFuneralDeduction(BASE64Util.getStringTowDecimal("0"));// 丧假
		salaryDerail.setRelaxation(BASE64Util.getStringTowDecimal("0"));// 调休
		salaryDerail.setTrainDeduction(BASE64Util.getStringTowDecimal("0"));// 培训假
		salaryDerail.setCompanionParentalDeduction(BASE64Util.getStringTowDecimal("0"));// 陪产假
		salaryDerail.setParentalDeduction(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(parentalFormula, salaryDerail, baseFields, formulaList))+""));// 产假
		salaryDerail.setOnboarding(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(onboardingFormula, salaryDerail, baseFields, formulaList))+""));// 月中入职、离职导致缺勤
		salaryDerail.setTotalDeduction(BASE64Util.getStringTowDecimal(scriptEngine.eval(replaceFields(totalFormula, salaryDerail, baseFields, formulaList))+""));// 应扣合计
	}

	/**
	 * 基础薪资信息
	 * 
	 * @param salaryDerail
	 * @param salary
	 */
	private void setBaseSalaryInfo(SalaryDetailEntity salaryDerail, BaseFieldsEntity baseFields) {
		logger.info("员工薪资计算:基础薪资信息");
		salaryDerail.setBaseSalary(BASE64Util.getStringTowDecimal(baseFields.getBaseSalary()));// 基本薪资
		salaryDerail.setFixedOvertimeSalary(BASE64Util.getStringTowDecimal(baseFields.getFixedOverTimeSalary()));// 固定加班费
		salaryDerail.setCompanySalary(BASE64Util.getStringTowDecimal(baseFields.getCompanySalary()));// 司龄工资
		salaryDerail.setPostSalary(BASE64Util.getStringTowDecimal(baseFields.getPostSalary()));// 岗位工资
	}

	/**
	 * 基础信息(组织机构)
	 * 
	 * @param salaryDerail
	 * @param employee
	 */
	private void setBaseInfo(SalaryDetailEntity salaryDerail, Map<String, Object> employee) {
		logger.info("员工薪资计算:基础信息部分");
		salaryDerail.setCompanyCode(employee.get("companyCode") + "");// 公司编号
		salaryDerail.setCompanyName(employee.get("companyName") + "");// 公司名称
		salaryDerail.setOrganizationCode(employee.get("organizationCode") + "");// 组织机构编号
		salaryDerail.setOrganizationName(employee.get("organizationName") + "");// 组织机构名称
		salaryDerail.setDeptCode(employee.get("deptCode") + "");// 部门编号
		salaryDerail.setDeptName(employee.get("deptName") + "");// 部门名称
		salaryDerail.setOfficeCode(employee.get("officeCode") + "");// 科室编号
		salaryDerail.setOfficeName(employee.get("officeName") + "");// 科室名称
		// salaryDerail.setGroupCode(employee.get("")+"");
		// salaryDerail.setGroupName(employee.get("")+"");
		salaryDerail.setPost(employee.get("positionId") + "");// 岗位编号
		salaryDerail.setPostName(employee.get("postName") + "");// 岗位名称
	}

	/**
	 * 获取所有计算公式
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getFormula() throws Exception {
		logger.info("薪资计算，查询所有计算公式");
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
				logger.info("薪资计算公式:key="+key+",formula="+formula);
			}
		}
//		if(formulaListMap.size() > 0){
//			Set<String> keys = formulaListMap.keySet();
//			for(String key:keys){
//				String value = formulaListMap.get(key);
//				value = replaceStr(value, formulaListMap);
//				formulaListMap.put(key, value);
//			}
//		}
		return formulaListMap;
	}
	
	/**
	 * 替换key对应表达式
	 * @param value
	 * @param formulaListMap
	 * @param salaryDerail 
	 * @param baseFields 
	 * @return
	 * @throws Exception 
	 */
	private String replaceStr(String formula,Map<String, String> formulaListMap, SalaryDetailEntity salaryDerail) throws Exception {
		logger.info("员工薪资计算:替换表达式");
		Set<String> keys = formulaListMap.keySet();
		for(String key:keys){
			if(formula.indexOf(key) > -1){
				formula = formula.replaceAll(key, "("+ formulaListMap.get(key) +")");
				formula = replaceFormulaValue(formula, salaryDerail);
				formula = replaceStr(formula, formulaListMap, salaryDerail);
				break;
			}
		}
		return formula;
	}
	
	/**
	 * 替换基础字敦
	 * @param formula
	 * @param baseFields
	 * @return
	 * @throws Exception
	 */
	private String replaceBaseFields(String formula,BaseFieldsEntity baseFields) throws Exception {
		logger.info("员工薪资计算:替换基础字敦");
		if(UtilString.isEmpty(formula) || baseFields == null){
			throw new Exception("数据异常");
		}
		if(formula.indexOf("baseSalary") > -1){
			formula = formula.replace("baseSalary", "(" + baseFields.getBaseSalary() + ")");
		}
		if(formula.indexOf("fixedOverTimeSalary") > -1){
			formula = formula.replace("fixedOverTimeSalary", "(" + baseFields.getFixedOverTimeSalary() + ")");
		}
		if(formula.indexOf("companySalary") > -1){
			formula = formula.replace("companySalary", "(" + baseFields.getCompanySalary() + ")");
		}
		if(formula.indexOf("postSalary") > -1){
			formula = formula.replace("postSalary", "(" + baseFields.getPostSalary() + ")");
		}
		if(formula.indexOf("attendanceHours") > -1){
			formula = formula.replace("attendanceHours", "(" + baseFields.getAttendanceHours() + ")");
		}
		if(formula.indexOf("absenceHours") > -1){
			formula = formula.replace("absenceHours", "(" + baseFields.getAbsenceHours() + ")");
		}
		if(formula.indexOf("singelMeal") > -1){
			formula = formula.replace("singelMeal", "(" + baseFields.getSingelMeal() + ")");
		}
		if(formula.indexOf("mealnum") > -1){
			formula = formula.replace("mealnum", "(" + baseFields.getMealnum() + ")");
		}
		if(formula.indexOf("morningShift") > -1){
			formula = formula.replace("morningShift", "(" + baseFields.getMorningShift() + ")");
		}
		if(formula.indexOf("nightShift") > -1){
			formula = formula.replace("nightShift", "(" + baseFields.getNightShift() + ")");
		}
		if(formula.indexOf("stay") > -1){
			formula = formula.replace("stay", "(" + baseFields.getStay() + ")");
		}
		if(formula.indexOf("otherAllowance") > -1){
			formula = formula.replace("otherAllowance", "(" + baseFields.getOtherAllowance() + ")");
		}
		if(formula.indexOf("performanceScore") > -1){
			formula = formula.replace("performanceScore", "(" + baseFields.getPerformanceScore() + ")");
		}
		if(formula.indexOf("performanceSalary") > -1){
			formula = formula.replace("performanceSalary", "(" + baseFields.getPerformanceSalary() + ")");
		}
		if(formula.indexOf("security") > -1){
			formula = formula.replace("security", "(" + baseFields.getSecurity() + ")");
		}
		if(formula.indexOf("otherBonus") > -1){
			formula = formula.replace("otherBonus", "(" + baseFields.getOtherBonus() + ")");
		}
		if(formula.indexOf("beforeDeduction") > -1){
			formula = formula.replace("beforeDeduction", "(" + baseFields.getBeforeDeduction() + ")");
		}
		if(formula.indexOf("addOther") > -1){
			formula = formula.replace("addOther", "(" + baseFields.getAddOther() + ")");
		}
		if(formula.indexOf("companyAge") > -1){
			formula = formula.replace("companyAge", "(" + baseFields.getCompanyAge() + ")");
		}
		if(formula.indexOf("sickGrandHours") > -1){
			formula = formula.replace("sickGrandHours", "(" + baseFields.getSickGrandHours() + ")");
		}
		if(formula.indexOf("mealDeduction") > -1){
			formula = formula.replace("mealDeduction", "(" + baseFields.getMealDeduction() + ")");
		}
		if(formula.indexOf("dormDeduction") > -1){
			formula = formula.replace("dormDeduction", "(" + baseFields.getDormDeduction() + ")");
		}
		if(formula.indexOf("insurance") > -1){
			formula = formula.replace("insurance", "(" + baseFields.getInsurance() + ")");
		}
		if(formula.indexOf("providentFund") > -1){
			formula = formula.replace("providentFund", "(" + baseFields.getProvidentFund() + ")");
		}
		if(formula.indexOf("sickHours") > -1){
			formula = formula.replace("sickHours", "(" + baseFields.getSickHours() + ")");
		}
		if(formula.indexOf("parentalHours") > -1){
			formula = formula.replace("parentalHours", "(" + baseFields.getParentalHours() + ")");
		}
		if(formula.indexOf("completionHours") > -1){
			formula = formula.replace("completionHours", "(" + baseFields.getCompletionHours() + ")");
		}
		if(formula.indexOf("onboardingHours") > -1){
			formula = formula.replace("onboardingHours", "(" + baseFields.getOnboardingHours() + ")");
		}
		if(formula.indexOf("overSalary") > -1){
			formula = formula.replace("overSalary", "(" + baseFields.getOverSalary() + ")");
		}
		if(formula.indexOf("thingHours") > -1){
			formula = formula.replace("thingHours", "(" + baseFields.getThingHours() + ")");
		}
		if(formula.indexOf("afterDeduction") > -1){
			formula = formula.replace("afterDeduction", "(" + baseFields.getAfterDeduction() + ")");
		}
		if(formula.indexOf("lateTime") > -1){
			formula = formula.replace("lateTime", "(" + baseFields.getLateTime() + ")");
		}
		if(formula.indexOf("leaveTime") > -1){
			formula = formula.replace("leaveTime", "(" + baseFields.getLeaveTime() + ")");
		}
		if(formula.indexOf("compensatory") > -1){
			formula = formula.replace("compensatory", "(" + baseFields.getCompensatory() + ")");
		}
		if(formula.indexOf("highTem") > -1){
			formula = formula.replace("highTem", "(" + baseFields.getHighTem() + ")");
		}
		if(formula.indexOf("lowTem") > -1){
			formula = formula.replace("lowTem", "(" + baseFields.getLowTem() + ")");
		}
		if(formula.indexOf("workType") > -1){
			formula = formula.replace("workType", "(" + baseFields.getWorkType() + ")");
		}
		if(formula.indexOf("workPlace") > -1){
			formula = formula.replace("workPlace", "(" + baseFields.getWorkPlace() + ")");
		}
		if(formula.indexOf("property") > -1){
			formula = formula.replace("property", "(" + baseFields.getProperty() + ")");
		}
		if(formula.indexOf("nationality") > -1){
			formula = formula.replace("nationality", "(" + baseFields.getNationality() + ")");
		}
		if(formula.indexOf("partTime") > -1){
			formula = formula.replace("partTime", "(" + baseFields.getPartTime() + ")");
		}
		if(formula.indexOf("callSubsidies") > -1){
			formula = formula.replace("callSubsidies", "(" + baseFields.getCallSubsidies() + ")");
		}
		if(formula.indexOf("reserved1") > -1){
			formula = formula.replace("reserved1", "(" + baseFields.getReserved1() + ")");
		}
		if(formula.indexOf("reserved2") > -1){
			formula = formula.replace("reserved2", "(" + baseFields.getReserved2() + ")");
		}
		if(formula.indexOf("reserved3") > -1){
			formula = formula.replace("reserved3", "(" + baseFields.getReserved3() + ")");
		}
		if(formula.indexOf("reserved4") > -1){
			formula = formula.replace("reserved4", "(" + baseFields.getReserved4() + ")");
		}
		if(formula.indexOf("reserved5") > -1){
			formula = formula.replace("reserved5", "(" + baseFields.getReserved5() + ")");
		}
		if(formula.indexOf("reserved6") > -1){
			formula = formula.replace("reserved6", "(" + baseFields.getReserved6() + ")");
		}
		if(formula.indexOf("reserved7") > -1){
			formula = formula.replace("reserved7", "(" + baseFields.getReserved7() + ")");
		}
		if(formula.indexOf("reserved8") > -1){
			formula = formula.replace("reserved8", "(" + baseFields.getReserved8() + ")");
		}
		if(formula.indexOf("reserved9") > -1){
			formula = formula.replace("reserved9", "(" + baseFields.getReserved9() + ")");
		}
		if(formula.indexOf("reserved10") > -1){
			formula = formula.replace("reserved10", "(" + baseFields.getReserved10() + ")");
		}
		if(formula.indexOf("partTime") > -1){
			formula = formula.replace("partTime", "0");
		}
		if(formula.indexOf("fullTime") > -1){
			formula = formula.replace("fullTime", "1");
		}
		if(formula.indexOf("mainLand") > -1){
			formula = formula.replace("mainLand", "1");
		}
		if(formula.indexOf("nonContinent") > -1){
			formula = formula.replace("nonContinent", "2");
		}
		if(formula.indexOf("piece") > -1){
			formula = formula.replace("piece", "1");
		}
		if(formula.indexOf("piming") > -1){
			formula = formula.replace("piming", "0");
		}
		return formula;
	}
	
	/**
	 * 替换已计算获得的数据值
	 * @param formula
	 * @param salaryDerail
	 * @param baseFields
	 * @param formulaList
	 * @return
	 * @throws Exception
	 */
	private String replaceFormulaValue(String formula, SalaryDetailEntity salaryDerail) throws Exception {
		logger.info("员工薪资计算:替换已计算项");
		if (UtilString.isEmpty(formula) || salaryDerail == null) {
			return formula;
		}
		if (formula.indexOf("laterAndLeaveFormula") > -1 && salaryDerail.getLaterAndLeaveDeduction() != null) {
			formula.replaceAll("laterAndLeaveFormula", "(" + salaryDerail.getLaterAndLeaveDeduction() + ")");
		}
		if (formula.indexOf("completionFormula") > -1 && salaryDerail.getCompletionDeduction() != null) {
			formula.replaceAll("completionFormula", "(" + salaryDerail.getCompletionDeduction() + ")");
		}
		if (formula.indexOf("thingFormula") > -1 && salaryDerail.getThingDeduction() != null) {
			formula.replaceAll("thingFormula", "(" + salaryDerail.getThingDeduction() + ")");
		}
		if (formula.indexOf("sickFormula") > -1 && salaryDerail.getSickDeduction() != null) {
			formula.replaceAll("sickFormula", "(" + salaryDerail.getSickDeduction() + ")");
		}
		if (formula.indexOf("parentalFormula") > -1 && salaryDerail.getParentalDeduction() != null) {
			formula.replaceAll("parentalFormula", "(" + salaryDerail.getParentalDeduction() + ")");
		}
		if (formula.indexOf("onboardingFormula") > -1 && salaryDerail.getOnboarding() != null) {
			formula.replaceAll("onboardingFormula", "(" + salaryDerail.getOnboarding() + ")");
		}
		if (formula.indexOf("totalFormula") > -1 && salaryDerail.getTotalDeduction() != null) {
			formula.replaceAll("totalFormula", "(" + salaryDerail.getTotalDeduction() + ")");
		}
		if (formula.indexOf("riceStickFormula") > -1 && salaryDerail.getRiceStick() != null) {
			formula.replaceAll("riceStickFormula", "(" + salaryDerail.getRiceStick() + ")");
		}
		if (formula.indexOf("callSunSalaryFormula") > -1 && salaryDerail.getCallSubsidies() != null) {
			formula.replaceAll("callSunSalaryFormula", "(" + salaryDerail.getCallSubsidies() + ")");
		}
		if (formula.indexOf("highTemFormula") > -1 && salaryDerail.getHighTem() != null) {
			formula.replaceAll("highTemFormula", "(" + salaryDerail.getHighTem() + ")");
		}
		if (formula.indexOf("lowTemFormula") > -1 && salaryDerail.getLowTem() != null) {
			formula.replaceAll("lowTemFormula", "(" + salaryDerail.getLowTem() + ")");
		}
		if (formula.indexOf("wagePayableFormula") > -1 && salaryDerail.getWagePayable() != null) {
			formula.replaceAll("wagePayableFormula", "(" + salaryDerail.getWagePayable() + ")");
		}
		if (formula.indexOf("personalTaxFormula") > -1 && salaryDerail.getPersonalTax() != null) {
			formula.replaceAll("personalTaxFormula", "(" + salaryDerail.getPersonalTax() + ")");
		}
		if (formula.indexOf("realWagesFormula") > -1 && salaryDerail.getRealWages() != null) {
			formula.replaceAll("realWagesFormula", "(" + salaryDerail.getRealWages() + ")");
		}
		if (formula.indexOf("performanceFormula") > -1 && salaryDerail.getPerformanceBonus() != null) {
			formula.replaceAll("performanceFormula", "(" + salaryDerail.getPerformanceBonus() + ")");
		}
		return formula;
	}
	
	/**
	 * 替换已计算数据或公式
	 * @param formula
	 * @param salaryDerail
	 * @param baseFields
	 * @param formulaList
	 * @return
	 * @throws Exception
	 */
	private String replaceFields(String formula, SalaryDetailEntity salaryDerail, BaseFieldsEntity baseFields,
			Map<String, String> formulaList) throws Exception {
		formula = replaceFormulaValue(formula, salaryDerail);
		formula = replaceStr(formula, formulaList,salaryDerail);
		return replaceBaseFields(formula, baseFields);
	}
}
