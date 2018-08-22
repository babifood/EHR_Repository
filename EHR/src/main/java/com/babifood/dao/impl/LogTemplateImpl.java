package com.babifood.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.babifood.utils.UtilDateTime;
import com.cn.babifood.operation.dao.LogTemplate;
import com.cn.babifood.operation.entity.OperationLog;

public class LogTemplateImpl implements LogTemplate {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void saveLog(Object arg0) {
		OperationLog operationLog = (OperationLog) arg0;
		String sql = "INSERT INTO `ehr_operating_log` (`operate_time`, `user_id`, `operate_type`, `ip`, `contect`) VALUES (?, ?, ?, ?, ?)";
		try {
			jdbcTemplate.update(sql, UtilDateTime.getCurrentTime("yyyy-MM-dd HH:mm:ss"), operationLog.getUserId(), 
					operationLog.getOperatType(), operationLog.getIp(), operationLog.getContect());
		} catch (Exception e) {
			throw e;
		}
	}

}
