package com.babifood.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.LoginDao;
import com.babifood.entity.LoginEntity;
@Repository
public class LoginDaoImpl implements LoginDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	
	@Override
	public LoginEntity findLogin(String user_name, String password) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select user_id,user_name,password,show_name,e_mail,phone,state ");
		sql.append("from ehr_users where user_name=? and password=?");
        LoginEntity login =null;
        try{
        	login = jdbctemplate.queryForObject(sql.toString(),new BeanPropertyRowMapper<>(LoginEntity.class), user_name,password);
        }catch(Exception e) {
            log.error("查询错误："+e.getMessage());
        }
		return login;
	}
}
