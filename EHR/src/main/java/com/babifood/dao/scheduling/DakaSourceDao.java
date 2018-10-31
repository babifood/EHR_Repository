package com.babifood.dao.scheduling;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.utils.CustomerContextHolder;

@Repository
public class DakaSourceDao {

private Logger logger = LoggerFactory.getLogger(DakaSourceDao.class);
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public String findLastDay() {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		String sql = "select max(check_time) from ehr_daka_source ";
		String checkTime = "";
		try {
			checkTime = jdbctemplate.queryForObject(sql, String.class);
		} catch (Exception e) {
			logger.error("查询最后打卡记录日期失败", e);
			throw e;
		}
		return checkTime;
	}

	public Integer findDakaRecordCount(String beginDate, String endDate) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_KQ);
		String sql = "select COUNT(*) from dbo.checkinout a left join dbo.userinfo b on a.pin = b.badgenumber where checktime > ? and checktime < ?";
		Integer total = 0;
		try {
			total = jdbctemplate.queryForInt(sql, beginDate, endDate);
		} catch (Exception e) {
			logger.error("查询未同步打卡记录总数失败", e);
			throw e;
		}finally {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		}
		return total;
	}

	public List<Map<String, Object>> findDakaRecordList(Integer start, Integer num, String beginTime, String endDate) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_KQ);
		StringBuffer sql = new StringBuffer();
		sql.append("select Top " + num + " o.* from (select ROW_NUMBER() over (order by a.id) as num, a.id as id, ");
		sql.append("a.pin as pNumber, a.checktime as checkTime,a.checktype as checkType, ");
		sql.append("a.verifycode as verifyCode, a.sn_name as snName, b.name as pName ");
		sql.append("from dbo.checkinout a ");
		sql.append("left join dbo.userinfo b on a.pin = b.badgenumber where checktime > '" + beginTime + "' and checktime < '" + endDate  + "') o ");
		sql.append("where num > " + start);
		List<Map<String, Object>> dakaRecordList = null;
		try {
			dakaRecordList = jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			logger.error("查询考勤系统打卡记录失败", e);
			throw e;
		}finally {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		}
		return dakaRecordList;
	}

	public void saveDakaRecords(List<Object[]> params) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("REPLACE INTO `ehr_daka_source` (`id`, `p_number`, `p_name`, `check_time`, `check_type`, ");
		sql.append("`verify_code`, `sn_name`) VALUES (?, ?, ?, ?, ?, ?, ?)");
		try {
			jdbctemplate.batchUpdate(sql.toString(), params);
		} catch (Exception e) {
			logger.error("保存源打卡记录失败", e);
			throw e;
		}
	}

	public List<Map<String, Object>> findDayDakaSources(String beginTime, String endTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUBSTRING(p_number,4,LENGTH(p_number)) AS pNumber, p_name AS pName, check_time AS checkTime ");
		sql.append("FROM ehr_daka_source WHERE check_time >= ? AND check_time < ?");
		List<Map<String, Object>> dakaSources = null;
		try {
			dakaSources = jdbctemplate.queryForList(sql.toString(), beginTime, endTime);
		} catch (Exception e) {
			logger.error("查询源打卡记录失败", e);
			throw e;
		}
		return dakaSources;
	}
}
