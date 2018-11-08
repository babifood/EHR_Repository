package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.SalaryDetailEntity;

public interface BasicSalaryDetailDao {

	public Integer getSalaryDetailsCount(Map<String, Object> params);

	public List<Map<String, Object>> getPageSalaryDetails(Map<String, Object> params);

	public void saveBasicSalaryDetailEntityList(List<Map<String, Object>> values);


}
