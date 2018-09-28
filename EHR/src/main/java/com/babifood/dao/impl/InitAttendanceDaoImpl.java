package com.babifood.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.InitAttendanceDao;
import com.babifood.entity.InitAttendanceEntity;

@Repository
public class InitAttendanceDaoImpl implements InitAttendanceDao {

	private static Logger log = Logger.getLogger(InitAttendanceDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int queryAttendanceCountAttendance(String pNumber, String year, String month) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT Count(*) FROM ehr_init_attendance where `P_NUMBER` = ? and `YEAR` = ? and `MONTH` = ?");
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql.toString(), pNumber, year, month);
		} catch (Exception e) {
			log.error("操作数据库失败", e);
		}
		return count;
	}

	@Override
	public void addInitAttendance(List<InitAttendanceEntity> attendanceList) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `ehr_init_attendance` (`YEAR`, `MONTH`, `P_NUMBER`, `P_NAME`, `COMPANY_CODE`, ");
		sql.append("`ORGANIZATION_CODE`, `DEPT_CODE`, `OFFICE_CODE`, `GROUP_CODE`, `POST`, `PUNCH_TYPE`, `ARRANGEMENT_TYPE`, ");
		sql.append("`DATE`, `WEEK_DAY`, `NORMAL_START_TIME`, `NORMAL_END_TIME`, `NORMAL_TIME`, `PUNCH_START_TIME`, `PUNCH_END_TIME`, ");
		sql.append("`ORIGINAL_TIME`, `WORK_TIME`, `LATE`, `LEAVE`, `COMPLETION`, `ABSENCE_HOURS`, `VACATION_HOURS`, `YEAR_VACATION`, ");
		sql.append("`RELAXATION`, `THING_VACATION`, `SICK_VACATION`, `TRAIN_VACATION`, `PARENTAL_VACATION`, `MARRIAGE_VACATION`, ");
		sql.append("`COMPANION_PARENTAL_VACATION`, `FUNERAL_VACATION`, `UNUSUAL_HOURS`, `OVERTIME_HOURS`, `TRAVEL_HOURS`, `MEAL_SUPPLEMENT`,`IS_ATTEND`) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		List<Object[]> attend = new ArrayList<>();
		for (InitAttendanceEntity entity : attendanceList) {
			attend.add(new Object[] { entity.getYear(), entity.getMonth(), entity.getpNumber(), entity.getpName(),
					entity.getCompanyCode(), entity.getOrganizationCode(), entity.getDeptCode(), entity.getOfficeCode(),
					entity.getGroupCode(), entity.getPost(), entity.getPunchType(), entity.getArrangementType(),
					entity.getDate(), entity.getWeekDay(), entity.getNormalStartTime(), entity.getNormalEndTime(),
					entity.getNormalTime(), entity.getPunchStartTime(), entity.getPunchEndTime(),
					entity.getOriginalTime(), entity.getWorkTime(), entity.getLate(), entity.getLeave(),
					entity.getCompletion(), entity.getAbsenceHours(), entity.getVacationHours(),
					entity.getYearVacation(), entity.getRelaxation(), entity.getThingVacation(),
					entity.getSickVacation(), entity.getTrainVacation(), entity.getParentalVacation(),
					entity.getMarriageVacation(), entity.getCompanionParentalVacation(), entity.getFuneralVacation(),
					entity.getUnusualHours(), entity.getOvertimeHours(), entity.getTravelHours(),
					entity.getMealSupplement(), entity.getIsAttend()});
		}
		try {
			jdbcTemplate.batchUpdate(sql.toString(), attend);
		} catch (Exception e) {
			log.error("员工 初始化排班失败,pNumber:" + attendanceList.get(0).getpNumber() + ",pName:"
					+ attendanceList.get(0).getpName(), e);
		}
	}

	@Override
	public Map<String, Object> summaryArrangementInfo(String year, String month, String pNumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUM(standardWorkLength) AS standardWorkLength, SUM(`chiDao`) AS chiDao, ");
		sql.append("SUM(`originalCheckingLength`) AS originalCheckingLength, SUM(zaoTui) AS zaoTui, ");
		sql.append("SUM(kuangGong) AS kuangGong, SUM(nianJia) AS `year`, SUM(tiaoXiu) AS tiaoXiu, ");
		sql.append("SUM(shiJia) AS shiJia, SUM(bingJia) AS bingJia, SUM(peixunJia) AS peixunJia, ");
		sql.append("SUM(hunJia) AS hunJia, SUM(chanJia) AS chanJia, SUM(PeiChanJia) AS PeiChanJia, ");
		sql.append("SUM(SangJia) AS SangJia, SUM(Qita) AS qita, SUM(Queqin) AS queqin, ");
		sql.append("SUM(Qingjia) AS qingjia, SUM(Canbu) AS canbu, SUM(InOutJob) AS onboarding ");
		sql.append("FROM ehr_checking_result where `YEAR` = ? and `MONTH` = ? and WorkNum = ?");
		Map<String, Object> summary = null;
		try {
			summary = jdbcTemplate.queryForMap(sql.toString(), year, month, pNumber);
		} catch (Exception e) {
			log.error("统计员工考勤信息失败",e);
			throw e;
		}
		return summary;
	}

	@Override
	public Double findYearSickHours(String year, String month, String pNumber, String endDate) {
		String sql = "SELECT SUM(bingJia) as sick from ehr_checking_result where year = ? and month = ? and WorkNum = ? and checkingDate <= ?";
		Double sickHours = 0.0;
		try {
			int ser = jdbcTemplate.queryForInt(sql, year, month, pNumber, endDate);
			sickHours = Double.valueOf(ser);
		} catch (Exception e) {
			log.error("统计员工年度病假统计信息失败",e);
			throw e;
		}
		return sickHours;
	}

	@Override
	public List<Map<String, Object>> findCurrentMonthSick(String year, String month, String pNumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("Year,Month,WorkNum,UserName,");
		sql.append("CompanyCode,Company,OrganCode,Organ,DeptCode, ");
		sql.append("Dept,OfficeCode,Office,GroupCode,GroupName, ");
		sql.append("PostCode,Post,CheckingType,PaiBanType,checkingDate, ");
		sql.append("Week,beginTime,endTime,standardWorkLength, ");
		sql.append("checkingBeginTime,checkingEndTime,originalCheckingLength, ");
		sql.append("actualWorkLength,chiDao,zaoTui,kuangGong,nianJia, ");
		sql.append("tiaoXiu,shiJia,bingJia,peixunJia,hunJia, ");
		sql.append("chanJia,PeiChanJia,SangJia,Qita,Queqin, ");
		sql.append("Qingjia,Yidong,Jiaban,Chuchai,Canbu, ");
		sql.append("EventBeginTime,EventEndTime,Clockflag ");
		sql.append("from ehr_checking_result ");
		sql.append("where bingJia > 0 and Year=? and Month = ? and WorkNum=?");
		List<Map<String, Object>> sickList = null;
		try {
			sickList = jdbcTemplate.queryForList(sql.toString(), year, month, pNumber);
		} catch (Exception e) {
			log.error("查询员工当月病假失败",e);
			throw e;
		}
		return sickList;
	}

}
