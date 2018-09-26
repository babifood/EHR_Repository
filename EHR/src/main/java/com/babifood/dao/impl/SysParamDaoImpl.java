package com.babifood.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.SysParamDao;

@Repository
public class SysParamDaoImpl implements SysParamDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getLastNumber(String key) {
		return jdbcTemplate.queryForInt("SELECT getLastNumber(?)", key) + "";
	}

}
