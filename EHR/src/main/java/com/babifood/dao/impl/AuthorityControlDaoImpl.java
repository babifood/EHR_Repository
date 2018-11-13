package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.AuthorityControlDao;
import com.babifood.entity.LoginEntity;

@Repository
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
		sql.append("rr.resource_code,r.organization_code as resource ");
		sql.append("from ehr_user_role ur ");
		sql.append("inner join ehr_roles r on ur.role_id =r.role_id ");
		sql.append("inner join ehr_role_resource rr ON r.role_id = rr.role_id ");
		sql.append("where ur.user_id = ? ");
		return jdbctemplate.queryForList(sql.toString(),login.getUser_id());
	}
	/**
	 * 根据数据权限添加SQL过滤条件
	 */
	@Override
	public StringBuffer jointDataAuthoritySql(String companyCode,String orgaCode,StringBuffer sql) {
		// TODO Auto-generated method stub
		StringBuffer thissql = sql;
		List<Map<String, Object>> codes = loadUserDataAuthority();
		if(codes.size()>0){
			String companys = "";
			for (Map<String, Object> map : codes) {
				companys+="'"+map.get("resource_code")+"',";
			}
			//如果有集团权限，默认查所有，所有不加任何条件
			if(codes.get(0).get("resource").equals("0")){
				thissql.append(" and "+companyCode+" in("+companys.substring(0, companys.length()-1)+")");
			}else if(codes.get(0).get("resource").equals("1")){
				thissql.append(" and "+orgaCode+" in("+companys.substring(0, companys.length()-1)+")");
			}
		}else{
			thissql.append(" and 1=2");
		}
		return thissql;
	}
	@Override
	public List<String> findPNumberList(List<Map<String, Object>> auths) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p_number FROM ehr_person_basic_info ");
		StringBuffer companyCodes = new StringBuffer();
		StringBuffer organizationCodes = new StringBuffer();
		for(int i = 0; i < auths.size(); i++){
			Map<String, Object> map = auths.get(i);
			companyCodes.append("'"+map.get("resource_code")+"'");
			organizationCodes.append("'"+map.get("resource")+"'");
			if(i < auths.size() - 1){
				companyCodes.append(",");
				organizationCodes.append(",");
			}
		}
		sql.append("where p_company_id in (" + companyCodes.toString() + ") or ");
		sql.append("p_organization_id in (" + organizationCodes.toString() + ")");
		List<String> pNumberList = null;
		try {
			pNumberList = jdbctemplate.queryForList(sql.toString(), String.class);
		} catch (Exception e) {
			throw e;
		}
		return pNumberList;
	}

}
