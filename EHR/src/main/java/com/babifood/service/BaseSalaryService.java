package com.babifood.service;

import java.util.Map;

import com.babifood.entity.BaseSalaryEntity;

public interface BaseSalaryService {

	public Map<String, Object> getPageBaseSalary(Integer page, Integer rows, String pNumber, String pName);

	public Map<String, Object> saveBaseSalary(BaseSalaryEntity baseSalary);

	public Map<String, Object> removeBaseSalary(Integer id, String pNumber);

	public Map<String, Object> getBaseSalaryRecord(Integer page, Integer rows, String pNumber);

}
