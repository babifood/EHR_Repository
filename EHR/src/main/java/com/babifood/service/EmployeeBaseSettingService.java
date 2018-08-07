package com.babifood.service;

import java.util.Map;

import com.babifood.entity.BaseSettingEntity;

public interface EmployeeBaseSettingService {

	public Map<String, Object> getPageBaseSettingInfo(Integer page, Integer rows, String pName, String pNumber,
			String workType, String workPlace, String ismeal);

	public Map<String, Object> saveBaseSettingInfo(BaseSettingEntity baseSetting);

	public Map<String, Object> findBaseSettingByPnumber(String pNumber);

}
