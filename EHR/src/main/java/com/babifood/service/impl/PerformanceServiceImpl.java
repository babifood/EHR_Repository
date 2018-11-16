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
import com.babifood.dao.PerformanceDao;
import com.babifood.entity.LoginEntity;
import com.babifood.service.PerformanceService;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.ExcelUtil;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;

@Service
public class PerformanceServiceImpl implements PerformanceService{

	private static Logger logger = Logger.getLogger(PerformanceServiceImpl.class);
	
	@Autowired
	private PerformanceDao performanceDao;
	
	/**
	 * 查询绩效薪资列表
	 */
	@Override
	@LogMethod(module = ModuleConstant.PERFORMANCE)
	public Map<String, Object> getPagePerformances(Integer page, Integer rows, String pNumber, String pName, String organzationName, String deptName,
			String officeName) {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer pageNum = page == null ? 1 : page;
		Integer pageSize = rows == null ? 10 : rows;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", (pageNum - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("pNumber", pNumber);
		params.put("pName", pName);
		params.put("organzationName", organzationName);
		params.put("deptName", deptName);
		params.put("officeName", officeName);
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			Integer total = performanceDao.getPerformancesCount(params);
			List<Map<String, Object>> performanceList = performanceDao.queryPerformanceList(params);
			result.put("total", total);
			result.put("rows", performanceList);
			LogManager.putContectOfLogInfo("查询绩效信息列表");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "分页查询数据失败");
			logger.error("查询绩效信息列表失败",e);
			LogManager.putContectOfLogInfo("查询绩效信息列表失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 导出绩效薪资
	 */
	@Override
	@LogMethod(module = ModuleConstant.PERFORMANCE)
	public Map<String, Object> exportPerformances(OutputStream ouputStream, String type, String year, String month) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> row1Name = getRow1Name();
		String[] sort = new String[]{"year", "month", "pNumber", "pName", "organzationName", "deptName", "officeName", "performanceScore", "pSalary"};
		List<Map<String, Object>> performanceList = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
			if("1".equals(type)){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("month", UtilString.isEmpty(month) ? UtilDateTime.getPreMonth() : month);
				param.put("year", UtilString.isEmpty(year) ? UtilDateTime.getYearOfPreMonth() : year);
				performanceList = performanceDao.queryPerformanceList(param);
				LogManager.putContectOfLogInfo("导出绩效信息列表");
			} else {
				performanceList = new ArrayList<Map<String, Object>>();
				LogManager.putContectOfLogInfo("下载绩效薪资信息模板");
			}
			ExcelUtil.exportExcel("绩效工资列表", row1Name, performanceList, ouputStream, sort);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "导出excel失败");
			logger.error("导出绩效信息列表失败",e);
			LogManager.putContectOfLogInfo("导出绩效信息列表失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * excel首行
	 * @return
	 */
	private Map<String, String> getRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("year", "年");
		row1Name.put("month", "月");
		row1Name.put("pNumber", "员工工号");
		row1Name.put("pName", "员工姓名");
		row1Name.put("organzationName", "中心机构");
		row1Name.put("deptName", "部门名称");
		row1Name.put("officeName", "科室名称");
		row1Name.put("performanceScore", "绩效分值");
		row1Name.put("pSalary", "绩效工资");
		return row1Name;
	}

	/**
	 * 导入绩效薪资信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.PERFORMANCE)
	public Map<String, Object> importExcel(MultipartFile file, String type) {
		Map<String, Object> result = new HashMap<>();
		Map<String, String> row1Name = getImportRow1Name();
		List<Map<String, Object>> values = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_IMPORT);
			values = ExcelUtil.importExcel(file, row1Name);
			if(values != null && values.size() > 0){
				Map<String, Object> user = checkValues(values);
				if(user == null || user.size() <= 0){
					List<Object[]> performanceParam = getPerformanceParam(values);
					performanceDao.savePerformance(performanceParam);
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
			LogManager.putContectOfLogInfo("导入绩效薪资信息");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", "excel数据异常，导入数据失败");
			logger.error("绩效信息数据异常",e);
			LogManager.putContectOfLogInfo("导出绩效信息列表失败，错误信息：绩效信息数据异常，" + e.getMessage());
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存导入数据异常，导入数据失败");
			logger.error("保存绩效信息失败",e);
			LogManager.putContectOfLogInfo("导出绩效信息列表失败，错误信息：保存绩效信息失败，" + e.getMessage());
		}
		return result;
	}
	
	public Map<String, Object> checkValues(List<Map<String, Object>> values) {
		Map<String, Object> user = new HashMap<String, Object>();
		List<Map<String, Object>> auths = performanceDao.loadUserDataAuthority();
		if(auths != null && auths.size() > 0){
			List<String> pNumberList = performanceDao.findPNumberList(auths);
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
	 * 导入excel首行与字段对应关系
	 * @return
	 */
	private Map<String, String> getImportRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("年", "year");
		row1Name.put("月", "month");
		row1Name.put("员工工号", "pNumber");
		row1Name.put("员工姓名", "pName");
		row1Name.put("中心机构", "organzationName");
		row1Name.put("部门名称", "deptName");
		row1Name.put("科室名称", "officeName");
		row1Name.put("绩效分值", "performanceScore");
		row1Name.put("绩效工资", "pSalary");
		return row1Name;
	}

	/**
	 * 组织参数
	 * @param values
	 * @return
	 */
	private List<Object[]> getPerformanceParam(List<Map<String, Object>> values) {
		List<Object[]> performanceParam = new ArrayList<Object[]>();
		for (Map<String, Object> map : values) {
			Object[] obj = new Object[] { map.get("year"),
					(map.get("month") + "").length() == 1 ? "0" + map.get("month") : map.get("month"),
					map.get("pNumber"), map.get("performanceScore"), BASE64Util.getDecodeStringTowDecimal(map.get("pSalary") + "") };
			performanceParam.add(obj);
		}
		return performanceParam;
	}

	/**
	 * 修改绩效薪资信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.PERFORMANCE)
	public Map<String, Object> savePerformanceScore(String year, String month, String pNumber, String score, String salary) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EDIT);
			performanceDao.updatePerformanceScore(year, month, pNumber, score, salary);
			result.put("code", "1");
			result.put("msg", "修改绩效信息成功");
			LogManager.putContectOfLogInfo("修改绩效信息");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "修改绩效信息失败");
			logger.error("修改绩效信息失败",e);
			LogManager.putContectOfLogInfo("修改绩效信息失败，错误信息：" + e.getMessage());
		}
		return result;
	}

}
