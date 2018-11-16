package com.babifood.dao;

import java.util.List;
import java.util.Map;

public interface BasicSalaryDetailDao {

	public Integer getSalaryDetailsCount(Map<String, Object> params);

	public List<Map<String, Object>> getPageSalaryDetails(Map<String, Object> params);

	public void saveBasicSalaryDetailEntityList(List<Map<String, Object>> values);

	public List<Map<String, Object>> loadUserDataAuthority();

	public List<String> findPNumberList(List<Map<String, Object>> auths);


}
