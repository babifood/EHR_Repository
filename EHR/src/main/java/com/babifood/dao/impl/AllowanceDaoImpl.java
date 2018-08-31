package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.AllowanceDao;
import com.babifood.utils.UtilString;

@Repository
public class AllowanceDaoImpl implements AllowanceDao {
	
	private static Logger log = Logger.getLogger(AllowanceDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Map<String, Object>> findEmployAllowance(Map<String, Object> param) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.HIGH_TEMPERATURE_ALLOWANCE AS highTem, a.LOW_TEMPERATURE_ALLOWANCE AS lowTem,");
		sql.append("a.MORNING_SHIFT_ALLOWANCE AS morningShift, a.NIGHT_SHIFT_ALLOWANCE AS nightShift,");
		sql.append("a.STAY_ALLOWANCE AS stay,a.OTHER_ALLOWANCE AS otherAllowance,a.PERFORMANCE_BONUS AS performanceBonus, ");
		sql.append("a.SECURITY_BONUS `security`,a.COMPENSATORY_BONUS AS compensatory,a.OTHER_BONUS AS otherBonus,");
		sql.append("a.ADD_OTHER AS addOther, a.MEAL_DEDUCTION AS mealDeduction, a.DORM_DEDUCTION AS dormDeduction, ");
		sql.append("a.BEFORE_OTHER_DEDUCTION AS beforeDeduction, a.INSURANCE_DEDUCTION AS insurance, a.OVER_SALARY AS overSalary, ");
		sql.append("a.PROVIDENT_FUND_DEDUCTION AS providentFund, a.AFTER_OTHER_DEDUCTION AS afterOtherDeduction, ");
		sql.append("a.reserved1, a.reserved2, a.reserved3, a.reserved4, a.reserved5, a.reserved6, a.reserved7, ");
		sql.append("a.reserved8, a.reserved9, a.reserved10, a.year, a.month, a.P_NUMBER AS pNumber, ");
		sql.append("b.p_name as pName, c.DEPT_NAME AS organzationName, d.DEPT_NAME AS deptName, ");
		sql.append("e.DEPT_NAME AS officeName FROM EHR_ALLOWANCES a ");
		sql.append("INNER JOIN ehr_person_basic_info b ON a.p_number = b.p_number ");
		sql.append("LEFT JOIN ehr_dept c ON b.p_organization_id = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d ON b.p_department_id = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e ON b.p_section_office_id = e.DEPT_CODE where 1 = 1 ");
		if(!UtilString.isEmpty(param.get("pNumber") + "")){
			sql.append(" AND a.P_NUMBER = '" + param.get("pNumber") + "'");
		}
		if(!UtilString.isEmpty(param.get("month") + "")){
			sql.append(" AND a.`MONTH` = " + param.get("month"));
		}
		if(!UtilString.isEmpty(param.get("year") + "")){
			sql.append(" AND a.`YEAR` = " + param.get("year"));
		}
		if(!UtilString.isEmpty(param.get("number") + "")){
			sql.append(" AND a.P_NUMBER like '%" + param.get("number") + "%'");
		}
		if(!UtilString.isEmpty(param.get("pName") + "")){
			sql.append(" AND b.P_NAME like '%" + param.get("pName") + "%' ");
		}
		if(!UtilString.isEmpty(param.get("organzationName") + "")){
			sql.append(" AND c.DEPT_NAME like '%" + param.get("organzationName") + "%' ");
		}
		if(!UtilString.isEmpty(param.get("deptName") + "")){
			sql.append(" AND d.DEPT_NAME like '%" + param.get("deptName") + "%' ");
		}
		if(!UtilString.isEmpty(param.get("officeName") + "")){
			sql.append(" AND e.DEPT_NAME like '%" + param.get("officeName") + "%' ");
		}
		if(!UtilString.isEmpty(param.get("start") + "") && !UtilString.isEmpty(param.get("pageSize") + "")){
			sql.append("GROUP BY a.`YEAR`, a.`MONTH`, a.P_NUMBER ORDER BY a.`YEAR` DESC, a.`MONTH` DESC ");
			sql.append("limit ?, ?");
		}
		List<Map<String, Object>> allowances = null;
		try {
			if(UtilString.isEmpty(param.get("start")+"") || UtilString.isEmpty(param.get("pageSize")+"")){
				allowances = jdbcTemplate.queryForList(sql.toString());
			} else {
				allowances = jdbcTemplate.queryForList(sql.toString(), param.get("start"), param.get("pageSize"));
			}
		} catch (Exception e) {
			log.error("查询员工津贴和扣款信息失败，员工编号：" + param.get("pNumber"), e);
			throw e;
		}
		return allowances;
	}

	@Override
	public void saveEmployAllowances(List<Object[]> values) {
		StringBuffer sql = new StringBuffer();
		sql.append("REPLACE INTO `EHR_ALLOWANCES` (`YEAR`, `MONTH`, `P_NUMBER`, ");
		sql.append("`OVER_SALARY`, `HIGH_TEMPERATURE_ALLOWANCE`, `LOW_TEMPERATURE_ALLOWANCE`, ");
		sql.append("`NIGHT_SHIFT_ALLOWANCE`, `MORNING_SHIFT_ALLOWANCE`, `STAY_ALLOWANCE`, ");
		sql.append("`OTHER_ALLOWANCE`, `PERFORMANCE_BONUS`, `SECURITY_BONUS`, `COMPENSATORY_BONUS`, ");
		sql.append("`OTHER_BONUS`, `ADD_OTHER`, `MEAL_DEDUCTION`, `DORM_DEDUCTION`, ");
		sql.append("`BEFORE_OTHER_DEDUCTION`, `INSURANCE_DEDUCTION`, `PROVIDENT_FUND_DEDUCTION`, ");
		sql.append("`AFTER_OTHER_DEDUCTION`, `RESERVED1`, `RESERVED2`, `RESERVED3`, `RESERVED4`, ");
		sql.append("`RESERVED5`, `RESERVED6`, `RESERVED7`, `RESERVED8`, `RESERVED9`, `RESERVED10`) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		try {
			jdbcTemplate.batchUpdate(sql.toString(), values);
		} catch (Exception e) {
			log.error("批量插入员工补贴、扣款信息失败", e);
			throw e;
		}
	}

	@Override
	public Integer getAllowanceCount(Map<String, Object> param) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM EHR_ALLOWANCES a ");
		sql.append("INNER JOIN ehr_person_basic_info b ON a.p_number = b.p_number ");
		sql.append("LEFT JOIN ehr_dept c ON b.p_organization_id = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d ON b.p_department_id = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e ON b.p_section_office_id = e.DEPT_CODE where 1 = 1 ");
		if(!UtilString.isEmpty(param.get("number") + "")){
			sql.append(" AND a.P_NUMBER like '%" + param.get("number") + "%'");
		}
		if(!UtilString.isEmpty(param.get("pName") + "")){
			sql.append(" AND b.P_NAME like '%" + param.get("pName") + "%' ");
		}
		if(!UtilString.isEmpty(param.get("organzationName") + "")){
			sql.append(" AND c.DEPT_NAME like '%" + param.get("organzationName") + "%' ");
		}
		if(!UtilString.isEmpty(param.get("deptName") + "")){
			sql.append(" AND d.DEPT_NAME like '%" + param.get("deptName") + "%' ");
		}
		if(!UtilString.isEmpty(param.get("officeName") + "")){
			sql.append(" AND e.DEPT_NAME like '%" + param.get("officeName") + "%' ");
		}
		Integer total = 0;
		try {
			total = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询津贴、扣款信息列表失败", e);
			throw e;
		}
		return total;
	}

}
