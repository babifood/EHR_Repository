package com.babifood.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.babifood.entity.LoginEntity;
import com.babifood.entity.RoleMenuEntity;

@Service
public interface NewUsersService {
	public List<Map<String, Object>> loadUserAll(String user_name,String show_name);
	
	public List<Map<String, Object>> loadRoleAll(String role_name);
	
	public Integer saveRole(String role_name,String role_desc,String state);
	
	public Integer editRole(String role_id,String role_name,String role_desc,String state);
	
	public Integer removeRole(String role_id);
	
	public List<Map<String,Object>> loadComboboxData();
	
	public Integer saveUser(LoginEntity userEntity);
	
	public Integer editUser(LoginEntity userEntity);
	
	public Integer removeUser(String user_id);
	
	public Integer saveMenuRole(RoleMenuEntity[] roleMenuEntity);
	
	public List<Map<String,Object>> getMenuIds(String role_id);
}
