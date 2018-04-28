package com.babifood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface NewUsersDao {
	public List<Map<String,Object>> loadUserAll();
	
	public List<Map<String,Object>> loadRoleAll();
	
	public Integer saveRole(String role_name,String role_desc,String state);
	
	public Integer editRole(String role_id,String role_name,String role_desc,String state);
}
