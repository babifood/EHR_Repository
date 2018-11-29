package com.babifood.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.babifood.entity.SalaryDetailEntity;

public interface SalaryDetailService {

	public Map<String, Object> saveCurrentMonthSalary(SalaryDetailEntity salaryDerail);

	public Map<String, Object> getPageSalaryDetails(Integer page, Integer row, String pNumber, String pName,
			String resourceCode, String organzationName, String deptName, String officeName, String groupName);

	public void exportExcel(OutputStream ouputStream) throws Exception;

	public String saveSalaryDetailEntityList(List<SalaryDetailEntity> salaryDetails);

	public Map<String, Object> getUserAuth();

}
