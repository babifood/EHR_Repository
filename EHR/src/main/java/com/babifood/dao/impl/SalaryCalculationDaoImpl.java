package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.SalaryCalculationDao;
import com.babifood.utils.BASE64Util;

@Repository
public class SalaryCalculationDaoImpl implements SalaryCalculationDao {
	
	private static Logger log = Logger.getLogger(SalaryCalculationDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> findBaseSalary(String date, String pNumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT P_NUMBER as pNumber,base_salary as baseSalary,fixed_overtime_salary as fixedOverTimeSalary,post_salary as postSalary,");
		sql.append("call_subsidies as callSubsidies,company_salary as companySalary,performance_salary as performanceSalary,singel_meal as singelMeal, ");
		sql.append("stay_subsidy AS stay from ehr_base_salary where P_NUMBER = ? and use_time <= ? ORDER BY use_time DESC limit 1");
		List<Map<String, Object>> baseSalaryList = null;
		Map<String, Object> baseSalary = null;
		try {
			baseSalaryList = jdbcTemplate.queryForList(sql.toString(), pNumber, date);
		} catch (Exception e) {
			log.error("查询基本薪资失败", e);
			throw e;
		}
		if(baseSalaryList != null && baseSalaryList.size() > 0){
			BASE64Util.Base64DecodeMap(baseSalaryList);
			baseSalary = baseSalaryList.get(0);
		}
		return baseSalary;
	}

	@Override
	public Integer findSalaryCalculationStatus(String year, String month) {
		String sql = "SELECT `status`  FROM `ehr_salary_calculation_status` WHERE `YEAR` = ? AND `MONTH` = ?";
		Integer status = 0;
		List<Integer> statusList = null;
		try {
			statusList = jdbcTemplate.queryForList(sql, Integer.class, year, month);
		} catch (Exception e) {
			log.error("查询薪资计算状态失败", e);
			throw e;
		}
		if(statusList != null && statusList.size() > 0){
			status = statusList.get(0);
		}
		return status;
	}

	@Override
	public void updateSalaryCalculationStatus(String year, String month, Integer type) {
		String sql = "replace into ehr_salary_calculation_status (`YEAR`, `MONTH`, `status`) values (?, ?, ?)";
		try {
			jdbcTemplate.update(sql, year, month, type);
		} catch (Exception e) {
			log.error("新增或修复薪资计算状态失败", e);
			throw e;
		}
	}

	
}
