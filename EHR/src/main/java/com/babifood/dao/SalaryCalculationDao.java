package com.babifood.dao;

import java.util.Map;

public interface SalaryCalculationDao {

	public Map<String, Object> findBaseSalary(String date, String pNumber);

	public Integer findSalaryCalculationStatus(String year, String month, String companyCode, String statusType);

	public void updateSalaryCalculationStatus(String year, String month, Integer type, String companyCode, String statusType);

	public Map<String, Object> findPersonFee(String year, String month, String pNumber);

}
