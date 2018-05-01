package com.babifood.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.NewUsersDao;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.RoleMenuEntity;
import com.babifood.service.NewUsersService;
import com.babifood.utils.IdGen;
import com.babifood.utils.MD5;
@Service
public class NewUsersServiceImpl implements NewUsersService {
	@Autowired
	NewUsersDao newUsersDao;
	@Override
	public List<Map<String, Object>> loadUserAll(String user_name,String show_name) {
		// TODO Auto-generated method stub
		return newUsersDao.loadUserAll(user_name,show_name);
	}
	@Override
	public List<Map<String, Object>> loadRoleAll(String role_name) {
		// TODO Auto-generated method stub
		return newUsersDao.loadRoleAll(role_name);
	}
	@Override
	public Integer saveRole(String role_name, String role_desc, String state) {
		// TODO Auto-generated method stub
		String role_id = IdGen.uuid();
		return newUsersDao.saveRole(role_id,role_name, role_desc, state);
	}
	@Override
	public Integer editRole(String role_id, String role_name, String role_desc, String state) {
		// TODO Auto-generated method stub
		return newUsersDao.editRole(role_id, role_name, role_desc, state);
	}
	@Override
	public Integer removeRole(String role_id) {
		// TODO Auto-generated method stub
		return newUsersDao.removeRole(role_id);
	}
	@Override
	public List<Map<String, Object>> loadComboboxData() {
		// TODO Auto-generated method stub
		return newUsersDao.loadComboboxData();
	}
	@Override
	public Integer saveUser(LoginEntity userEntity) {
		// TODO Auto-generated method stub
		userEntity.setUser_id(IdGen.uuid());
		userEntity.setPassword(MD5.gtePasswordMd5(userEntity.getPassword()));
		return newUsersDao.saveUser(userEntity);
	}
	@Override
	public Integer editUser(LoginEntity userEntity) {
		// TODO Auto-generated method stub
		userEntity.setPassword(MD5.gtePasswordMd5(userEntity.getPassword()));
		return newUsersDao.editUser(userEntity);
	}
	@Override
	public Integer removeUser(String user_id) {
		// TODO Auto-generated method stub
		return newUsersDao.removeUser(user_id);
	}
	@Override
	public Integer saveMenuRole(RoleMenuEntity[] roleMenuEntity) {
		// TODO Auto-generated method stub
		return newUsersDao.saveMenuRole(roleMenuEntity);
	}
	@Override
	public List<Map<String, Object>> getMenuIds(String role_id) {
		// TODO Auto-generated method stub
		return newUsersDao.getMenuIds(role_id);
	}

}
