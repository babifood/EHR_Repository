package com.babifood.service.salary;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.dao.SalaryDetailDao;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.SalaryDetailEntity;
import com.babifood.service.SalaryDetailService;
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
public class SalaryDetailServiceImpl implements SalaryDetailService {

	private static Logger logger = Logger.getLogger(SalaryDetailServiceImpl.class);
	
	@Autowired
	private SalaryDetailDao salaryDetailDao;
	
	/**
	 * 保存薪资明细数据
	 */
	@Override
	@LogMethod(module = ModuleConstant.SALARYDETAIL)
	public Map<String, Object> saveCurrentMonthSalary(SalaryDetailEntity salaryDerail) {
		logger.info("员工薪资计算:保存计算数据");
		try {
			Map<String, Object> currentMonthSalary = salaryDetailDao.findCurrentMonthSalary(salaryDerail.getYear(), salaryDerail.getMonth(), salaryDerail.getpNumber());
			if(currentMonthSalary != null){
				salaryDerail.setId(Integer.valueOf(currentMonthSalary.get("Id")+""));
				salaryDetailDao.updateSalaryDetail(salaryDerail);
			} else {
				salaryDetailDao.addSalaryDetail(salaryDerail);
			}
		} catch (Exception e) {
			logger.error("保存薪资数据失败，pNumber："+salaryDerail.getpNumber(),e);
		}
		return null;
	}

	/**
	 * 分页查询薪资信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.SALARYDETAIL)
	public Map<String, Object> getPageSalaryDetails(Integer page, Integer row, String pNumber, String pName,
			String organzationName, String deptName, String officeName, String groupName) {
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
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			Integer count = salaryDetailDao.getSalaryDetailsCount(params);
			List<Map<String, Object>> salaryDetailList = salaryDetailDao.getPageSalaryDetails(params);
			BASE64Util.Base64DecodeMap(salaryDetailList);
			result.put("total", count);
			result.put("rows", salaryDetailList);
			LogManager.putContectOfLogInfo("分页查询薪资信息");
		} catch (Exception e) {
			logger.error("分页查询薪资数据失败",e);
			LogManager.putContectOfLogInfo("分页查询薪资信息失败,错误信息：" + e.getMessage());
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
			List<Map<String, Object>> dataSource = salaryDetailDao.getPageSalaryDetails(new HashMap<String, Object>());;
			BASE64Util.Base64DecodeMap(dataSource);
			ExcelUtil.exportExcel("薪资详情列表", row1Name, dataSource, ouputStream, sort);
			LogManager.putContectOfLogInfo("导出薪资明细数据");
		} catch (Exception e) {
			logger.error("导出薪资数据失败",e);
			LogManager.putContectOfLogInfo("导出薪资明细数据失败,错误信息：" + e.getMessage());
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
		row1Name.put("attendanceHours", "应出勤小时候");
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
		row1Name.put("yearDeduction", "年假");
		row1Name.put("relaxation", "调休");
		row1Name.put("thingDeduction", "事假");
		row1Name.put("sickDeduction", "病假");
		row1Name.put("trainDeduction", "培训假");
		row1Name.put("parentalDeduction", "产假");
		row1Name.put("marriageDeduction", "婚假");
		row1Name.put("companionParentalDeduction", "陪产假");
		row1Name.put("funeralDeduction", "丧假");
		row1Name.put("onboarding", "月中入职、离职");
		row1Name.put("totalDeduction", "应扣合计");
		row1Name.put("wagePayable", "应发工资");
		row1Name.put("realWages", "实发工资");
		row1Name.put("personalTax", "代缴税金");
		return row1Name;
	}

	/**
	 * 批量保存薪资明细
	 */
	@Override
	@LogMethod(module = ModuleConstant.SALARYDETAIL)
	public String saveSalaryDetailEntityList(List<SalaryDetailEntity> salaryDetails) {
		if(salaryDetails == null || salaryDetails.size() <= 0){
			return "薪资明细数量为0";
		}
		String message = "";
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
			salaryDetailDao.saveSalaryDetailEntityList(salaryDetails);
			LogManager.putContectOfLogInfo("批量保存薪资明细");
			message = "批量保存薪资明细成功";
			logger.info("薪资计算========>批量保存薪资明细");
		} catch (Exception e) {
			logger.error("薪资计算========>批量保存薪资明细失败",e);
			LogManager.putContectOfLogInfo("薪资计算========>批量保存薪资明细失败,错误信息：" + e.getMessage());
			message = "批量保存薪资明细失败，错误信息：" + e.getMessage();
		}
		return message;
	}

}
