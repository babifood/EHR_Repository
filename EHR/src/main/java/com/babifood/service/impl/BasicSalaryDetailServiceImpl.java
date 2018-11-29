package com.babifood.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.dao.BasicSalaryDetailDao;
import com.babifood.entity.LoginEntity;
import com.babifood.service.BasicSalaryDetailService;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.ExcelUtil;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;

/**
 * 薪资数据操作类
 * @author wangguocheng
 *
 */
@Service
public class BasicSalaryDetailServiceImpl implements BasicSalaryDetailService {

	private static Logger logger = Logger.getLogger(BasicSalaryDetailServiceImpl.class);
	
	@Autowired
	private BasicSalaryDetailDao basicSalaryDetailDao;
	

	/**
	 * 分页查询薪资信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.SALARYDETAIL)
	public Map<String, Object> getPageSalaryDetails(Integer page, Integer row, String pNumber, String pName,
			String companyCode, String organzationName, String deptName, String officeName, String groupName) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Integer pageNum = page == null ? 1 : page;
		Integer pageSize = row == null ? 10 : row;
		params.put("start", (pageNum - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("pNumber", pNumber);
		params.put("pName", pName);
		params.put("organzationName", organzationName);
		params.put("deptName", deptName);
		params.put("officeName", officeName);
		params.put("groupName", groupName);
		params.put("companyCode", companyCode);
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			Integer count = basicSalaryDetailDao.getSalaryDetailsCount(params);
			List<Map<String, Object>> salaryDetailList = basicSalaryDetailDao.getPageSalaryDetails(params);
			BASE64Util.Base64DecodeMap(salaryDetailList);
			result.put("total", count);
			result.put("rows", salaryDetailList);
			LogManager.putContectOfLogInfo("分页查询一线员工薪资信息");
		} catch (Exception e) {
			logger.error("分页查询薪资数据失败",e);
			LogManager.putContectOfLogInfo("分页查询一线员工薪资信息失败,错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 导出薪资明细数据
	 */
	@Override
	@LogMethod(module = ModuleConstant.SALARYDETAIL)
	public void exportExcel(OutputStream ouputStream) throws Exception {
		Map<String, String> row1Name = getRow1Names();
		String[] sort = new String[]{"year", "month", "pNumber", "pName", "companyName", "organizationName",
				"deptName", "officeName", "groupName", "postName", "baseSalary", "fixedOvertimeSalary", 
				"postSalary", "companySalary", "totalDeduction", "wagePayable", "personalTax", "realWages", 
				"callSubsidies", "riceStick", "highTem", "lowTem", "morningShift", "nightShift", "stay", 
				"otherAllowance", "security", "performanceBonus", "compensatory", "otherBonus", "addOther", 
				"overSalary", "attendanceHours", "absenceHours", "thingDeduction", "sickDeduction", 
				"parentalDeduction", "onboarding", "laterAndLeaveDeduction", "completionDeduction", "yearDeduction", 
				"relaxation", "trainDeduction", "marriageDeduction", "companionParentalDeduction", "funeralDeduction", 
				"mealDeduction", "dormDeduction", "insurance", "providentFund", "beforeDeduction", "afterDeduction",};
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
			List<Map<String, Object>> dataSource = new ArrayList<Map<String,Object>>();
			ExcelUtil.exportExcel("一线员工薪资详情列表", row1Name, dataSource, ouputStream, sort);
			LogManager.putContectOfLogInfo("导出一线员工薪资明细数据");
		} catch (Exception e) {
			logger.error("导出薪资数据失败",e);
			LogManager.putContectOfLogInfo("导出一线员工薪资明细数据失败,错误信息：" + e.getMessage());
		}
	}

	/**
	 * 第一行名称
	 * @return
	 */
	private Map<String, String> getRow1Names() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("year", "年");
		row1Name.put("month", "月");
		row1Name.put("pName", "员工姓名");
		row1Name.put("pNumber", "员工工号");
		row1Name.put("companyName", "公司名称");
		row1Name.put("organizationName", "中心机构");
		row1Name.put("deptName", "部门名称");
		row1Name.put("officeName", "科室名称");
		row1Name.put("groupName", "班组名称");
		row1Name.put("postName", "岗位名称");
		row1Name.put("attendanceHours", "应出勤小时数");
		row1Name.put("absenceHours", "缺勤小时数");
		row1Name.put("baseSalary", "基本工资");
		row1Name.put("fixedOvertimeSalary", "固定加班工资");
		row1Name.put("postSalary", "岗位工资");
		row1Name.put("callSubsidies", "话费补贴");
		row1Name.put("companySalary", "司龄工资");
		row1Name.put("overSalary", "加班工资");
		row1Name.put("riceStick", "饭贴");
		row1Name.put("highTem", "高温津贴");
		row1Name.put("lowTem", "低温津贴");
		row1Name.put("morningShift", "早班津贴");
		row1Name.put("nightShift", "夜班津贴");
		row1Name.put("stay", "驻外/住宿津贴");
		row1Name.put("otherAllowance", "其它津贴");
		row1Name.put("security", "安全奖金");
		row1Name.put("performanceBonus", "绩效奖金");
		row1Name.put("compensatory", "礼金/补偿金");
		row1Name.put("otherBonus", "其它奖金");
		row1Name.put("addOther", "加其它");
		row1Name.put("mealDeduction", "餐费扣款");
		row1Name.put("dormDeduction", "住宿扣款");
		row1Name.put("beforeDeduction", "其它扣款（税前）");
		row1Name.put("insurance", "社保扣款");
		row1Name.put("providentFund", "公积金");
		row1Name.put("afterDeduction", "其它扣款（税后）");
		row1Name.put("laterAndLeaveDeduction", "迟到和早退扣款");
		row1Name.put("completionDeduction", "旷工扣款");
		row1Name.put("yearDeduction", "年假扣款");
		row1Name.put("relaxation", "调休扣款");
		row1Name.put("thingDeduction", "事假扣款");
		row1Name.put("sickDeduction", "病假扣款");
		row1Name.put("trainDeduction", "培训假扣款");
		row1Name.put("parentalDeduction", "产假扣款");
		row1Name.put("marriageDeduction", "婚假扣款");
		row1Name.put("companionParentalDeduction", "陪产假扣款");
		row1Name.put("funeralDeduction", "丧假扣款");
		row1Name.put("onboarding", "月中入职、离职扣款");
		row1Name.put("totalDeduction", "应扣合计");
		row1Name.put("wagePayable", "应发工资");
		row1Name.put("realWages", "实发工资");
		row1Name.put("personalTax", "代缴税金");
		return row1Name;
	}


	@Override
	public Map<String, Object> importExcel(MultipartFile file) throws Exception {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_IMPORT);
		Map<String, Object> result = new HashMap<>();
		Map<String, String> row1Name = getRow1Name();
		List<Map<String, Object>> values = null;
		try {
			values = ExcelUtil.importExcel(file, row1Name);
			if(values != null && values.size() > 0){
				Map<String, Object> user = checkValues(values);
				if(user == null || user.size() <= 0){
					basicSalaryDetailDao.saveBasicSalaryDetailEntityList(values);
					result.put("code", "1");
					result.put("msg", "导入数据成功");
				} else {
					result.put("code", "0");
					result.put("msg", "导入数据失败，"+user.get("pName") + ",工号"+user.get("pNumber")+"不是权限范围内员工");
				}
			} else {
				result.put("code", "1");
				result.put("msg", "导入数据成功");
			}
		} catch (IOException e) {
			result.put("code", "0");
			result.put("msg", "excel数据异常，导入数据失败");
			logger.error("导入一线员工薪资详情信息失败",e);
			LogManager.putContectOfLogInfo("导入一线员工薪资详情信息失败:"+e.getMessage());
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存导入数据异常，导入数据失败");
			logger.error("保存一线员工薪资详情信息失败",e);
			LogManager.putContectOfLogInfo("保存一线员工薪资详情信息失败:"+e.getMessage());
		}
		return result;
	}
	
	public Map<String, Object> checkValues(List<Map<String, Object>> values) {
		Map<String, Object> user = new HashMap<String, Object>();
		List<Map<String, Object>> auths = basicSalaryDetailDao.loadUserDataAuthority();
		if(auths != null && auths.size() > 0){
			List<String> pNumberList = basicSalaryDetailDao.findPNumberList(auths);
			for(Map<String, Object> map : values){
				if(!pNumberList.contains(map.get("pNumber")+"")){
					user = map;
					break;
				}
			}
		}
		return user;
	}
	
	/**
	 * 第一行名称
	 * @return
	 */
	private Map<String, String> getRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("年", "year");
		row1Name.put("月", "month");
		row1Name.put("员工姓名", "pName");
		row1Name.put("员工工号", "pNumber");
		row1Name.put("公司名称", "companyName");
		row1Name.put("中心机构", "organizationName");
		row1Name.put("部门名称", "deptName");
		row1Name.put("科室名称", "officeName");
		row1Name.put("班组名称", "groupName");
		row1Name.put("岗位名称", "postName");
		row1Name.put("应出勤小时数", "attendanceHours");
		row1Name.put("缺勤小时数", "absenceHours");
		row1Name.put("基本工资", "baseSalary");
		row1Name.put("固定加班工资", "fixedOvertimeSalary");
		row1Name.put("岗位工资", "postSalary");
		row1Name.put("话费补贴", "callSubsidies");
		row1Name.put("司龄工资", "companySalary");
		row1Name.put("加班工资", "overSalary");
		row1Name.put("饭贴", "riceStick");
		row1Name.put("高温津贴", "highTem");
		row1Name.put("低温津贴", "lowTem");
		row1Name.put("早班津贴", "morningShift");
		row1Name.put("夜班津贴", "nightShift");
		row1Name.put("驻外/住宿津贴", "c");
		row1Name.put("其它津贴", "otherAllowance");
		row1Name.put("安全奖金", "security");
		row1Name.put("绩效奖金", "performanceBonus");
		row1Name.put("礼金/补偿金", "compensatory");
		row1Name.put("其它奖金", "otherBonus");
		row1Name.put("加其它", "addOther");
		row1Name.put("餐费扣款", "mealDeduction");
		row1Name.put("住宿扣款", "dormDeduction");
		row1Name.put("其它扣款（税前）", "beforeDeduction");
		row1Name.put("社保扣款", "insurance");
		row1Name.put("公积金", "providentFund");
		row1Name.put("其它扣款（税后）", "afterDeduction");
		row1Name.put("迟到和早退扣款", "laterAndLeaveDeduction");
		row1Name.put("旷工扣款", "completionDeduction");
		row1Name.put("年假扣款", "yearDeduction");
		row1Name.put("调休扣款", "relaxation");
		row1Name.put("事假扣款", "thingDeduction");
		row1Name.put("病假扣款", "sickDeduction");
		row1Name.put("培训假扣款", "trainDeduction");
		row1Name.put("产假扣款", "parentalDeduction");
		row1Name.put("婚假扣款", "marriageDeduction");
		row1Name.put("陪产假扣款", "companionParentalDeduction");
		row1Name.put("丧假扣款", "funeralDeduction");
		row1Name.put("月中入职、离职扣款", "onboarding");
		row1Name.put("应扣合计", "totalDeduction");
		row1Name.put("应发工资", "wagePayable");
		row1Name.put("实发工资", "realWages");
		row1Name.put("代缴税金", "personalTax");
		return row1Name;
	}


}
