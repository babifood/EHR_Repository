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
			log.info("薪资计算========>修改计算状态");
		} catch (Exception e) {
			log.error("新增或修复薪资计算状态失败", e);
			throw e;
		}
	}

	@Override
	public Map<String, Object> findPersonFee(String year, String month, String pNumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.p_number AS pNumber, b.ADD_OTHER AS addOther, b.AFTER_OTHER_DEDUCTION AS afterOtherDeduction, ");
		sql.append("b.BEFORE_OTHER_DEDUCTION AS beforeDeduction, b.COMPENSATORY_BONUS AS compensatory, ");
		sql.append("b.HIGH_TEMPERATURE_ALLOWANCE AS highTem, b.INSURANCE_DEDUCTION AS insurance, ");
		sql.append("b.LOW_TEMPERATURE_ALLOWANCE as lowTem, b.MEAL_DEDUCTION AS mealDeduction, ");
		sql.append("b.MORNING_SHIFT_ALLOWANCE AS morningShift, b.NIGHT_SHIFT_ALLOWANCE AS nightShift, ");
		sql.append("b.OTHER_ALLOWANCE AS otherAllowance, b.OTHER_BONUS AS otherBonus, b.OVER_SALARY AS overSalary, ");
		sql.append("b.PROVIDENT_FUND_DEDUCTION AS providentFund, b.RESERVED1 AS reserved1, b.RESERVED2 AS reserved2, ");
		sql.append("b.RESERVED3 AS reserved3, b.RESERVED4 AS reserved4, b.RESERVED5 AS reserved5, b.RESERVED6 AS reserved6, ");
		sql.append("b.RESERVED7 AS reserved7, b.RESERVED8 AS reserved8, b.RESERVED9  AS reserved9, b.RESERVED10 AS reserved10, ");
		sql.append("b.SECURITY_BONUS AS `security`, b.DORM_BONUS AS dormBonus, ");
		sql.append("b.STAY_ALLOWANCE AS stay, c.DORM_BONUS AS dormBonus, c.DORM_DEDUCTION AS dormDeduction, c.DORM_FEE AS dormFee, ");
		sql.append("c.ELECTRICITY_FEE AS electricityFee,d.PERFORMANCE_SALARY AS pSalary,d.PERFORMANCE_SCORE AS performanceScore");
		sql.append("FROM ehr_person_basic_info a ");
		sql.append("LEFT JOIN ehr_allowances b ON a.p_number = b.P_NUMBER AND b.`YEAR` = ? AND b.`YEAR` = ? ");
		sql.append("LEFT JOIN ehr_dormitory_fee c ON a.p_number = c.P_NUMBER AND c.`YEAR` = ? AND c.`YEAR` = ? ");
		sql.append("LEFT JOIN ehr_performance d ON a.p_number = d.P_NUMBER AND d.`YEAR` = ? AND d.`YEAR` = ? ");
		sql.append("WHERE a.p_number = ?");
		List<Map<String, Object>> feelist = null;
		Map<String, Object> fees = null;
		try {
			feelist = jdbcTemplate.queryForList(sql.toString(), year, month, year, month, year, month, pNumber);
		} catch (Exception e) {
			log.error("查询薪资计算费项数据失败", e);
			throw e;
		}
		if(feelist != null && feelist.size() > 0){
			fees = feelist.get(0);
		}
		return fees;
	}

	
}
