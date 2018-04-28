package com.babifood.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.NewUsersDao;
import com.babifood.service.NewUsersService;
@Service
public class NewUsersServiceImpl implements NewUsersService {
	@Autowired
	NewUsersDao newUsersDao;
	@Override
	public List<Map<String, Object>> loadUserAll() {
		// TODO Auto-generated method stub
		return newUsersDao.loadUserAll();
	}
	@Override
	public List<Map<String, Object>> loadRoleAll() {
		// TODO Auto-generated method stub
		return newUsersDao.loadRoleAll();
	}
	@Override
	public Integer saveRole(String role_name, String role_desc, String state) {
		// TODO Auto-generated method stub
		return newUsersDao.saveRole(role_name, role_desc, state);
	}
	@Override
	public Integer editRole(String role_id, String role_name, String role_desc, String state) {
		// TODO Auto-generated method stub
		return newUsersDao.editRole(role_id, role_name, role_desc, state);
	}

}
