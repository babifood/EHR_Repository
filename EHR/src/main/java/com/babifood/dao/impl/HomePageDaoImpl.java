package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.HomePageDao;
@Repository
public class HomePageDaoImpl implements HomePageDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	public static final Logger log = Logger.getLogger(HomePageDaoImpl.class);
	@Override
	public List<Map<String,Object>> LoadTreeMenu(String id,String role_id) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select m.id,m.text,m.state,m.iconCls,m.url,m.nid,m.flag");
		sql.append(" from ehr_menu m inner join ehr_role_menu r");
		sql.append(" on m.id = r.menu_tbo_id");
		sql.append(" where m.nid = ?");
		sql.append(" and r.role_id in("+role_id+")");
		sql.append(" group by r.menu_tbo_id");
		List<Map<String,Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString(),id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("TreeMenu查询错误"+e.getMessage());
		}
		return list;
	}

}
