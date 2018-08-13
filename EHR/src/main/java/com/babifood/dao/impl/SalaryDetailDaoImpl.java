package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.SalaryDetailDao;
import com.babifood.entity.SalaryDetailEntity;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.UtilString;

@Repository
public class SalaryDetailDaoImpl implements SalaryDetailDao {

	Logger log = LoggerFactory.getLogger(SalaryDetailDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> findCurrentMonthSalary(String year, String month, String pNumber) {
		String sql = "SELECT * FROM EHR_SALARY_DETAILS WHERE YEAR = ? AND MONTH = ? AND P_NUMBER = ?";
		List<Map<String, Object>> salaryDetails = null;
		Map<String, Object> salaryDetail = null;
		try {
			salaryDetails = jdbcTemplate.queryForList(sql, year, month, pNumber);
		} catch (Exception e) {
			log.error("查询当月薪资明细失败,pNumber=" + pNumber, e.getMessage());
			throw e;
		}
		if (salaryDetails != null && salaryDetails.size() > 0) {
			salaryDetail = salaryDetails.get(0);
		}
		return salaryDetail;
	}

	@Override
	public void updateSalaryDetail(SalaryDetailEntity salaryDerail) {
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
			jdbcTemplate.update(sql.toString(), salaryDerail.getYear(), salaryDerail.getMonth(),
					salaryDerail.getpNumber(), salaryDerail.getCompanyCode(), salaryDerail.getOrganizationCode(),
					salaryDerail.getDeptCode(), salaryDerail.getOfficeCode(), salaryDerail.getGroupCode(),
					salaryDerail.getPost(), salaryDerail.getAttendanceHours(), salaryDerail.getAbsenceHours(),
					BASE64Util.encode(salaryDerail.getBaseSalary()),
					BASE64Util.encode(salaryDerail.getFixedOvertimeSalary()),
					BASE64Util.encode(salaryDerail.getPostSalary()), BASE64Util.encode(salaryDerail.getCallSubsidies()),
					BASE64Util.encode(salaryDerail.getCompanySalary()), BASE64Util.encode(salaryDerail.getOverSalary()),
					BASE64Util.encode(salaryDerail.getRiceStick()),
					BASE64Util.encode(salaryDerail.getHighTem()),
					BASE64Util.encode(salaryDerail.getLowTem()),
					BASE64Util.encode(salaryDerail.getMorningShift()),
					BASE64Util.encode(salaryDerail.getNightShift()),
					BASE64Util.encode(salaryDerail.getStay()),
					BASE64Util.encode(salaryDerail.getOtherAllowance()),
					BASE64Util.encode(salaryDerail.getPerformanceBonus()),
					BASE64Util.encode(salaryDerail.getSecurity()),
					BASE64Util.encode(salaryDerail.getCompensatory()),
					BASE64Util.encode(salaryDerail.getOtherBonus()), BASE64Util.encode(salaryDerail.getAddOther()),
					BASE64Util.encode(salaryDerail.getMealDeduction()),
					BASE64Util.encode(salaryDerail.getDormDeduction()),
					BASE64Util.encode(salaryDerail.getBeforeDeduction()),
					BASE64Util.encode(salaryDerail.getInsurance()), BASE64Util.encode(salaryDerail.getProvidentFund()),
					BASE64Util.encode(salaryDerail.getAfterDeduction()),
					BASE64Util.encode(salaryDerail.getYearDeduction()),
					BASE64Util.encode(salaryDerail.getLaterAndLeaveDeduction()),
					BASE64Util.encode(salaryDerail.getCompletionDeduction()),
					BASE64Util.encode(salaryDerail.getRealWages()), BASE64Util.encode(salaryDerail.getThingDeduction()),
					BASE64Util.encode(salaryDerail.getSickDeduction()),
					BASE64Util.encode(salaryDerail.getTrainDeduction()),
					BASE64Util.encode(salaryDerail.getParentalDeduction()),
					BASE64Util.encode(salaryDerail.getMarriageDeduction()),
					BASE64Util.encode(salaryDerail.getCompanionParentalDeduction()),
					BASE64Util.encode(salaryDerail.getFuneralDeduction()),
					BASE64Util.encode(salaryDerail.getOnboarding()),
					BASE64Util.encode(salaryDerail.getTotalDeduction()),
					BASE64Util.encode(salaryDerail.getWagePayable()), BASE64Util.encode(salaryDerail.getPersonalTax()),
					BASE64Util.encode(salaryDerail.getRealWages()), salaryDerail.getId());
		} catch (Exception e) {
			log.error("修改当月薪资明细失败,pNumber=" + salaryDerail.getpNumber(), e.getMessage());
			throw e;
		}
	}

	@Override
	public void addSalaryDetail(SalaryDetailEntity salaryDerail) {
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
			jdbcTemplate.update(sql.toString(), salaryDerail.getYear(), salaryDerail.getMonth(),
					salaryDerail.getpNumber(), salaryDerail.getCompanyCode(), salaryDerail.getOrganizationCode(),
					salaryDerail.getDeptCode(), salaryDerail.getOfficeCode(), salaryDerail.getGroupCode(),
					salaryDerail.getPost(), salaryDerail.getAttendanceHours(), salaryDerail.getAbsenceHours(),
					BASE64Util.encode(salaryDerail.getBaseSalary()),
					BASE64Util.encode(salaryDerail.getFixedOvertimeSalary()),
					BASE64Util.encode(salaryDerail.getPostSalary()), BASE64Util.encode(salaryDerail.getCallSubsidies()),
					BASE64Util.encode(salaryDerail.getCompanySalary()), BASE64Util.encode(salaryDerail.getOverSalary()),
					BASE64Util.encode(salaryDerail.getRiceStick()),
					BASE64Util.encode(salaryDerail.getHighTem()),
					BASE64Util.encode(salaryDerail.getLowTem()),
					BASE64Util.encode(salaryDerail.getMorningShift()),
					BASE64Util.encode(salaryDerail.getNightShift()),
					BASE64Util.encode(salaryDerail.getStay()),
					BASE64Util.encode(salaryDerail.getOtherAllowance()),
					BASE64Util.encode(salaryDerail.getPerformanceBonus()),
					BASE64Util.encode(salaryDerail.getSecurity()),
					BASE64Util.encode(salaryDerail.getCompensatory()),
					BASE64Util.encode(salaryDerail.getOtherBonus()), BASE64Util.encode(salaryDerail.getAddOther()),
					BASE64Util.encode(salaryDerail.getMealDeduction()),
					BASE64Util.encode(salaryDerail.getDormDeduction()),
					BASE64Util.encode(salaryDerail.getBeforeDeduction()),
					BASE64Util.encode(salaryDerail.getInsurance()), BASE64Util.encode(salaryDerail.getProvidentFund()),
					BASE64Util.encode(salaryDerail.getAfterDeduction()),
					BASE64Util.encode(salaryDerail.getLaterAndLeaveDeduction()),
					BASE64Util.encode(salaryDerail.getCompletionDeduction()),
					BASE64Util.encode(salaryDerail.getYearDeduction()), BASE64Util.encode(salaryDerail.getRealWages()),
					BASE64Util.encode(salaryDerail.getThingDeduction()),
					BASE64Util.encode(salaryDerail.getSickDeduction()),
					BASE64Util.encode(salaryDerail.getTrainDeduction()),
					BASE64Util.encode(salaryDerail.getParentalDeduction()),
					BASE64Util.encode(salaryDerail.getMarriageDeduction()),
					BASE64Util.encode(salaryDerail.getCompanionParentalDeduction()),
					BASE64Util.encode(salaryDerail.getFuneralDeduction()),
					BASE64Util.encode(salaryDerail.getOnboarding()),
					BASE64Util.encode(salaryDerail.getTotalDeduction()),
					BASE64Util.encode(salaryDerail.getWagePayable()), BASE64Util.encode(salaryDerail.getPersonalTax()),
					BASE64Util.encode(salaryDerail.getRealWages()));
		} catch (Exception e) {
			log.error("新增当月薪资明细失败,pNumber=" + salaryDerail.getpNumber(), e.getMessage());
			throw e;
		}
	}

	@Override
	public Integer getSalaryDetailsCount(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM ehr_salary_details a ");
		sql.append("LEFT JOIN ehr_dept b on a.ORGANIZATION_CODE = b.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept c on a.DEPT_CODE = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d on a.OFFICE_CODE = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e on a.GROUP_CODE = e.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_person_basic_info f ON a.P_NUMBER = f.p_number WHERE 1=1 ");
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
			count = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询薪资明细数量失败", e.getMessage());
			throw e;
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> getPageSalaryDetails(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ID AS id, a.`YEAR` AS `year`, a.`MONTH` AS `month`, a.P_NUMBER AS pNumber, ");
		sql.append("a.ABSENCE_HOURS AS absenceHours, a.AFTER_OTHER_DEDUCTION AS afterDeduction, ");
		sql.append("a.ATTENDANCE_HOURS AS attendanceHours, a.BASE_SALARY AS baseSalary, a.ADD_OTHER AS addOther, ");
		sql.append("a.BEFORE_OTHER_DEDUCTION AS beforeDeduction, a.CALL_SUBSIDIES AS callSubsidies, ");
		sql.append("a.COMPANION_PARENTAL_DEDUCTION AS companionParentalDeduction, a.COMPANY_SALARY AS companySalary, ");
		sql.append("a.COMPENSATORY_BONUS AS compensatory, a.DORM_DEDUCTION as dormDeduction, ");
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
		sql.append("a.YEAR_DEDUCTION AS yearDeduction, b.DEPT_NAME AS organzationName, c.DEPT_NAME AS deptName, ");
		sql.append("a.LATER_AND_LEAVE_DEDUCTION AS laterAndLeaveDeduction, a.COMPLETION_DEDUCTION AS completionDeduction, ");
		sql.append("d.DEPT_NAME AS officeName, e.DEPT_NAME AS groupName, f.p_name AS pName FROM ehr_salary_details a ");
		sql.append("LEFT JOIN ehr_dept b on a.ORGANIZATION_CODE = b.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept c on a.DEPT_CODE = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d on a.OFFICE_CODE = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e on a.GROUP_CODE = e.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_person_basic_info f ON a.P_NUMBER = f.p_number WHERE 1=1 ");
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
			log.error("查询薪资明细列表失败", e.getMessage());
			throw e;
		}
		return salaryDetailList;
	}

}
