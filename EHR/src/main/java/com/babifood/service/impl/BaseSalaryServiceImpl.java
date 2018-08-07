package com.babifood.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.BaseSalaryDao;
import com.babifood.entity.BaseSalaryEntity;
import com.babifood.service.BaseSalaryService;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.UtilDateTime;

@Service
public class BaseSalaryServiceImpl implements BaseSalaryService {
	
	@Autowired
	private BaseSalaryDao baseSalaryDao;

	@Override
	public Map<String, Object> getPageBaseSalary(Integer page, Integer rows, String pNumber, String pName) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Integer pageNum = page == null ? 1 : page;
		Integer pageSize = rows == null ? 10 : rows;
		params.put("start", (pageNum - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("pNumber", pNumber);
		params.put("pName", pName);
		try {
			int count = baseSalaryDao.queryCountOfBaseSalarys(params);
			List<Map<String, Object>> baseSalaryList = baseSalaryDao.queryBaseSalaryList(params);
			BASE64Util.Base64DecodeMap(baseSalaryList);
			result.put("code", "1");
			result.put("total", count);
			result.put("rows", baseSalaryList);
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询基础配置信息失败");
		}
		return result;
	}
	
	@Override
	public Map<String, Object> saveBaseSalary(BaseSalaryEntity baseSalary) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(baseSalary.getId() != null){
				baseSalaryDao.updateBaseSalary(baseSalary);
			} else {
				baseSalary.setCreateTime(UtilDateTime.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				baseSalaryDao.insertBaseSalary(baseSalary);
			}
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存基础配置信息失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> removeBaseSalary(Integer id, String pNumber) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> baseSalarys = baseSalaryDao.findBaseSalaryByPNumber(pNumber);
			if(baseSalarys == null || baseSalarys.size() <= 1){
				result.put("code", "0");
				result.put("msg", "该基础新增信息为员工唯一基础薪资信息，不能删除");
			} else {
				baseSalaryDao.deleteBaseSalaryById(id);
				result.put("code", "1");
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "删除基础配置信息失败");
		}
		return result;
	}

}
