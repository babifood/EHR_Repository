package com.babifood.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface NewUsersService {
	public List<Map<String, Object>> loadUserAll();
	
	public List<Map<String, Object>> loadRoleAll();
	
	public Integer saveRole(String role_name,String role_desc,String state);
	
	public Integer editRole(String role_id,String role_name,String role_desc,String state);
}
