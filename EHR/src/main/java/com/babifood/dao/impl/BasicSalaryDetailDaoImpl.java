package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.BasicSalaryDetailDao;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.UtilString;

@Repository
public class BasicSalaryDetailDaoImpl extends AuthorityControlDaoImpl implements BasicSalaryDetailDao {

	private static Logger log = Logger.getLogger(BasicSalaryDetailDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 查询薪资明细数量
	 */
	@Override
	public Integer getSalaryDetailsCount(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM ehr_basic_salary_details a ");
		sql.append("INNER JOIN ehr_person_basic_info f ON a.P_NUMBER = f.p_number ");
		sql.append("LEFT JOIN ehr_dept c on f.P_DEPARTMENT_ID = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d on f.P_SECTION_OFFICE_ID = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e on f.P_GROUP_ID = e.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept g on f.P_COMPANY_ID = g.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept b on f.P_ORGANIZATION_ID = b.DEPT_CODE WHERE 1=1 ");
		if (!UtilString.isEmpty(params.get("companyCode") + "")) {
			sql.append(" AND f.P_COMPANY_ID like '%" + params.get("companyCode") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			sql.append(" AND a.p_number like '%" + params.get("pNumber") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			sql.append(" AND f.p_name like '%" + params.get("pName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("organzationName") + "")) {
			sql.append(" AND b.DEPT_NAME like '%" + params.get("organzationName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("deptName") + "")) {
			sql.append(" AND c.DEPT_NAME like '%" + params.get("deptName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("officeName") + "")) {
			sql.append(" AND d.DEPT_NAME like '%" + params.get("officeName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("groupName") + "")) {
			sql.append(" AND e.DEPT_NAME like '%" + params.get("groupName") + "%'");
		}
		Integer count = 0;
		try {
			sql = super.jointDataAuthoritySql("f.P_COMPANY_ID","f.P_ORGANIZATION_ID",sql);
			count = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询薪资明细数量失败", e);
			throw e;
		}
		return count;
	}

	/**
	 * 查询薪资明细列表
	 */
	@Override
	public List<Map<String, Object>> getPageSalaryDetails(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ID AS id, a.`YEAR` AS `year`, a.`MONTH` AS `month`, a.P_NUMBER AS pNumber, ");
		sql.append("a.ABSENCE_HOURS AS absenceHours, a.AFTER_OTHER_DEDUCTION AS afterDeduction, ");
		sql.append("a.ATTENDANCE_HOURS AS attendanceHours, a.BASE_SALARY AS baseSalary, a.ADD_OTHER AS addOther, ");
		sql.append("a.BEFORE_OTHER_DEDUCTION AS beforeDeduction, a.CALL_SUBSIDIES AS callSubsidies, ");
		sql.append("a.COMPANION_PARENTAL_DEDUCTION AS companionParentalDeduction, a.COMPANY_SALARY AS companySalary, ");
		sql.append("a.COMPENSATORY_BONUS AS compensatory, a.DORM_DEDUCTION as dormDeduction, g.DEPT_NAME AS companyName,");
		sql.append("a.FIXED_OVERTIME_SALARY AS fixedOverTimeSalary, a.FUNERAL_DEDUCTION funeralDeduction, ");
		sql.append("a.HIGH_TEMPERATURE_ALLOWANCE AS highTem, a.INSURANCE_DEDUCTION AS insurance, ");
		sql.append("a.LOW_TEMPERATURE_ALLOWANCE AS lowTem, a.MARRIAGE_DEDUCTION mealDeduction, ");
		sql.append("a.MARRIAGE_DEDUCTION AS marriageDeduction, a.MORNING_SHIFT_ALLOWANCE AS morningShift, ");
		sql.append("a.NIGHT_SHIFT_ALLOWANCE AS nightShift, a.PROVIDENT_FUND_DEDUCTION as providentFund, ");
		sql.append("a.OTHER_ALLOWANCE AS otherAllowance, a.OTHER_BONUS otherBonus, a.OVER_SALARY AS overSalary, ");
		sql.append("a.PARENTAL_DEDUCTION AS parentalDeduction, a.PERFORMANCE_BONUS AS performanceBonus, ");
		sql.append("a.PERSONAL_TAX AS personalTax, a.POST_SALARY AS postSalary, a.ONBOARDING_DEPARTURE AS onboarding, ");
		sql.append("a.REAL_WAGES AS realWages, a.RELAXATION AS relaxation,a.RICE_STICK AS riceStick, a.SECURITY_BONUS as security, ");
		sql.append("a.SICK_DEDUCTION AS sickDeduction, a.STAY_ALLOWANCE AS stay, a.THING_DEDUCTION AS thingDeduction, ");
		sql.append("a.TOTAL_DEDUCTION AS totalDeduction, a.TRAIN_DEDUCTION as trainDeduction, a.WAGE_PAYABLE AS wagePayable, ");
		sql.append("a.YEAR_DEDUCTION AS yearDeduction, b.DEPT_NAME AS organizationName, c.DEPT_NAME AS deptName, ");
		sql.append("a.LATER_AND_LEAVE_DEDUCTION AS laterAndLeaveDeduction, a.COMPLETION_DEDUCTION AS completionDeduction, ");
		sql.append("d.DEPT_NAME AS officeName, e.DEPT_NAME AS groupName, f.p_name AS pName, h.post_name as postName ");
		sql.append("FROM ehr_basic_salary_details a ");
		sql.append("INNER JOIN ehr_person_basic_info f ON a.P_NUMBER = f.p_number ");
		sql.append("LEFT JOIN ehr_dept c on f.P_DEPARTMENT_ID = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d on f.P_SECTION_OFFICE_ID = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e on f.P_GROUP_ID = e.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept g on f.P_COMPANY_ID = g.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_post h on f.P_POST_ID = h.post_id ");
		sql.append("LEFT JOIN ehr_dept b on f.P_ORGANIZATION_ID = b.DEPT_CODE WHERE 1=1 ");
		if (!UtilString.isEmpty(params.get("companyCode") + "")) {
			sql.append(" AND f.P_COMPANY_ID like '%" + params.get("companyCode") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			sql.append(" AND a.p_number like '%" + params.get("pNumber") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			sql.append(" AND f.p_name like '%" + params.get("pName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("organzationName") + "")) {
			sql.append(" AND b.DEPT_NAME like '%" + params.get("organzationName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("deptName") + "")) {
			sql.append(" AND c.DEPT_NAME like '%" + params.get("deptName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("officeName") + "")) {
			sql.append(" AND d.DEPT_NAME like '%" + params.get("officeName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("groupName") + "")) {
			sql.append(" AND e.DEPT_NAME like '%" + params.get("groupName") + "%'");
		}
		sql = super.jointDataAuthoritySql("f.P_COMPANY_ID","f.P_ORGANIZATION_ID",sql);
		if(!UtilString.isEmpty(params.get("start") + "") && !UtilString.isEmpty(params.get("start") + "")){
			sql.append(" GROUP BY a.`YEAR`, a.`MONTH`, a.P_NUMBER ORDER BY a.`YEAR` DESC, a.`MONTH` DESC");
			sql.append(" limit ?, ?");
		}
		List<Map<String, Object>> salaryDetailList = null;
		try {
			if(!UtilString.isEmpty(params.get("start") + "") && !UtilString.isEmpty(params.get("start") + "")){
				salaryDetailList = jdbcTemplate.queryForList(sql.toString(), params.get("start"), params.get("pageSize"));
			} else {
				salaryDetailList = jdbcTemplate.queryForList(sql.toString());
			}
		} catch (Exception e) {
			log.error("查询薪资明细列表失败", e);
			throw e;
		}
		return salaryDetailList;
	}

	/**
	 * 批量新增薪资明细
	 */
	@Override
	public void saveBasicSalaryDetailEntityList(List<Map<String, Object>> salaryDetails) {
		StringBuffer sql = new StringBuffer();
		sql.append("REPLACE INTO `ehr_basic_salary_details` (`YEAR`, `MONTH`, `P_NUMBER`,  ");
		sql.append("`Attendance_hours`, `ABSENCE_HOURS`, `BASE_SALARY`, `FIXED_OVERTIME_SALARY`, ");
		sql.append("`POST_SALARY`, `CALL_SUBSIDIES`, `COMPANY_SALARY`, `OVER_SALARY`, ");
		sql.append("`RICE_STICK`, `HIGH_TEMPERATURE_ALLOWANCE`, `LOW_TEMPERATURE_ALLOWANCE`, ");
		sql.append("`MORNING_SHIFT_ALLOWANCE`, `NIGHT_SHIFT_ALLOWANCE`, `STAY_ALLOWANCE`, ");
		sql.append("`other_allowance`, `performance_bonus`, `SECURITY_BONUS`, `COMPENSATORY_BONUS`, ");
		sql.append("`OTHER_BONUS`, `ADD_OTHER`, `MEAL_DEDUCTION`, `DORM_DEDUCTION`, `BEFORE_OTHER_DEDUCTION`, ");
		sql.append("`INSURANCE_DEDUCTION`, `PROVIDENT_FUND_DEDUCTION`, `AFTER_OTHER_DEDUCTION`, ");
		sql.append("`LATER_AND_LEAVE_DEDUCTION`, `COMPLETION_DEDUCTION`, ");
		sql.append("`YEAR_DEDUCTION`, `RELAXATION`, `THING_DEDUCTION`, `SICK_DEDUCTION`, `TRAIN_DEDUCTION`, ");
		sql.append("`PARENTAL_DEDUCTION`, `MARRIAGE_DEDUCTION`, `COMPANION_PARENTAL_DEDUCTION`, `FUNERAL_DEDUCTION`, ");
		sql.append("`ONBOARDING_DEPARTURE`, `TOTAL_DEDUCTION`, `WAGE_PAYABLE`, `PERSONAL_TAX`, `REAL_WAGES`) VALUES");
		for(int i = 0 ;i < salaryDetails.size() ; i++){
			Map<String, Object> salaryDetail = salaryDetails.get(i);
			sql.append("(" +(UtilString.isEmpty(salaryDetail.get("year")+"") ? null : "'" + salaryDetail.get("year") + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.get("month")+"") ? null : "'" + salaryDetail.get("month") + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.get("pNumber")+"") ? null : "'" + salaryDetail.get("pNumber") + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.get("attendanceHours")+"") ? null : "'" + salaryDetail.get("attendanceHours") + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.get("absenceHours")+"") ? null : "'" + salaryDetail.get("absenceHours") + "'") + ",");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("baseSalary")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("fixedOvertimeSalary")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("postSalary")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("callSubsidies")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("companySalary")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("overSalary")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("riceStick")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("highTem")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("lowTem")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("morningShift")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("nightShift")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("stay")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("otherAllowance")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("performanceBonus")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("security")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("compensatory")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("otherBonus")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("addOther")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("mealDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("dormDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("beforeDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("insurance")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("providentFund")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("afterDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("laterAndLeaveDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("completionDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("yearDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("relaxation")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("thingDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("sickDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("trainDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("parentalDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("marriageDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("companionParentalDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("funeralDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("onboarding")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("totalDeduction")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("wagePayable")+"") + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.get("personalTax")+"") + "',");
			if( i == salaryDetails.size() - 1){
				sql.append("'" + BASE64Util.encode(salaryDetail.get("realWages")+"") + "')");
			} else {
				sql.append("'" + BASE64Util.encode(salaryDetail.get("realWages")+"") + "'),");
			}
		}
		try {
			jdbcTemplate.update(sql.toString());
		} catch (Exception e) {
			log.error("批量插入数据失败", e);
			throw e;
		}
	}

}
