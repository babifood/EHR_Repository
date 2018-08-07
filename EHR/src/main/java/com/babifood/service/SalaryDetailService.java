package com.babifood.service;

import java.io.OutputStream;
import java.util.Map;

import com.babifood.entity.SalaryDetailEntity;

public interface SalaryDetailService {

	public Map<String, Object> saveCurrentMonthSalary(SalaryDetailEntity salaryDerail);

	public Map<String, Object> getPageSalaryDetails(Integer page, Integer row, String pNumber, String pName,
			String organzationName, String deptName, String officeName, String groupName);

	public void exportExcel(OutputStream ouputStream) throws Exception;

}
