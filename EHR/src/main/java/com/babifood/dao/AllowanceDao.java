package com.babifood.dao;

import java.util.List;
import java.util.Map;

public interface AllowanceDao {

	public List<Map<String, Object>> findEmployAllowance(Map<String,Object> param);

	public void saveEmployAllowances(List<Object[]> values);

	public Integer getAllowanceCount(Map<String, Object> param);

}
