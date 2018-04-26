package com.babifood.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface NewUsersService {
	public List<Map<String, Object>> loadUserAll();
	
	public List<Map<String, Object>> loadRoleAll();
}
