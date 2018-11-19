package com.babifood.dao.scheduling;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.utils.CustomerContextHolder;

@Repository
public class DakaRecordDao {

	private Logger logger = LoggerFactory.getLogger(DakaRecordDao.class);
	
	@Autowired
	private JdbcTemplate jdbctemplate;

	public String findLastDay() {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("select max(ClockedDate) from ehr_daka_record ");
		String checkTime = "";
		try {
			checkTime = jdbctemplate.queryForObject(sql.toString(), String.class);
		} catch (Exception e) {
			logger.error("查询最后打卡记录日期失败", e);
			throw e;
		}
		return checkTime;
	}

	public void saveDakaRecord(List<Object[]> dakaParams) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("REPLACE INTO `ehr_daka_record` (`WorkNum`, `UserName`, ");
		sql.append("`ClockedDate`, `BeginTime`, `EndTime`) VALUES (?, ?, ?, ?, ?)");
		try {
			jdbctemplate.batchUpdate(sql.toString(), dakaParams);
		} catch (Exception e) {
			logger.error("保存打卡记录失败", e);
			throw e;
		}
	}

		
}
