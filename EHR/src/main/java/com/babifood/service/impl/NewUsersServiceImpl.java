package com.babifood.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.constant.OperationConstant;
import com.babifood.dao.NewUsersDao;
import com.babifood.dao.impl.LoginDaoImpl;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.RoleAuthorityEntity;
import com.babifood.entity.RoleMenuEntity;
import com.babifood.entity.UserRoleEntity;
import com.babifood.service.NewUsersService;
import com.babifood.utils.IdGen;
import com.babifood.utils.MD5;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;
@Service
public class NewUsersServiceImpl implements NewUsersService {
	public static final Logger log = Logger.getLogger(NewUsersServiceImpl.class);
	@Autowired
	NewUsersDao newUsersDao;
	@LogMethod
	@Override
	public List<Map<String, Object>> loadUserAll(String user_name,String show_name) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> userList =null;
		List<Map<String, Object>> roleList =null;
		try {
			userList = newUsersDao.loadUser(user_name, show_name);
			roleList = newUsersDao.loadUserAll(user_name, show_name);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadUserAll:"+e.getMessage());
		}
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
	@LogMethod
	@Override
	public List<Map<String, Object>> loadRoleAll(String role_name) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list =null;
		try {
			list = newUsersDao.loadRoleAll(role_name);
			LogManager.putContectOfLogInfo("参数:"+role_name);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadUserAll:"+e.getMessage());
		}
		return list;
	}
	@LogMethod
	@Override
	public Integer saveRole(String role_name, String role_desc, String state,String organization_code,String organization_name) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
		String role_id = IdGen.uuid();
		int rows = -1;
		try {
			rows = newUsersDao.saveRole(role_id, role_name, role_desc, state, organization_code, organization_name);
			LogManager.putContectOfLogInfo("参数:"+role_id+ role_name+role_desc+state+organization_code+organization_name);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("saveRole:"+e.getMessage());
		}
		return rows;
	}
	@LogMethod
	@Override
	public Integer editRole(String role_id, String role_name, String role_desc, String state,String organization_code,String organization_name) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EDIT);
		int rows = -1;
		try {
			rows = newUsersDao.editRole(role_id, role_name, role_desc, state,organization_code,organization_name);
			LogManager.putContectOfLogInfo("参数:"+role_id+role_name+role_desc+state+organization_code+organization_name);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("editRole:"+e.getMessage());
		}
		return rows;
	}
	@LogMethod
	@Override
	public Integer removeRole(String role_id) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
		int rows = 1;
		try {
			// TODO Auto-generated method stub
			newUsersDao.removeRole(role_id);
			LogManager.putContectOfLogInfo("参数:"+role_id);
		} catch (Exception e) {
			// TODO: handle exception
			rows = -1;
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("removeRole:"+e.getMessage());
		}
		return rows;
	}
	@LogMethod
	@Override
	public List<Map<String, Object>> loadComboboxData() {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			list = newUsersDao.loadComboboxData();
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadComboboxData:"+e.getMessage());
		}
		return list;
	}
	@LogMethod
	@Override
	public Integer saveUser(LoginEntity userEntity) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
		int rows = 1;
		userEntity.setUser_id(IdGen.uuid());
		userEntity.setPassword(MD5.gtePasswordMd5(userEntity.getPassword()));
		try {
			// TODO Auto-generated method stub
			newUsersDao.saveUser(userEntity);
			LogManager.putContectOfLogInfo("参数:"+userEntity);
		} catch (Exception e) {
			// TODO: handle exception
			rows = -1;
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("saveUser:"+e.getMessage());
		}
		return rows;
	}
	@LogMethod
	@Override
	public Integer editUser(LoginEntity userEntity) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EDIT);
		int rows = 1;
		String password = userEntity.getPassword();
		String[] passwordArr = password.split("/");
		if(passwordArr[0].equals(passwordArr[1])){
			userEntity.setPassword(passwordArr[0]);
		}else{
			userEntity.setPassword(MD5.gtePasswordMd5(passwordArr[1]));
		}
		try {
			newUsersDao.editUser(userEntity);
			LogManager.putContectOfLogInfo("参数:"+userEntity);
		} catch (Exception e) {
			// TODO: handle exception
			rows = -1;
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("saveUser:"+e.getMessage());
		}
		return rows;
	}
	@LogMethod
	@Override
	public Integer removeUser(String user_id) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
		int rows = 1;
		try {
			// TODO Auto-generated method stub
			newUsersDao.removeUser(user_id);
		} catch (Exception e) {
			// TODO: handle exception
			rows = -1;
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("saveUser:"+e.getMessage());
		}
		return rows;
	}
	@LogMethod
	@Override
	public Integer saveMenuRole(RoleMenuEntity[] roleMenuEntity) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
		int rows = 1;
		try {
			// TODO Auto-generated method stub
			newUsersDao.saveMenuRole(roleMenuEntity);
		} catch (Exception e) {
			// TODO: handle exception
			rows = -1;
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("saveMenuRole:"+e.getMessage());
		}
		return rows;
	}
	@LogMethod
	@Override
	public List<Map<String, Object>> getMenuIds(String role_id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			list = newUsersDao.getMenuIds(role_id);
			LogManager.putContectOfLogInfo("参数:"+role_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("getMenuIds:"+e.getMessage());
		}
		return list;
	}
	@LogMethod
	@Override
	public List<Map<String, Object>> loadCheckTreeMenu(String id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		String strId = id==null||id.equals("")?"0":id;
		try {
			list = newUsersDao.loadCheckTreeMenu(strId);
			LogManager.putContectOfLogInfo("参数:"+strId);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadCheckTreeMenu:"+e.getMessage());
		}
		return list;
	}
	@LogMethod
	@Override
	public List<UserRoleEntity> loadRoleWhereUser(String user_id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String,Object>> list = null;
		try {
			list = newUsersDao.loadRoleWhereUser(user_id);
			LogManager.putContectOfLogInfo("参数:"+user_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadRoleWhereUser:"+e.getMessage());
		}
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
	@LogMethod
	@Override
	public List<RoleAuthorityEntity> loadRoleAuthority(String orle_id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		
		List<Map<String,Object>> list = null;
		try {
			list = newUsersDao.loadRoleAuthority(orle_id);
			LogManager.putContectOfLogInfo("参数:"+orle_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadRoleAuthority:"+e.getMessage());
		}
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
	@LogMethod
	@Override
	public List<Map<String, Object>> loadCombotreeDeptData(String id) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		// TODO Auto-generated method stub
		String strId = id==null||id.equals("")?"0":id;
		List<Map<String, Object>> list = null;
		try {
			list = newUsersDao.loadCombotreeDeptData(strId);
			LogManager.putContectOfLogInfo("参数:"+strId);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadCombotreeDeptData:"+e.getMessage());
		}
		return list;
	}

}
