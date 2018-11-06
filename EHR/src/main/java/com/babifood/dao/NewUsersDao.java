package com.babifood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.babifood.entity.LoginEntity;
import com.babifood.entity.RoleAuthorityEntity;
import com.babifood.entity.RoleMenuEntity;
import com.babifood.entity.RoleResourceEntity;
@Repository
public interface NewUsersDao {
	public List<Map<String,Object>> loadUserAll(String user_name,String show_name);
	
	public List<Map<String,Object>> loadUser(String user_name,String show_name);
	
	public List<Map<String,Object>> loadRoleAll(String role_name);
	
	public Integer saveRole(String role_id,String role_name,String role_desc,String state,String organization_code,String organization_name);
	
	public Integer editRole(String role_id,String role_name,String role_desc,String state,String organization_code,String organization_name);
	
	public void removeRole(String role_id);
	
	public List<Map<String,Object>> loadRoleWhereUser(String user_id);
	
	public List<Map<String,Object>> loadComboboxData();
	
	public void saveUser(LoginEntity userEntity);
	
	public void editUser(LoginEntity userEntity);
	
	public void removeUser(String user_id);
	
	public void saveMenuRole(RoleMenuEntity[] roleMenuEntity);
	
	public List<Map<String,Object>> getMenuIds(String role_id);
	
	public List<Map<String,Object>> loadCheckTreeMenu(String id);

	public List<Map<String, Object>> loadRoleAuthority(String orle_id);

	public List<Map<String, Object>> loadCombotreeDeptData(String id);
	
	public List<Map<String, Object>> loadAllocationResourceTree(String resource);

	public void saveRoleResource(RoleResourceEntity[] roleResourceEntity);

	public List<Map<String, Object>> loadRoleResource(String role_id);
}
