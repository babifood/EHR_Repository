package com.babifood.dao;

import java.util.Map;

public interface SalaryCalculationDao {

	public Map<String, Object> findBaseSalary(String date, String pNumber);

	public Integer findSalaryCalculationStatus(String year, String month);

	public void updateSalaryCalculationStatus(String year, String month, Integer type);

}
