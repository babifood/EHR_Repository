package com.babifood.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.babifood.dao.NewUsersDao;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.RoleMenuEntity;
@Repository
public class NewUsersDaoImpl implements NewUsersDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	//角色方法
	@Override
	public List<Map<String, Object>> loadRoleAll(String role_name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select role_id,role_name,role_desc,state ");
		sql.append(" from ehr_roles");
		if(role_name!=null||!role_name.equals("")){
			sql.append(" where role_name like '%"+role_name+"%'");
		}
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}
	@Override
	public Integer saveRole(String role_id,String role_name,String role_desc,String state) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_roles (role_id,role_name,role_desc,state) ");
		sql.append(" values(?,?,?,?)");
		Object[] params=new Object[4];
		params[0]=role_id;
		params[1]=role_name;
		params[2]=role_desc;
		params[3]=state;
		int rows =-1;
		try {
			rows = jdbctemplate.update(sql.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
	@Override
	public Integer editRole(String role_id, String role_name, String role_desc, String state) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_roles set ROLE_NAME=?,ROLE_DESC=?,STATE=? where ROLE_ID=?");
		Object[] params=new Object[4];
		params[0]=role_name;
		params[1]=role_desc;
		params[2]=state;
		params[3]=role_id;
		int rows =-1;
		try {
			rows = jdbctemplate.update(sql.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
	@Transactional("txManager")
	@Override
	public Integer removeRole(String role_id) {
		// TODO Auto-generated method stub
		int state = 1;
		try {
			StringBuffer sql_role = new StringBuffer();
			sql_role.append("delete from ehr_roles where role_id=?");
			StringBuffer sql_user_role = new StringBuffer();
			sql_user_role.append("delete from ehr_user_role where user_role_id =?");
			StringBuffer sql_menu_role = new StringBuffer();
			sql_menu_role.append("delete from ehr_role_menu where role_menu_id =?");
			jdbctemplate.update(sql_role.toString(),role_id);
			jdbctemplate.update(sql_user_role.toString(),role_id);
			jdbctemplate.update(sql_menu_role.toString(),role_id);
		} catch (Exception e) {
			// TODO: handle exception
			state = -1;
			log.error("查询错误："+e.getMessage());
		}
		return state;
	}
	//用户方法
	@Override
	public List<Map<String, Object>> loadUserAll(String user_name,String show_name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select u.user_id,u.user_name,u.password,u.show_name,u.e_mail,u.phone,u.state,r.role_id,r.role_name");
		sql.append(" from ehr_users u inner join ehr_user_role r on u.user_id = r.user_id where 1=1");
		if(user_name!=null&&!user_name.equals("")){
			sql.append(" and u.user_name like '%"+user_name+"%'");
		}
		if(show_name!=null&&!show_name.equals("")){
			sql.append(" and u.show_name like '%"+show_name+"%'");
		}
		
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> loadComboboxData() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select role_id as id,role_name as text ");
		sql.append(" from ehr_roles where state = '1'");
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return list;		
	}
	@Transactional("txManager")
	@Override
	public Integer saveUser(LoginEntity userEntity) {
		// TODO Auto-generated method stub
		int rows =1;
		try {
			StringBuffer sqlUser = new StringBuffer();
			sqlUser.append("insert into ehr_users (user_id,user_name,password,show_name,e_mail,phone,state) ");
			sqlUser.append(" values(?,?,?,?,?,?,?)");
			Object[] params=new Object[7];
			params[0]=userEntity.getUser_id();
			params[1]=userEntity.getUser_name();
			params[2]=userEntity.getPassword();
			params[3]=userEntity.getShow_name();
			params[4]=(userEntity.getE_mail()==null||userEntity.getE_mail().equals(""))?"":userEntity.getE_mail();
			params[5]=(userEntity.getPhone()==null||userEntity.getPhone().equals(""))?"":userEntity.getPhone();
			params[6]=userEntity.getState();
			
			StringBuffer sqlUser_Role = new StringBuffer();
			sqlUser_Role.append("insert into ehr_user_role (user_id,user_name,role_id,role_name) ");
			sqlUser_Role.append(" values(?,?,?,?)");
			Object[] User_Role_params=new Object[4];
			User_Role_params[0]=userEntity.getUser_id();
			User_Role_params[1]=userEntity.getUser_name();
			User_Role_params[2]=userEntity.getRole_id();
			User_Role_params[3]=userEntity.getRole_name();
		
		
			jdbctemplate.update(sqlUser.toString(), params);
			jdbctemplate.update(sqlUser_Role.toString(), User_Role_params);
		} catch (Exception e) {
			// TODO: handle exception
			rows =-1;
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
	@Transactional("txManager")
	@Override
	public Integer editUser(LoginEntity userEntity) {
		// TODO Auto-generated method stub
		int rows =1;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("update ehr_users set user_name=?,password=?,show_name=?,e_mail=?,phone=?,state=? where user_id=?");
			Object[] params=new Object[7];
			params[0]=userEntity.getUser_name();
			params[1]=userEntity.getPassword();
			params[2]=userEntity.getShow_name();
			params[3]=(userEntity.getE_mail()==null||userEntity.getE_mail().equals(""))?"":userEntity.getE_mail();
			params[4]=(userEntity.getPhone()==null||userEntity.getPhone().equals(""))?"":userEntity.getPhone();
			params[5]=userEntity.getState();
			params[6]=userEntity.getUser_id();
			
			StringBuffer sqlUser_Role = new StringBuffer();
			sqlUser_Role.append("update ehr_user_role set user_id=?,user_name=?,role_id=?,role_name=? where user_id=? ");
			Object[] User_Role_params=new Object[5];
			User_Role_params[0]=userEntity.getUser_id();
			User_Role_params[1]=userEntity.getUser_name();
			User_Role_params[2]=userEntity.getRole_id();
			User_Role_params[3]=userEntity.getRole_name();
			User_Role_params[4]=userEntity.getUser_id();
		
			jdbctemplate.update(sql.toString(), params);
			jdbctemplate.update(sqlUser_Role.toString(), User_Role_params);
		} catch (Exception e) {
			// TODO: handle exception
			rows =-1;
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
	@Transactional("txManager")
	@Override
	public Integer removeUser(String user_id) {
		// TODO Auto-generated method stub
		int state = 1;
		try {
			StringBuffer sql_user = new StringBuffer();
			sql_user.append("delete from ehr_users where user_id=?");
			StringBuffer sql_user_role = new StringBuffer();
			sql_user_role.append("delete from ehr_user_role where user_id =?");
			jdbctemplate.update(sql_user.toString(),user_id);
			jdbctemplate.update(sql_user_role.toString(),user_id);
		} catch (Exception e) {
			// TODO: handle exception
			state = -1;
			log.error("查询错误："+e.getMessage());
		}
		return state;
	}
	@Transactional("txManager")
	@Override
	public Integer saveMenuRole(RoleMenuEntity[] roleMenuEntity) {
		// TODO Auto-generated method stub
		int row = 1;
		StringBuffer sql_menu = new StringBuffer();
		sql_menu.append("delete from ehr_role_menu where role_id=?");
		
		List<Object[]> params = new ArrayList<>();
		for(int i=0;i<roleMenuEntity.length;i++){
			params.add(new Object[]{
					roleMenuEntity[i].getRole_id(),
					roleMenuEntity[i].getRole_name(),
					roleMenuEntity[i].getId(),
					roleMenuEntity[i].getText()
					});
		}
		final String sql = "insert into ehr_role_menu (role_id,role_name,menu_tbo_id,title) values(?,?,?,?)";
		
		try {
			jdbctemplate.update(sql_menu.toString(),roleMenuEntity[0].getRole_id());
			jdbctemplate.batchUpdate(sql.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
			row =-1;
			log.error("查询错误："+e.getMessage());
		}
		return row;
	}
	@Override
	public List<Map<String, Object>> getMenuIds(String role_id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select role_id,menu_tbo_id from ehr_role_menu where role_id=?");
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString(),role_id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return list;	
	}

}
