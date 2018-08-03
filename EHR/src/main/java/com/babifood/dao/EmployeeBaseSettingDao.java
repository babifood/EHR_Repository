package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.BaseSettingEntity;

public interface EmployeeBaseSettingDao {

	public int getCountOfBaseSettingInfo(Map<String, Object> params);

	public List<Map<String, Object>> getPageBaseSettingInfo(Map<String, Object> params);

	public void insertBaseSettingInfo(BaseSettingEntity baseSetting);

	public void updateBaseSettingInfo(BaseSettingEntity baseSetting);

	public Map<String, Object> findBaseSettingByPnumber(String pNumber);

}
