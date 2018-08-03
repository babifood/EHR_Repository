package com.babifood.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.babifood.dao.PerformanceDao;
import com.babifood.service.PerformanceService;
import com.babifood.utils.ExcelUtil;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;

@Service
public class PerformanceServiceImpl implements PerformanceService{

	Logger log = LoggerFactory.getLogger(PerformanceServiceImpl.class);
	
	@Autowired
	private PerformanceDao performanceDao;
	
	@Override
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
			Integer total = performanceDao.getPerformancesCount(params);
			List<Map<String, Object>> performanceList = performanceDao.queryPerformanceList(params);
			result.put("total", total);
			result.put("rows", performanceList);
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "分页查询数据失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> exportPerformances(OutputStream ouputStream, String type, String year, String month) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> row1Name = getRow1Name();
		String[] sort = new String[]{"year", "month", "pNumber", "pName", "organzationName", "deptName", "officeName", "performanceScore", "performanceSalary"};
		List<Map<String, Object>> performanceList = null;
		try {
			if("1".equals(type)){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("month", UtilString.isEmpty(month) ? UtilDateTime.getPreMonth() : month);
				param.put("year", UtilString.isEmpty(year) ? UtilDateTime.getYearOfPreMonth() : year);
				performanceList = performanceDao.queryPerformanceList(param);
			} else {
				performanceList = new ArrayList<Map<String, Object>>();
			}
			ExcelUtil.exportExcel("绩效工资列表", row1Name, performanceList, ouputStream, sort);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "导出excel失败");
		}
		return result;
	}

	private Map<String, String> getRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("year", "年");
		row1Name.put("month", "月");
		row1Name.put("pNumber", "员工工号");
		row1Name.put("pName", "员工姓名");
		row1Name.put("organzationName", "组织机构名称");
		row1Name.put("deptName", "部门名称");
		row1Name.put("officeName", "科室名称");
		row1Name.put("performanceScore", "绩效分值");
		row1Name.put("performanceSalary", "绩效工资");
		return row1Name;
	}

	@Override
	public Map<String, Object> importExcel(MultipartFile file, String type) {
		Map<String, Object> result = new HashMap<>();
		Map<String, String> row1Name = getImportRow1Name();
		List<Map<String, Object>> values = null;
		try {
			values = ExcelUtil.importExcel(file, row1Name);
			if(values != null || values.size() > 0){
				List<Object[]> performanceParam = getPerformanceParam(values);
				performanceDao.savePerformance(performanceParam);
			}
			result.put("code", "1");
			result.put("msg", "导入数据成功");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", "excel数据异常，导入数据失败");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存导入数据异常，导入数据失败");
		}
		return result;
	}

	private Map<String, String> getImportRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("年", "year");
		row1Name.put("月", "month");
		row1Name.put("员工工号", "pNumber");
		row1Name.put("员工姓名", "pName");
		row1Name.put("组织机构名称", "organzationName");
		row1Name.put("部门名称", "deptName");
		row1Name.put("科室名称", "officeName");
		row1Name.put("绩效分值", "performanceScore");
		row1Name.put("绩效工资", "performanceSalary");
		return row1Name;
	}

	private List<Object[]> getPerformanceParam(List<Map<String, Object>> values) {
		List<Object[]> performanceParam = new ArrayList<Object[]>();
		for(Map<String, Object> map : values){
			Object[] obj = new Object[] { map.get("year"),
					(map.get("month") + "").length() == 1 ? "0" + map.get("month") : map.get("month"),
					map.get("pNumber"), map.get("performanceScore") };
			performanceParam.add(obj);
		}
		return performanceParam;
	}

}
