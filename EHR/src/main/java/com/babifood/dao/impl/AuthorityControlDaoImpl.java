package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.babifood.dao.AuthorityControlDao;
import com.babifood.entity.LoginEntity;

public class AuthorityControlDaoImpl implements AuthorityControlDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	/**
	 * 查询当前用户数据权限
	 */
	@Override
	public List<Map<String, Object>> loadUserDataAuthority() {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("r.organization_code as companyCodes ");
		sql.append("from ehr_user_role u ");
		sql.append("inner join ehr_roles r ");
		sql.append("on u.role_id =r.role_id ");
		sql.append("where u.user_id = ? ");
		return jdbctemplate.queryForList(sql.toString(),login.getUser_id());
	}
	/**
	 * 根据数据权限添加SQL过滤条件
	 */
	@Override
	public StringBuffer jointDataAuthoritySql(String companyCode,StringBuffer sql) {
		// TODO Auto-generated method stub
		StringBuffer thissql = sql;
		List<Map<String, Object>> companyCodes = loadUserDataAuthority();
		int size = companyCodes==null?0:companyCodes.size();
		if(size==1){
			//如果有集团权限，默认查所有，所有不加任何条件
			if(!companyCodes.get(0).get("companyCodes").equals("00000001")){
				thissql.append(" and "+companyCode+" = '"+companyCodes.get(0).get("companyCodes")+"'");
			}
		}else if(size>1){
			String companys = "";
			for (Map<String, Object> map : companyCodes) {
				if(map.get("companyCodes").equals("00000001")){
					companys = map.get("companyCodes").toString();
					break;
				}
				companys+="'"+map.get("companyCodes")+"',";
			}
			//如果有集团权限，默认查所有，所有不加任何条件
			if(!companyCodes.get(0).get("companyCodes").equals("00000001")){
				thissql.append(" and "+companyCode+" in("+companys.substring(0, companys.length()-1)+")");
			}
		}
		return thissql;
	}
	

}
