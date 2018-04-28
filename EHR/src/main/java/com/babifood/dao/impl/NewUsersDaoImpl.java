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
	@Override
	public Integer saveRole(String role_name,String role_desc,String state) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_roles (role_name,role_desc,state) ");
		sql.append(" values(?,?,?)");
		Object[] params=new Object[3];
		params[0]=role_name;
		params[1]=role_desc;
		params[2]=state;
		int rows =-1;
		try {
			rows = jdbctemplate.update(sql.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
	@Override
	public Integer editRole(String role_id, String role_name, String role_desc, String state) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_roles set ROLE_NAME=?,ROLE_DESC=?,STATE=? where ROLE_ID=?");
		Object[] params=new Object[4];
		params[0]=role_name;
		params[1]=role_desc;
		params[2]=state;
		params[3]=role_id;
		int rows =-1;
		try {
			rows = jdbctemplate.update(sql.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}

}
