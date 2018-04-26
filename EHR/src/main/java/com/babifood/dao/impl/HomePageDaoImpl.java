package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.HomePageDao;
@Repository
public class HomePageDaoImpl implements HomePageDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	@Override
	public List<Map<String,Object>> LoadTreeMenu(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select id,text,state,iconCls,url,nid");
		sql.append(" FROM  ehr_menu where nid = ?");
		List<Map<String,Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString(), id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("TreeMenu查询错误"+e.getMessage());
		}
		return list;
	}

}
