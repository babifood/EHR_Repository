package com.babifood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface NewUsersDao {
	public List<Map<String,Object>> loadUserAll();
	
	public List<Map<String,Object>> loadRoleAll();
}
