package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.UserTreeDao;
@Repository
public class UserTreeDaoImpl implements UserTreeDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	//部门树
	@Override
	public List<Map<String,Object>> loaduserTreeDept() {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select dept_code as id,pCode as parentId,dept_name as name from ehr_dept");
		List<Map<String,Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("loaduserTreeDept查询错误"+e.getMessage());
		}
		return list;
	}
	//未选人员by部门id
	@Override
	public List<Map<String, Object>> loadunSelectPersonByDeptID(String dept_id) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select p_id, p_name from ehr_person_basic_info");
		if(dept_id!=null&&!dept_id.equals("")){
			sql.append(" where p_section_office_id="+dept_id);
		}
		List<Map<String,Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("loadunSelectPersonByDeptID查询错误"+e.getMessage());
		}
		return list;
	}
	//未选人员by人员姓名
	@Override
	public List<Map<String, Object>> loadunSelectPersonByPersonName(String p_name) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select p_id, p_name from ehr_person_basic_info");
		if(p_name!=null&&!p_name.equals("")){
			sql.append(" where p_name like '%"+p_name+"%'");
		}
		List<Map<String,Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("loadunSelectPersonByPersonName查询错误"+e.getMessage());
		}
		return list;
	}
	//已选中人员by奖惩列表中的记录ID----这个要改的，要改成根据查询按钮的那个input直接显示里面的值
	@Override
	public List<Map<String, Object>> loadinSelectPersonByRapId(Integer rap_id) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select RAP_PROPOSER_NAME as p_name from ehr_rewardandpunishment");
		if(rap_id!=null&&!rap_id.equals("")){
			sql.append(" where RAP_ID ="+rap_id);
		}
		List<Map<String,Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("loadunSelectPersonByPersonName查询错误"+e.getMessage());
		}
		return list;
	}
	//已选人员by人员姓名
	@Override
	public List<Map<String, Object>> loadinSelectPersonByPersonName(String p_name) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select RAP_PROPOSER_ID as rap_proposer from ehr_rewrdandpunishment");
		if(p_name!=null&&!p_name.equals("")){
			sql.append(" where RAP_ID ="+p_name);
		}
		List<Map<String,Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("loadunSelectPersonByPersonName查询错误"+e.getMessage());
		}
		return list;
	}

}
