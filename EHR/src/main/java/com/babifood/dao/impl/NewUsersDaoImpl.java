package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.NewUsersDao;
@Repository
public class NewUsersDaoImpl implements NewUsersDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	@Override
	public List<Map<String, Object>> loadUserAll() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select user_id,user_name,show_name,e_mail,phone,state ");
		sql.append(" from ehr_users");
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> loadRoleAll() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select role_id,role_name,role_desc,state ");
		sql.append(" from ehr_roles");
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}

}
