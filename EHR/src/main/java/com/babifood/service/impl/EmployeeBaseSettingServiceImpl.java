package com.babifood.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.EmployeeBaseSettingDao;
import com.babifood.entity.BaseSettingEntity;
import com.babifood.service.EmployeeBaseSettingService;

@Service
public class EmployeeBaseSettingServiceImpl implements EmployeeBaseSettingService {

	@Autowired
	private EmployeeBaseSettingDao employeeDao;

	@Override
	public Map<String, Object> getPageBaseSettingInfo(Integer page, Integer rows, String pName, String pNumber,
			String workType, String workPlace, String ismeal) {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer pageNum = page == null ? 1 : page <= 0 ? 1 : page;
		Integer pageSize = rows == null ? 10 : rows <= 0 ? 10 : rows;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", (pageNum - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("pName", pName);
		params.put("pNumber", pNumber);
		params.put("workType", workType);
		params.put("workPlace", workPlace);
		params.put("ismeal", ismeal);
		try {
			int total = employeeDao.getCountOfBaseSettingInfo(params);
			List<Map<String, Object>> baseSettings = employeeDao.getPageBaseSettingInfo(params);
			result.put("code", "1");
			result.put("total", total);
			result.put("rows", baseSettings);
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询数据失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> saveBaseSettingInfo(BaseSettingEntity baseSetting) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(baseSetting.getId() == null){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("pnumber", baseSetting.getpNumber());
				int total = employeeDao.getCountOfBaseSettingInfo(params);
				if(total > 0){
					result.put("code", "0");
					result.put("msg", "该员工基础设置信息已存在，不能重复新增");
					return result;
				} else {
					employeeDao.insertBaseSettingInfo(baseSetting);
				}
			} else {
				employeeDao.updateBaseSettingInfo(baseSetting);
			}
			result.put("code", "1");
			result.put("msg", "保存数据成功");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存数据失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> findBaseSettingByPnumber(String pNumber) {
		Map<String, Object> baseSetting = new HashMap<>();
		try {
			baseSetting = employeeDao.findBaseSettingByPnumber(pNumber);
		} catch (Exception e) {
			
		}
		return baseSetting;
	}

}
