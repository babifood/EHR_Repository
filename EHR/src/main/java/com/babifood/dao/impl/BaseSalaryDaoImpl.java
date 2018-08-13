package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.BaseSalaryDao;
import com.babifood.entity.BaseSalaryEntity;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.UtilString;

@Repository
public class BaseSalaryDaoImpl implements BaseSalaryDao {

	Logger log = LoggerFactory.getLogger(BaseSalaryDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int queryCountOfBaseSalarys(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM ehr_base_salary a ");
		sql.append("LEFT JOIN ehr_person_basic_info b ON a.P_NUMBER = b.p_number ");
		sql.append("WHERE a.is_delete = '0'");
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			sql.append(" AND a.P_NUMBER like '%" + params.get("pNumber") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			sql.append(" AND b.p_name like '%" + params.get("pName") + "%'");
		}
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询基础薪资信息总数失败", e.getMessage());
			throw e;
		}
		return count;
	}

	@Override
	public void insertBaseSalary(BaseSalaryEntity baseSalary) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `ehr_base_salary` (`P_NUMBER`, `base_salary`, ");
		sql.append("`fixed_overtime_salary`, `post_salary`, `Call_subsidies`, ");
		sql.append("`company_salary`, `singel_meal`, `performance_salary`, ");
		sql.append("`create_time`, `work_type`, `use_time`, `is_delete`) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		try {
			jdbcTemplate.update(sql.toString(), baseSalary.getpNumber(),
					BASE64Util.encode(baseSalary.getBaseSalary() + ""),
					BASE64Util.encode(baseSalary.getFixedOverTimeSalary() + ""),
					BASE64Util.encode(baseSalary.getPostSalary() + ""),
					BASE64Util.encode(baseSalary.getCallSubsidies() + ""),
					BASE64Util.encode(baseSalary.getCompanySalary() + ""),
					BASE64Util.encode(baseSalary.getSingelMeal() + ""),
					BASE64Util.encode(baseSalary.getPerformanceSalary() + ""), baseSalary.getCreateTime(),
					baseSalary.getWorkType(), baseSalary.getUseTime(), "0");
		} catch (Exception e) {
			log.error("新增基础薪资信息总数失败", e.getMessage());
			throw e;
		}
	}

	@Override
	public void updateBaseSalary(BaseSalaryEntity baseSalary) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `ehr_base_salary` SET `P_NUMBER`=?, `base_salary`=?, `fixed_overtime_salary`=?, ");
		sql.append("`post_salary`=?, `Call_subsidies`=?, `company_salary`=?, `singel_meal`=?, ");
		sql.append("`performance_salary`=?, `use_time`=? ");
		sql.append("WHERE `ID`=?");
		try {
			jdbcTemplate.update(sql.toString(), baseSalary.getpNumber(),
					BASE64Util.encode(baseSalary.getBaseSalary() + ""),
					BASE64Util.encode(baseSalary.getFixedOverTimeSalary() + ""),
					BASE64Util.encode(baseSalary.getPostSalary() + ""),
					BASE64Util.encode(baseSalary.getCallSubsidies() + ""),
					BASE64Util.encode(baseSalary.getCompanySalary() + ""),
					BASE64Util.encode(baseSalary.getSingelMeal() + ""),
					BASE64Util.encode(baseSalary.getPerformanceSalary() + ""), baseSalary.getUseTime());
		} catch (Exception e) {
			log.error("修改基础薪资信息失败", e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Map<String, Object>> queryBaseSalaryList(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ID AS id, a.base_salary AS baseSalary, a.Call_subsidies AS callSubsidies, ");
		sql.append("a.company_salary AS companySalary, a.fixed_overtime_salary AS fixedOverTimeSalary, ");
		sql.append("a.performance_salary AS performanceSalary, a.post_salary AS postSalary, a.P_NUMBER AS pNumber, ");
		sql.append("a.singel_meal AS singelMeal, a.use_time AS useTime, b.p_name AS pName, a.WORK_TYPE as workType ");
		sql.append("FROM ehr_base_salary a LEFT JOIN ehr_person_basic_info b ON a.P_NUMBER = b.p_number ");
		sql.append("WHERE a.is_delete = '0' ");
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			sql.append(" AND a.P_NUMBER like '%" + params.get("pNumber") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			sql.append(" AND b.p_name like '%" + params.get("pName") + "%'");
		}
		sql.append(" limit ?, ?");
		List<Map<String, Object>> baseSalaryList = null;
		try {
			baseSalaryList = jdbcTemplate.queryForList(sql.toString(), params.get("start"), params.get("pageSize"));
		} catch (Exception e) {
			log.error("分页查询基础薪资信息失败", e.getMessage());
			throw e;
		}
		return baseSalaryList;
	}

	@Override
	public List<Map<String, Object>> findBaseSalaryByPNumber(String pNumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID as id, P_NUMBER AS pNumber, base_salary as baseSalary, ");
		sql.append("fixed_overtime_salary AS fixedOverTimeSalary, post_salary AS postSalary, ");
		sql.append("Call_subsidies AS callSubsidies, company_salary as companySalary, ");
		sql.append("singel_meal AS singelMeal,performance_salary AS performanceSalary, ");
		sql.append("use_time AS useTime, is_delete as isDelete, work_type as workType ");
		sql.append("FROM ehr_base_salary ");
		sql.append("WHERE P_NUMBER = ? AND is_delete = '0'");
		List<Map<String, Object>> baseSalarys = null;
		try {
			baseSalarys = jdbcTemplate.queryForList(sql.toString(), pNumber);
		} catch (Exception e) {
			log.error("根据员工编号查询基础薪资信息失败", e.getMessage());
			throw e;
		}
		return baseSalarys;
	}

	@Override
	public void deleteBaseSalaryById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `ehr_base_salary` SET `is_delete`= '1' ");
		sql.append("WHERE `ID`=?");
		try {
			jdbcTemplate.update(sql.toString(), id);
		} catch (Exception e) {
			log.error("修改基础薪资信息失败", e.getMessage());
			throw e;
		}
	}

}
