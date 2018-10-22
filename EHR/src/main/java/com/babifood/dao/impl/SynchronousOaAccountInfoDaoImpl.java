package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.SynchronousOaAccountInfoDao;
import com.babifood.utils.CustomerContextHolder;
@Repository
public class SynchronousOaAccountInfoDaoImpl implements SynchronousOaAccountInfoDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	public static final Logger log = Logger.getLogger(SynchronousOaAccountInfoDaoImpl.class);
	@Override
	public List<Map<String, Object>> loadOaWorkNumInFo(String workNum, String userName) {
		// TODO Auto-generated method stub
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_OA);
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("id as member,name,code from org_member where code is not null");
		if(workNum!=null&&!workNum.equals("")){
			sql.append(" and code like '%"+workNum+"%'");
		}
		if(userName!=null&&!userName.equals("")){
			sql.append(" and name like '%"+userName+"%'");
		}
		List<Map<String, Object>> map= null;
		try {
			map = jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
			throw e;
		}finally{
			CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		}
		return map;		
	}

}
