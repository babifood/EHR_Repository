package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.SalaryDetailEntity;

public interface SalaryDetailDao {

	public Map<String, Object> findCurrentMonthSalary(String year, String month, String pNumber);

	public void updateSalaryDetail(SalaryDetailEntity salaryDerail);

	public void addSalaryDetail(SalaryDetailEntity salaryDerail);

	public Integer getSalaryDetailsCount(Map<String, Object> params);

	public List<Map<String, Object>> getPageSalaryDetails(Map<String, Object> params);

	public void saveSalaryDetailEntityList(List<SalaryDetailEntity> salaryDetails);

}
