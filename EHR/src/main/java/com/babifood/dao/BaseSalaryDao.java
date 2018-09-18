package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.BaseSalaryEntity;

public interface BaseSalaryDao {

	public int queryCountOfBaseSalarys(Map<String, Object> params);

	public void insertBaseSalary(BaseSalaryEntity baseSalary);

	public void updateBaseSalary(BaseSalaryEntity baseSalary);

	public List<Map<String, Object>> queryBaseSalaryList(Map<String, Object> params);

	public List<Map<String, Object>> findBaseSalaryByPNumber(String pNumber);

	public void deleteBaseSalaryById(Integer id);

	public List<Map<String, Object>> getBaseSalaryRecord(String pNumber);

}
