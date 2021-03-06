package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.BaseSalaryDao;
import com.babifood.entity.BaseSalaryEntity;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.UtilString;

/**
 * 基础薪资dao层
 * @author wangguocheng
 *
 */
@Repository
public class BaseSalaryDaoImpl extends AuthorityControlDaoImpl implements BaseSalaryDao {

	private static Logger log = Logger.getLogger(BaseSalaryDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 查询基础薪资信息总数
	 */
	@Override
	public int queryCountOfBaseSalarys(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM ehr_base_salary a ");
		sql.append("INNER JOIN ehr_person_basic_info b ON a.P_NUMBER = b.p_number ");
		sql.append("WHERE a.is_delete = '0'");
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			sql.append(" AND a.P_NUMBER like '%" + params.get("pNumber") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			sql.append(" AND b.p_name like '%" + params.get("pName") + "%'");
		}
		int count = 0;
		try {
			sql = super.jointDataAuthoritySql("b.p_company_id","b.p_organization_id", sql);
			count = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询基础薪资信息总数失败", e);
			throw e;
		}
		return count;
	}

	/**
	 * 新增基础薪资信息
	 */
	@Override
	public void insertBaseSalary(BaseSalaryEntity baseSalary) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `ehr_base_salary` (`P_NUMBER`, `base_salary`, ");
		sql.append("`fixed_overtime_salary`, `post_salary`, `Call_subsidies`, ");
		sql.append("`company_salary`, `singel_meal`, `performance_salary`, `stay_subsidy`, ");
		sql.append("`create_time`, `work_type`, `use_time`, `is_delete`) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		try {
			jdbcTemplate.update(sql.toString(), baseSalary.getpNumber(),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getBaseSalary()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getFixedOverTimeSalary()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getPostSalary()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getCallSubsidies()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getCompanySalary()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getSingelMeal()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getPerformanceSalary()), 
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getStay()), 
					baseSalary.getCreateTime(),
					baseSalary.getWorkType(), baseSalary.getUseTime(), "0");
		} catch (Exception e) {
			log.error("新增基础薪资信息失败", e);
			throw e;
		}
	}

	/**
	 * 修改基础薪资信息
	 */
	@Override
	public void updateBaseSalary(BaseSalaryEntity baseSalary) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `ehr_base_salary` SET `P_NUMBER`=?, `base_salary`=?, `fixed_overtime_salary`=?, ");
		sql.append("`post_salary`=?, `Call_subsidies`=?, `company_salary`=?, `singel_meal`=?, ");
		sql.append("`performance_salary`=?, `stay_subsidy`= ?, `use_time`=? ");
		sql.append("WHERE `ID`=?");
		try {
			jdbcTemplate.update(sql.toString(), baseSalary.getpNumber(),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getBaseSalary()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getFixedOverTimeSalary()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getPostSalary()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getCallSubsidies()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getCompanySalary()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getSingelMeal()),
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getPerformanceSalary()), 
					BASE64Util.getDecodeStringTowDecimal(baseSalary.getStay()), baseSalary.getUseTime(),
					baseSalary.getId());
		} catch (Exception e) {
			log.error("修改基础薪资信息失败", e);
			throw e;
		}
	}

	/**
	 * 分页查询基础薪资信息
	 */
	@Override
	public List<Map<String, Object>> queryBaseSalaryList(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ID AS id, a.base_salary AS baseSalary, a.Call_subsidies AS callSubsidies, a.stay_subsidy AS stay, ");
		sql.append("a.company_salary AS companySalary, a.fixed_overtime_salary AS fixedOverTimeSalary, ");
		sql.append("a.performance_salary AS performanceSalary, a.post_salary AS postSalary, a.P_NUMBER AS pNumber, ");
		sql.append("a.singel_meal AS singelMeal, a.use_time AS useTime, b.p_name AS pName, a.WORK_TYPE as workType, ");
		sql.append("case a.WORK_TYPE WHEN '1' THEN '计件' WHEN '0' THEN '计时' end as workType1 ");
		sql.append("FROM (SELECT * FROM ehr_base_salary t WHERE t.use_time = ");
		sql.append("(SELECT MAX(use_time) FROM ehr_base_salary WHERE P_NUMBER = t.p_number) or t.use_time >= '" + params.get("lastMonth") + "') a ");
		sql.append("INNER JOIN ehr_person_basic_info b ON a.P_NUMBER = b.p_number ");
		sql.append("WHERE a.is_delete = '0' ");
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			sql.append(" AND a.P_NUMBER like '%" + params.get("pNumber") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			sql.append(" AND b.p_name like '%" + params.get("pName") + "%'");
		}
		sql = super.jointDataAuthoritySql("b.p_company_id","b.p_organization_id",sql);
		if(!UtilString.isEmpty(params.get("start")+"") && !UtilString.isEmpty(params.get("pageSize")+"")){
			sql.append(" order by b.p_number");
			sql.append(" limit ?, ?");
		}
		List<Map<String, Object>> baseSalaryList = null;
		try {
			if(!UtilString.isEmpty(params.get("start")+"") && !UtilString.isEmpty(params.get("pageSize")+"")){
				baseSalaryList = jdbcTemplate.queryForList(sql.toString(), params.get("start"), params.get("pageSize"));
			} else {
				baseSalaryList = jdbcTemplate.queryForList(sql.toString());
			}
			BASE64Util.Base64DecodeMap(baseSalaryList);
		} catch (Exception e) {
			log.error("分页查询基础薪资信息失败", e);
			throw e;
		}
		return baseSalaryList;
	}
	
	/**
	 * 分页查询基础薪资信息
	 */
	@Override
	public List<Map<String, Object>> getBaseSalaryRecord(String pNumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ID AS id, a.base_salary AS baseSalary, a.Call_subsidies AS callSubsidies, a.stay_subsidy AS stay, ");
		sql.append("a.company_salary AS companySalary, a.fixed_overtime_salary AS fixedOverTimeSalary, ");
		sql.append("a.performance_salary AS performanceSalary, a.post_salary AS postSalary, a.P_NUMBER AS pNumber, ");
		sql.append("a.singel_meal AS singelMeal, a.use_time AS useTime, b.p_name AS pName, a.WORK_TYPE as workType ");
		sql.append("FROM ehr_base_salary a ");
		sql.append("INNER JOIN ehr_person_basic_info b ON a.P_NUMBER = b.p_number ");
		sql.append("WHERE a.is_delete = '0' AND a.P_NUMBER = ?");
		List<Map<String, Object>> baseSalaryList = null;
		try {
			baseSalaryList = jdbcTemplate.queryForList(sql.toString(), pNumber);
		} catch (Exception e) {
			log.error("分页查询基础薪资信息失败", e);
			throw e;
		}
		return baseSalaryList;
	}

	/**
	 * 根据员工编号查询基础薪资信息
	 */
	@Override
	public List<Map<String, Object>> findBaseSalaryByPNumber(String pNumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID as id, P_NUMBER AS pNumber, base_salary as baseSalary, ");
		sql.append("fixed_overtime_salary AS fixedOverTimeSalary, post_salary AS postSalary, ");
		sql.append("Call_subsidies AS callSubsidies, company_salary as companySalary, stay_subsidy AS stay, ");
		sql.append("singel_meal AS singelMeal,performance_salary AS performanceSalary, ");
		sql.append("use_time AS useTime, is_delete as isDelete, work_type as workType ");
		sql.append("FROM ehr_base_salary ");
		sql.append("WHERE P_NUMBER = ? AND is_delete = '0'");
		List<Map<String, Object>> baseSalarys = null;
		try {
			baseSalarys = jdbcTemplate.queryForList(sql.toString(), pNumber);
		} catch (Exception e) {
			log.error("根据员工编号查询基础薪资信息失败", e);
			throw e;
		}
		return baseSalarys;
	}

	/**
	 * 修改基础薪资信息
	 */
	@Override
	public void deleteBaseSalaryById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `ehr_base_salary` SET `is_delete`= '1' ");
		sql.append("WHERE `ID`=?");
		try {
			jdbcTemplate.update(sql.toString(), id);
		} catch (Exception e) {
			log.error("修改基础薪资信息失败", e);
			throw e;
		}
	}

	@Override
	public void saveBaseSalaryList(List<Object[]> baseSalaryParam) {
		StringBuffer sql = new StringBuffer();
		sql.append("REPLACE INTO `ehr_base_salary` (`P_NUMBER`, `base_salary`, ");
		sql.append("`fixed_overtime_salary`, `post_salary`, `Call_subsidies`, ");
		sql.append("`company_salary`, `singel_meal`, `performance_salary`, `stay_subsidy`, ");
		sql.append("`create_time`, `work_type`, `use_time`, `is_delete`) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		try {
			jdbcTemplate.batchUpdate(sql.toString(), baseSalaryParam);
		} catch (Exception e) {
			log.error("批量新增基础薪资信息失败", e);
			throw e;
		}
		
	}

}
