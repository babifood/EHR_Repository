package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.SalaryDetailDao;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.SalaryDetailEntity;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.UtilString;

@Repository
public class SalaryDetailDaoImpl extends AuthorityControlDaoImpl implements SalaryDetailDao {

	private static Logger log = Logger.getLogger(SalaryDetailDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 查询员工对应月份薪资明细
	 */
	@Override
	public Map<String, Object> findCurrentMonthSalary(String year, String month, String pNumber) {
		String sql = "SELECT * FROM EHR_SALARY_DETAILS WHERE YEAR = ? AND MONTH = ? AND P_NUMBER = ?";
		List<Map<String, Object>> salaryDetails = null;
		Map<String, Object> salaryDetail = null;
		try {
			salaryDetails = jdbcTemplate.queryForList(sql, year, month, pNumber);
		} catch (Exception e) {
			log.error("查询当月薪资明细失败,pNumber=" + pNumber, e);
			throw e;
		}
		if (salaryDetails != null && salaryDetails.size() > 0) {
			salaryDetail = salaryDetails.get(0);
		}
		return salaryDetail;
	}

	/**
	 * 修改薪资明细
	 */
	@Override
	public void updateSalaryDetail(SalaryDetailEntity salaryDetail) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `EHR_SALARY_DETAILS` SET `YEAR`=?, `MONTH`=?, `P_NUMBER`= ?, ");
		sql.append("`COMPANY_CODE`=?, `ORGANIZATION_CODE`=?, `DEPT_CODE`=?, `OFFICE_CODE`=?, ");
		sql.append("`GROUP_CODE`=?, `POST`=?, `ATTENDANCE_HOURS`=?, `ABSENCE_HOURS`=?, `BASE_SALARY`= ?, ");
		sql.append("`FIXED_OVERTIME_SALARY`=?, `POST_SALARY`=?, `CALL_SUBSIDIES`=?, `COMPANY_SALARY`= ?, ");
		sql.append("`OVER_SALARY`=?, `Rice_stick`=?, `high_temperature_allowance`=?, ");
		sql.append("`LOW_TEMPERATURE_ALLOWANCE`=?, `MORNING_SHIFT_ALLOWANCE`=?, `NIGHT_SHIFT_ALLOWANCE`= ?, ");
		sql.append("`STAY_ALLOWANCE`=?, `OTHER_ALLOWANCE`=?, `PERFORMANCE_BONUS`=?, `SECURITY_BONUS`= ?, ");
		sql.append("`COMPENSATORY_BONUS`=?, `OTHER_BONUS`=?, `ADD_OTHER`=?, `MEAL_DEDUCTION`=?, ");
		sql.append("`DORM_DEDUCTION`=?, `BEFORE_OTHER_DEDUCTION`=?, `INSURANCE_DEDUCTION`=?, ");
		sql.append("`PROVIDENT_FUND_DEDUCTION`= ?, `AFTER_OTHER_DEDUCTION`= ?, `YEAR_DEDUCTION`= ?, ");
		sql.append("`LATER_AND_LEAVE_DEDUCTION` = ?, `COMPLETION_DEDUCTION` = ?, ");
		sql.append("`RELAXATION`=?, `THING_DEDUCTION`=?, `SICK_DEDUCTION`=?, `TRAIN_DEDUCTION`=?, ");
		sql.append("`PARENTAL_DEDUCTION`=?, `MARRIAGE_DEDUCTION`=?, `COMPANION_PARENTAL_DEDUCTION`=?, ");
		sql.append("`FUNERAL_DEDUCTION`=?, `ONBOARDING_DEPARTURE`=?, `TOTAL_DEDUCTION`=?, `WAGE_PAYABLE`=?, ");
		sql.append("`PERSONAL_TAX`=?, `REAL_WAGES`=? WHERE `ID`=?");
		try {
			jdbcTemplate.update(sql.toString(), salaryDetail.getYear(), salaryDetail.getMonth(),
					salaryDetail.getpNumber(), salaryDetail.getCompanyCode(), salaryDetail.getOrganizationCode(),
					salaryDetail.getDeptCode(), salaryDetail.getOfficeCode(), salaryDetail.getGroupCode(),
					salaryDetail.getPost(), salaryDetail.getAttendanceHours(), salaryDetail.getAbsenceHours(),
					BASE64Util.encode(salaryDetail.getBaseSalary()),
					BASE64Util.encode(salaryDetail.getFixedOvertimeSalary()),
					BASE64Util.encode(salaryDetail.getPostSalary()), BASE64Util.encode(salaryDetail.getCallSubsidies()),
					BASE64Util.encode(salaryDetail.getCompanySalary()), BASE64Util.encode(salaryDetail.getOverSalary()),
					BASE64Util.encode(salaryDetail.getRiceStick()),
					BASE64Util.encode(salaryDetail.getHighTem()),
					BASE64Util.encode(salaryDetail.getLowTem()),
					BASE64Util.encode(salaryDetail.getMorningShift()),
					BASE64Util.encode(salaryDetail.getNightShift()),
					BASE64Util.encode(salaryDetail.getStay()),
					BASE64Util.encode(salaryDetail.getOtherAllowance()),
					BASE64Util.encode(salaryDetail.getPerformanceBonus()),
					BASE64Util.encode(salaryDetail.getSecurity()),
					BASE64Util.encode(salaryDetail.getCompensatory()),
					BASE64Util.encode(salaryDetail.getOtherBonus()), BASE64Util.encode(salaryDetail.getAddOther()),
					BASE64Util.encode(salaryDetail.getMealDeduction()),
					BASE64Util.encode(salaryDetail.getDormDeduction()),
					BASE64Util.encode(salaryDetail.getBeforeDeduction()),
					BASE64Util.encode(salaryDetail.getInsurance()), BASE64Util.encode(salaryDetail.getProvidentFund()),
					BASE64Util.encode(salaryDetail.getAfterDeduction()),
					BASE64Util.encode(salaryDetail.getYearDeduction()),
					BASE64Util.encode(salaryDetail.getLaterAndLeaveDeduction()),
					BASE64Util.encode(salaryDetail.getCompletionDeduction()),
					BASE64Util.encode(salaryDetail.getRelaxation()), BASE64Util.encode(salaryDetail.getThingDeduction()),
					BASE64Util.encode(salaryDetail.getSickDeduction()),
					BASE64Util.encode(salaryDetail.getTrainDeduction()),
					BASE64Util.encode(salaryDetail.getParentalDeduction()),
					BASE64Util.encode(salaryDetail.getMarriageDeduction()),
					BASE64Util.encode(salaryDetail.getCompanionParentalDeduction()),
					BASE64Util.encode(salaryDetail.getFuneralDeduction()),
					BASE64Util.encode(salaryDetail.getOnboarding()),
					BASE64Util.encode(salaryDetail.getTotalDeduction()),
					BASE64Util.encode(salaryDetail.getWagePayable()), BASE64Util.encode(salaryDetail.getPersonalTax()),
					BASE64Util.encode(salaryDetail.getRealWages()), salaryDetail.getId());
		} catch (Exception e) {
			log.error("修改当月薪资明细失败,pNumber=" + salaryDetail.getpNumber(), e);
			throw e;
		}
	}

	/**
	 * 新增薪资明细
	 */
	@Override
	public void addSalaryDetail(SalaryDetailEntity salaryDetail) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `ehr_salary_details` (`YEAR`, `MONTH`, `P_NUMBER`, `COMPANY_CODE`, ");
		sql.append("`ORGANIZATION_CODE`, `DEPT_CODE`, `OFFICE_CODE`, `GROUP_CODE`, `POST`, ");
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
		sql.append("`ONBOARDING_DEPARTURE`, `TOTAL_DEDUCTION`, `WAGE_PAYABLE`, `PERSONAL_TAX`, `REAL_WAGES`) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		try {
			jdbcTemplate.update(sql.toString(), salaryDetail.getYear(), salaryDetail.getMonth(),
					salaryDetail.getpNumber(), salaryDetail.getCompanyCode(), salaryDetail.getOrganizationCode(),
					salaryDetail.getDeptCode(), salaryDetail.getOfficeCode(), salaryDetail.getGroupCode(),
					salaryDetail.getPost(), salaryDetail.getAttendanceHours(), salaryDetail.getAbsenceHours(),
					BASE64Util.encode(salaryDetail.getBaseSalary()),
					BASE64Util.encode(salaryDetail.getFixedOvertimeSalary()),
					BASE64Util.encode(salaryDetail.getPostSalary()), BASE64Util.encode(salaryDetail.getCallSubsidies()),
					BASE64Util.encode(salaryDetail.getCompanySalary()), BASE64Util.encode(salaryDetail.getOverSalary()),
					BASE64Util.encode(salaryDetail.getRiceStick()),
					BASE64Util.encode(salaryDetail.getHighTem()),
					BASE64Util.encode(salaryDetail.getLowTem()),
					BASE64Util.encode(salaryDetail.getMorningShift()),
					BASE64Util.encode(salaryDetail.getNightShift()),
					BASE64Util.encode(salaryDetail.getStay()),
					BASE64Util.encode(salaryDetail.getOtherAllowance()),
					BASE64Util.encode(salaryDetail.getPerformanceBonus()),
					BASE64Util.encode(salaryDetail.getSecurity()),
					BASE64Util.encode(salaryDetail.getCompensatory()),
					BASE64Util.encode(salaryDetail.getOtherBonus()), 
					BASE64Util.encode(salaryDetail.getAddOther()),
					BASE64Util.encode(salaryDetail.getMealDeduction()),
					BASE64Util.encode(salaryDetail.getDormDeduction()),
					BASE64Util.encode(salaryDetail.getBeforeDeduction()),
					BASE64Util.encode(salaryDetail.getInsurance()), 
					BASE64Util.encode(salaryDetail.getProvidentFund()),
					BASE64Util.encode(salaryDetail.getAfterDeduction()),
					BASE64Util.encode(salaryDetail.getLaterAndLeaveDeduction()),
					BASE64Util.encode(salaryDetail.getCompletionDeduction()),
					BASE64Util.encode(salaryDetail.getYearDeduction()), 
					BASE64Util.encode(salaryDetail.getRealWages()),
					BASE64Util.encode(salaryDetail.getThingDeduction()),
					BASE64Util.encode(salaryDetail.getSickDeduction()),
					BASE64Util.encode(salaryDetail.getTrainDeduction()),
					BASE64Util.encode(salaryDetail.getParentalDeduction()),
					BASE64Util.encode(salaryDetail.getMarriageDeduction()),
					BASE64Util.encode(salaryDetail.getCompanionParentalDeduction()),
					BASE64Util.encode(salaryDetail.getFuneralDeduction()),
					BASE64Util.encode(salaryDetail.getOnboarding()),
					BASE64Util.encode(salaryDetail.getTotalDeduction()),
					BASE64Util.encode(salaryDetail.getWagePayable()), 
					BASE64Util.encode(salaryDetail.getPersonalTax()),
					BASE64Util.encode(salaryDetail.getRealWages()));
		} catch (Exception e) {
			log.error("新增当月薪资明细失败,pNumber=" + salaryDetail.getpNumber(), e);
			throw e;
		}
	}

	/**
	 * 查询薪资明细数量
	 */
	@Override
	public Integer getSalaryDetailsCount(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM ehr_salary_details a ");
		sql.append("LEFT JOIN ehr_dept b on a.ORGANIZATION_CODE = b.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept c on a.DEPT_CODE = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d on a.OFFICE_CODE = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e on a.GROUP_CODE = e.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept g on a.COMPANY_CODE = g.DEPT_CODE ");
		sql.append("INNER JOIN ehr_person_basic_info f ON a.P_NUMBER = f.p_number WHERE 1=1 ");
		if (!UtilString.isEmpty(params.get("resourceCode") + "")) {
			sql.append(" AND (a.COMPANY_CODE = '" + params.get("resourceCode") + 
					"' or a.ORGANIZATION_CODE = '" + params.get("resourceCode") + "')");
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
			sql = super.jointDataAuthoritySql("a.COMPANY_CODE","a.ORGANIZATION_CODE",sql);
			count = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询薪资明细数量失败", e);
			throw e;
		}
		StringBuffer basic_sql = new StringBuffer();
		basic_sql.append("SELECT COUNT(*) FROM ehr_basic_salary_details a ");
		basic_sql.append("INNER JOIN ehr_person_basic_info f ON a.P_NUMBER = f.p_number ");
		basic_sql.append("LEFT JOIN ehr_dept c on f.P_DEPARTMENT_ID = c.DEPT_CODE ");
		basic_sql.append("LEFT JOIN ehr_dept d on f.P_SECTION_OFFICE_ID = d.DEPT_CODE ");
		basic_sql.append("LEFT JOIN ehr_dept e on f.P_GROUP_ID = e.DEPT_CODE ");
		basic_sql.append("LEFT JOIN ehr_dept g on f.P_COMPANY_ID = g.DEPT_CODE ");
		basic_sql.append("LEFT JOIN ehr_dept b on f.P_ORGANIZATION_ID = b.DEPT_CODE WHERE f.p_oa_and_ehr = 'EHR' ");
		if (!UtilString.isEmpty(params.get("resourceCode") + "")) {
			basic_sql.append(" AND (f.P_COMPANY_ID = '" + params.get("resourceCode") + 
					"' or f.P_ORGANIZATION_ID = '" + params.get("resourceCode") + "')");
		}
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			basic_sql.append(" AND a.p_number like '%" + params.get("pNumber") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			basic_sql.append(" AND f.p_name like '%" + params.get("pName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("organzationName") + "")) {
			basic_sql.append(" AND b.DEPT_NAME like '%" + params.get("organzationName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("deptName") + "")) {
			basic_sql.append(" AND c.DEPT_NAME like '%" + params.get("deptName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("officeName") + "")) {
			basic_sql.append(" AND d.DEPT_NAME like '%" + params.get("officeName") + "%'");
		}
		if (!UtilString.isEmpty(params.get("groupName") + "")) {
			basic_sql.append(" AND e.DEPT_NAME like '%" + params.get("groupName") + "%'");
		}
		Integer count1 = 0;
		try {
			basic_sql = super.jointDataAuthoritySql("g.DEPT_CODE","b.DEPT_CODE",basic_sql);
			count1 = jdbcTemplate.queryForInt(basic_sql.toString());
		} catch (Exception e) {
			log.error("查询薪资明细数量失败", e);
			throw e;
		}
		return count + count1;
	}

	/**
	 * 查询薪资明细列表basic_sql.append("LEFT JOIN ehr_base_salary h on h.P_NUMBER = f.P_NUMBER ");
	 */
	@Override
	public List<Map<String, Object>> getPageSalaryDetails(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.* from ((SELECT a.ID AS id, a.`YEAR` AS `year`, a.`MONTH` AS `month`, a.P_NUMBER AS pNumber, ");
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
		sql.append("d.DEPT_NAME AS officeName, e.DEPT_NAME AS groupName, f.p_name AS pName, h.post_name as postName,'OA' as oaandehr ");
		sql.append("FROM ehr_salary_details a ");
		sql.append("INNER JOIN ehr_person_basic_info f ON a.P_NUMBER = f.p_number ");
		sql.append("LEFT JOIN ehr_dept c on f.P_DEPARTMENT_ID = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d on f.P_SECTION_OFFICE_ID = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e on f.P_GROUP_ID = e.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept g on f.P_COMPANY_ID = g.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_post h on f.P_POST_ID = h.post_id ");
		sql.append("LEFT JOIN ehr_dept b on f.P_ORGANIZATION_ID = b.DEPT_CODE WHERE 1=1 ");
		if (!UtilString.isEmpty(params.get("resourceCode") + "")) {
			sql.append(" AND (f.P_COMPANY_ID = '" + params.get("resourceCode") + 
					"' or f.P_ORGANIZATION_ID = '" + params.get("resourceCode") + "')");
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
		sql = super.jointDataAuthoritySql("a.COMPANY_CODE","a.ORGANIZATION_CODE",sql);
		sql.append(" ) union (");
		sql.append("SELECT a.ID AS id, a.`YEAR` AS `year`, a.`MONTH` AS `month`, a.P_NUMBER AS pNumber, ");
		sql.append("a.ABSENCE_HOURS AS absenceHours, a.AFTER_OTHER_DEDUCTION AS afterDeduction, ");
		sql.append("a.ATTENDANCE_HOURS AS attendanceHours, i.BASE_SALARY AS baseSalary, a.ADD_OTHER AS addOther, ");
		sql.append("a.BEFORE_OTHER_DEDUCTION AS beforeDeduction, a.CALL_SUBSIDIES AS callSubsidies, ");
		sql.append("a.COMPANION_PARENTAL_DEDUCTION AS companionParentalDeduction, i.COMPANY_SALARY AS companySalary, ");
		sql.append("a.COMPENSATORY_BONUS AS compensatory, a.DORM_DEDUCTION as dormDeduction, g.DEPT_NAME AS companyName,");
		sql.append("i.FIXED_OVERTIME_SALARY AS fixedOverTimeSalary, a.FUNERAL_DEDUCTION funeralDeduction, ");
		sql.append("a.HIGH_TEMPERATURE_ALLOWANCE AS highTem, a.INSURANCE_DEDUCTION AS insurance, ");
		sql.append("a.LOW_TEMPERATURE_ALLOWANCE AS lowTem, a.MARRIAGE_DEDUCTION mealDeduction, ");
		sql.append("a.MARRIAGE_DEDUCTION AS marriageDeduction, a.MORNING_SHIFT_ALLOWANCE AS morningShift, ");
		sql.append("a.NIGHT_SHIFT_ALLOWANCE AS nightShift, a.PROVIDENT_FUND_DEDUCTION as providentFund, ");
		sql.append("a.OTHER_ALLOWANCE AS otherAllowance, a.OTHER_BONUS otherBonus, a.OVER_SALARY AS overSalary, ");
		sql.append("a.PARENTAL_DEDUCTION AS parentalDeduction, a.PERFORMANCE_BONUS AS performanceBonus, ");
		sql.append("a.PERSONAL_TAX AS personalTax, i.POST_SALARY AS postSalary, a.ONBOARDING_DEPARTURE AS onboarding, ");
		sql.append("a.REAL_WAGES AS realWages, a.RELAXATION AS relaxation,a.RICE_STICK AS riceStick, a.SECURITY_BONUS as security, ");
		sql.append("a.SICK_DEDUCTION AS sickDeduction, a.STAY_ALLOWANCE AS stay, a.THING_DEDUCTION AS thingDeduction, ");
		sql.append("a.TOTAL_DEDUCTION AS totalDeduction, a.TRAIN_DEDUCTION as trainDeduction, a.WAGE_PAYABLE AS wagePayable, ");
		sql.append("a.YEAR_DEDUCTION AS yearDeduction, b.DEPT_NAME AS organizationName, c.DEPT_NAME AS deptName, ");
		sql.append("a.LATER_AND_LEAVE_DEDUCTION AS laterAndLeaveDeduction, a.COMPLETION_DEDUCTION AS completionDeduction, ");
		sql.append("d.DEPT_NAME AS officeName, e.DEPT_NAME AS groupName, f.p_name AS pName, h.post_name as postName,'EHR' as oaandehr ");
		sql.append("FROM ehr_basic_salary_details a ");
		sql.append("INNER JOIN ehr_person_basic_info f ON a.P_NUMBER = f.p_number ");
		sql.append("LEFT JOIN ehr_dept c on f.P_DEPARTMENT_ID = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d on f.P_SECTION_OFFICE_ID = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e on f.P_GROUP_ID = e.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept g on f.P_COMPANY_ID = g.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_post h on f.P_POST_ID = h.post_id ");
		sql.append("LEFT JOIN ehr_dept b on f.P_ORGANIZATION_ID = b.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_base_salary i on i.p_number = a.P_NUMBER WHERE f.p_oa_and_ehr = 'EHR' ");
		if (!UtilString.isEmpty(params.get("resourceCode") + "")) {
			sql.append(" AND (f.P_COMPANY_ID = '" + params.get("resourceCode") + 
					"' or f.P_ORGANIZATION_ID = '" + params.get("resourceCode") + "')");
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
		sql.append(")) t");
		if(!UtilString.isEmpty(params.get("start") + "") && !UtilString.isEmpty(params.get("start") + "")){
			sql.append(" GROUP BY t.`YEAR`, t.`MONTH`, t.pNumber ORDER BY t.`YEAR` DESC, t.`MONTH` DESC");
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
	public void saveSalaryDetailEntityList(List<SalaryDetailEntity> salaryDetails) {
		StringBuffer sql = new StringBuffer();
		sql.append("REPLACE INTO `ehr_salary_details` (`ID`, `YEAR`, `MONTH`, `P_NUMBER`, `COMPANY_CODE`, ");
		sql.append("`ORGANIZATION_CODE`, `DEPT_CODE`, `OFFICE_CODE`, `GROUP_CODE`, `POST`, ");
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
			SalaryDetailEntity salaryDetail = salaryDetails.get(i);
			sql.append("(" + (salaryDetail.getId() != null && salaryDetail.getId() == 0 ? null : salaryDetail.getId()) + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getYear()) ? null : "'" + salaryDetail.getYear() + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getMonth()) ? null : "'" + salaryDetail.getMonth() + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getpNumber()) ? null : "'" + salaryDetail.getpNumber() + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getCompanyCode()) ? null : "'" + salaryDetail.getCompanyCode() + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getOrganizationCode()) ? null : "'" + salaryDetail.getOrganizationCode() + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getDeptCode()) ? null : "'" + salaryDetail.getDeptCode() + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getOfficeCode()) ? null : "'" + salaryDetail.getOfficeCode() + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getGroupCode()) ? null : "'" + salaryDetail.getGroupCode() + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getPost()) ? null : "'" + salaryDetail.getPost() + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getAttendanceHours()) ? null : "'" + salaryDetail.getAttendanceHours() + "'") + ",");
			sql.append((UtilString.isEmpty(salaryDetail.getAbsenceHours()) ? null : "'" + salaryDetail.getAbsenceHours() + "'") + ",");
			sql.append("'" + BASE64Util.encode(salaryDetail.getBaseSalary()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getFixedOvertimeSalary()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getPostSalary()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getCallSubsidies()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getCompanySalary()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getOverSalary()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getRiceStick()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getHighTem()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getLowTem()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getMorningShift()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getNightShift()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getStay()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getOtherAllowance()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getPerformanceBonus()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getSecurity()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getCompensatory()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getOtherBonus()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getAddOther()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getMealDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getDormDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getBeforeDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getInsurance()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getProvidentFund()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getAfterDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getLaterAndLeaveDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getCompletionDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getYearDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getRelaxation()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getThingDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getSickDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getTrainDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getParentalDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getMarriageDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getCompanionParentalDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getFuneralDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getOnboarding()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getTotalDeduction()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getWagePayable()) + "',");
			sql.append("'" + BASE64Util.encode(salaryDetail.getPersonalTax()) + "',");
			if( i == salaryDetails.size() - 1){
				sql.append("'" + BASE64Util.encode(salaryDetail.getRealWages()) + "')");
			} else {
				sql.append("'" + BASE64Util.encode(salaryDetail.getRealWages()) + "'),");
			}
		}
		try {
			jdbcTemplate.update(sql.toString());
		} catch (Exception e) {
			log.error("批量插入数据失败", e);
			throw e;
		}
	}

	@Override
	public List<Map<String, Object>> loadUserAuthCompany() {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("s.resource_code as resourceCode, s.resource_name as resourceName ");
		sql.append("from ehr_user_role u ");
		sql.append("inner join ehr_roles r on u.role_id = r.role_id ");
		sql.append("inner join ehr_role_resource s on s.role_id = r.role_id ");
		sql.append("where u.user_id = ? ");
		return jdbctemplate.queryForList(sql.toString(),login.getUser_id());
	}
}
