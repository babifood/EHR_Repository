package com.babifood.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.NewUsersDao;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.RoleAuthorityEntity;
import com.babifood.entity.RoleMenuEntity;
import com.babifood.entity.UserRoleEntity;
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
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> userList = newUsersDao.loadUser(user_name, show_name);
		List<Map<String, Object>> roleList = newUsersDao.loadUserAll(user_name,show_name);
		for (int i=0;i<userList.size();i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("user_id",userList.get(i).get("user_id"));
			dataMap.put("user_name",userList.get(i).get("user_name"));
			dataMap.put("password",userList.get(i).get("password"));
			dataMap.put("show_name",userList.get(i).get("show_name"));
			dataMap.put("e_mail",userList.get(i).get("e_mail"));
			dataMap.put("phone",userList.get(i).get("phone"));
			dataMap.put("state",userList.get(i).get("state"));
			String role_id = "";
			String role_name="";
			for(int j=0;j<roleList.size();j++){
				if(userList.get(i).get("user_id").toString().equals(roleList.get(j).get("user_id").toString())){
					role_id+=roleList.get(j).get("role_id")+"/";
					role_name+=roleList.get(j).get("role_name")+"/";
				}
			}
			dataMap.put("role_id",role_id);
			dataMap.put("role_name",role_name);
			returnList.add(dataMap);
		}
		return returnList;
	}
	@Override
	public List<Map<String, Object>> loadRoleAll(String role_name) {
		// TODO Auto-generated method stub
		return newUsersDao.loadRoleAll(role_name);
	}
	@Override
	public Integer saveRole(String role_name, String role_desc, String state,String organization_code,String organization_name) {
		// TODO Auto-generated method stub
		String role_id = IdGen.uuid();
		return newUsersDao.saveRole(role_id,role_name, role_desc, state,organization_code,organization_name);
	}
	@Override
	public Integer editRole(String role_id, String role_name, String role_desc, String state,String organization_code,String organization_name) {
		// TODO Auto-generated method stub
		return newUsersDao.editRole(role_id, role_name, role_desc, state,organization_code,organization_name);
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
		String password = userEntity.getPassword();
		String[] passwordArr = password.split("/");
		if(passwordArr[0].equals(passwordArr[1])){
			userEntity.setPassword(passwordArr[0]);
		}else{
			userEntity.setPassword(MD5.gtePasswordMd5(passwordArr[1]));
		}
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
	@Override
	public List<Map<String, Object>> loadCheckTreeMenu(String id) {
		// TODO Auto-generated method stub
		String strId = id==null||id.equals("")?"0":id;
		return newUsersDao.loadCheckTreeMenu(strId);
	}
	@Override
	public List<UserRoleEntity> loadRoleWhereUser(String user_id) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = newUsersDao.loadRoleWhereUser(user_id);
		List<UserRoleEntity> roleList = null;
		if(list.size()>0){
			roleList = new ArrayList<UserRoleEntity>();
			for (Map<String, Object> map : list) {
				UserRoleEntity role = new UserRoleEntity();
				role.setRole_id(map.get("role_id").toString());
				role.setRole_name(map.get("role_name").toString());
				roleList.add(role);
			}
		}
		return roleList;
	}
	@Override
	public List<RoleAuthorityEntity> loadRoleAuthority(String orle_id) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = newUsersDao.loadRoleAuthority(orle_id);
		List<RoleAuthorityEntity> authorityList = null;
		if(list.size()>0){
			authorityList = new ArrayList<RoleAuthorityEntity>();
			for(Map<String,Object> map : list) {
				RoleAuthorityEntity authorityEntity = new RoleAuthorityEntity();
				authorityEntity.setAuthority_id(map.get("authority_id").toString());
				authorityEntity.setAuthority_name(map.get("authority_title").toString());
				authorityEntity.setAuthority_code(map.get("authority_code").toString());
				authorityList.add(authorityEntity);
			}
		}
		return authorityList;
	}
	@Override
	public List<Map<String, Object>> loadCombotreeDeptData(String id) {
		// TODO Auto-generated method stub
		String strId = id==null||id.equals("")?"0":id;
		return newUsersDao.loadCombotreeDeptData(strId);
	}

}
