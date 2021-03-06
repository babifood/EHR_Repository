package com.babifood.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.babifood.dao.NewUsersDao;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.RoleMenuEntity;
import com.babifood.entity.RoleResourceEntity;
@Repository
@Transactional
public class NewUsersDaoImpl implements NewUsersDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	public static final Logger log = Logger.getLogger(NewUsersDaoImpl.class);
	//角色方法
	@Override
	public List<Map<String, Object>> loadRoleAll(String role_name) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select role_id,role_name,role_desc,state,organization_name,organization_code,resource");
		sql.append(" from ehr_roles");
		if(role_name!=null||!role_name.equals("")){
			sql.append(" where role_name like '%"+role_name+"%'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public Integer saveRole(String role_id,String role_name,String role_desc,String state,String organization_code,String organization_name) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_roles (role_id,role_name,role_desc,state,organization_name,organization_code,resource) ");
		sql.append(" values(?,?,?,?,?,?,?)");
		Object[] params=new Object[7];
		params[0]=role_id;
		params[1]=role_name;
		params[2]=role_desc;
		params[3]=state;
		params[4]=organization_name;
		params[5]=organization_code;
		params[6]="1";
		return jdbctemplate.update(sql.toString(), params);
	}
	@Override
	public Integer editRole(String role_id, String role_name, String role_desc, String state,String organization_code,String organization_name) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_roles set role_name=?,role_desc=?,state=?,organization_name=?,organization_code=? where ROLE_ID=?");
		Object[] params=new Object[6];
		params[0]=role_name;
		params[1]=role_desc;
		params[2]=state;
		params[3]=organization_name;
		params[4]=organization_code;
		params[5]=role_id;
		return jdbctemplate.update(sql.toString(), params);
	}
	@Override
	public void removeRole(String role_id) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sql_role = new StringBuffer();
		sql_role.append("delete from ehr_roles where role_id=?");
		StringBuffer sql_user_role = new StringBuffer();
		sql_user_role.append("delete from ehr_user_role where user_role_id =?");
		StringBuffer sql_menu_role = new StringBuffer();
		sql_menu_role.append("delete from ehr_role_menu where role_menu_id =?");
		StringBuffer ehr_role_authority = new StringBuffer();
		ehr_role_authority.append("delete from ehr_role_authority where role_id =?");
		StringBuffer ehr_role_resource = new StringBuffer();
		ehr_role_resource.append("delete from ehr_role_resource where role_id =?");
		jdbctemplate.update(sql_role.toString(),role_id);
		jdbctemplate.update(sql_user_role.toString(),role_id);
		jdbctemplate.update(sql_menu_role.toString(),role_id);
		jdbctemplate.update(ehr_role_authority.toString(),role_id);
		jdbctemplate.update(ehr_role_resource.toString(),role_id);
	}
	//查询用户及角色信息
	@Override
	public List<Map<String, Object>> loadUserAll(String user_name,String show_name) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select u.user_id,u.user_name,u.user_password,u.show_name,u.e_mail,u.phone,u.state,r.role_id,r.role_name");
		sql.append(" from ehr_users u inner join ehr_user_role r on u.user_id = r.user_id where 1=1");
		if(user_name!=null&&!user_name.equals("")){
			sql.append(" and u.user_name like '%"+user_name+"%'");
		}
		if(show_name!=null&&!show_name.equals("")){
			sql.append(" and u.show_name like '%"+show_name+"%'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	//查询用户信息
	@Override
	public List<Map<String, Object>> loadUser(String user_name, String show_name) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select u.user_id,u.user_name,u.user_password,u.show_name,u.e_mail,u.phone,u.state");
		sql.append(" from ehr_users u  where 1=1");
		if(user_name!=null&&!user_name.equals("")){
			sql.append(" and u.user_name like '%"+user_name+"%'");
		}
		if(show_name!=null&&!show_name.equals("")){
			sql.append(" and u.show_name like '%"+show_name+"%'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public List<Map<String, Object>> loadComboboxData() throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select role_id,role_name,organization_code,organization_name");
		sql.append(" from ehr_roles where state = '1' and resource = '0'");
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public void saveUser(LoginEntity userEntity) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sqlUser = new StringBuffer();
		sqlUser.append("insert into ehr_users (user_id,user_name,user_password,show_name,e_mail,phone,state) ");
		sqlUser.append(" values(?,?,?,?,?,?,?)");
		Object[] params=new Object[7];
		params[0]=userEntity.getUser_id();
		params[1]=userEntity.getUser_name();
		params[2]=userEntity.getUser_password();
		params[3]=userEntity.getShow_name();
		params[4]=(userEntity.getE_mail()==null||userEntity.getE_mail().equals(""))?"":userEntity.getE_mail();
		params[5]=(userEntity.getPhone()==null||userEntity.getPhone().equals(""))?"":userEntity.getPhone();
		params[6]=userEntity.getState();
		
		final String sqlUser_Role = "insert into ehr_user_role (user_id,user_name,role_id,role_name) values(?,?,?,?)";
		
		List<Object[]> user_Role_Params = new ArrayList<>();
		for(int i=0;i<userEntity.getRoleList().size();i++){
			user_Role_Params.add(new Object[]{
					userEntity.getUser_id(),
					userEntity.getUser_name(),
					userEntity.getRoleList().get(i).getRole_id(),
					userEntity.getRoleList().get(i).getRole_name()
					});
		}
		jdbctemplate.update(sqlUser.toString(), params);
		jdbctemplate.batchUpdate(sqlUser_Role, user_Role_Params);	
	}
	@Override
	public void editUser(LoginEntity userEntity) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_users set user_name=?,user_password=?,show_name=?,e_mail=?,phone=?,state=? where user_id=?");
		Object[] params=new Object[7];
		params[0]=userEntity.getUser_name();
		params[1]=userEntity.getUser_password();
		params[2]=userEntity.getShow_name();
		params[3]=(userEntity.getE_mail()==null||userEntity.getE_mail().equals(""))?"":userEntity.getE_mail();
		params[4]=(userEntity.getPhone()==null||userEntity.getPhone().equals(""))?"":userEntity.getPhone();
		params[5]=userEntity.getState();
		params[6]=userEntity.getUser_id();
		
		StringBuffer sql_User_Role_Del = new StringBuffer("delete from ehr_user_role where user_id=?");
		final String sqlUser_Role = "insert into ehr_user_role (user_id,user_name,role_id,role_name) values(?,?,?,?)";
		
		List<Object[]> user_Role_Params = new ArrayList<>();
		for(int i=0;i<userEntity.getRoleList().size();i++){
			user_Role_Params.add(new Object[]{
					userEntity.getUser_id(),
					userEntity.getUser_name(),
					userEntity.getRoleList().get(i).getRole_id(),
					userEntity.getRoleList().get(i).getRole_name()
					});
		}
	
		jdbctemplate.update(sql.toString(), params);
		jdbctemplate.update(sql_User_Role_Del.toString(), userEntity.getUser_id());
		jdbctemplate.batchUpdate(sqlUser_Role, user_Role_Params);
	}
	@Override
	public void removeUser(String user_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql_user = new StringBuffer();
		sql_user.append("delete from ehr_users where user_id=?");
		StringBuffer sql_user_role = new StringBuffer();
		sql_user_role.append("delete from ehr_user_role where user_id =?");
		jdbctemplate.update(sql_user.toString(),user_id);
		jdbctemplate.update(sql_user_role.toString(),user_id);
	}
	@Override
	public void saveMenuRole(RoleMenuEntity[] roleMenuEntity) throws DataAccessException{
		// TODO Auto-generated method stub
		int row = 1;
		StringBuffer sql_menu = new StringBuffer();
		sql_menu.append("delete from ehr_role_menu where role_id=?");
		
		StringBuffer sql_authority = new StringBuffer();
		sql_authority.append("delete from ehr_role_authority where role_id=?");
		
		List<Object[]> params_menu = new ArrayList<>();
		List<Object[]> params_authority = new ArrayList<>();
		for(int i=0;i<roleMenuEntity.length;i++){
			if(roleMenuEntity[i].getFlag().equals("0")||roleMenuEntity[i].getFlag().equals("1")){
				params_menu.add(new Object[]{
						roleMenuEntity[i].getRole_id(),
						roleMenuEntity[i].getRole_name(),
						roleMenuEntity[i].getId(),
						roleMenuEntity[i].getText()
						});
			}else if(roleMenuEntity[i].getFlag().equals("2")){
				params_authority.add(new Object[]{
						roleMenuEntity[i].getRole_id(),
						roleMenuEntity[i].getRole_name(),
						roleMenuEntity[i].getId(),
						roleMenuEntity[i].getText(),
						roleMenuEntity[i].getAuthority_code()
						});
			}
			
		}
		final String sql_menu_insert = "insert into ehr_role_menu (role_id,role_name,menu_tbo_id,title) values(?,?,?,?)";
		final String sql_authority_insert = "insert into ehr_role_authority (role_id,role_name,authority_id,authority_title,authority_code) values(?,?,?,?,?)";
		jdbctemplate.update(sql_menu.toString(),roleMenuEntity[0].getRole_id());
		jdbctemplate.update(sql_authority.toString(),roleMenuEntity[0].getRole_id());
		jdbctemplate.batchUpdate(sql_menu_insert, params_menu);
		jdbctemplate.batchUpdate(sql_authority_insert, params_authority);
	}
	@Override
	public List<Map<String, Object>> getMenuIds(String role_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select role_id,menu_tbo_id from ehr_role_menu where role_id =?");
		sql.append(" union ");
		sql.append("select role_id,authority_id from ehr_role_authority where role_id =?");
		List<Map<String, Object>> list = null;
		return jdbctemplate.queryForList(sql.toString(),role_id,role_id);
	}
	@Override
	public List<Map<String, Object>> loadCheckTreeMenu(String id) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select m.id,m.text,m.state,m.iconCls,m.url,m.nid,m.authority_code,flag");
		sql.append(" from ehr_menu m where m.nid = ?");
		List<Map<String,Object>> list = null;
		return jdbctemplate.queryForList(sql.toString(),id);
	}
	//根据用户查询对应的角色
	@Override
	public List<Map<String, Object>> loadRoleWhereUser(String user_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ROLE_ID,ROLE_NAME FROM EHR_USER_ROLE WHERE USER_ID=?");
		return jdbctemplate.queryForList(sql.toString(),user_id);
	}
	@Override
	public List<Map<String, Object>> loadRoleAuthority(String orle_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select authority_id,authority_title,authority_code");
		sql.append(" from ehr_role_authority where role_id in ("+orle_id+")");
		sql.append(" group by authority_id");
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public List<Map<String, Object>> loadCombotreeDeptData(String id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select dept_code as id,dept_name as text,iconcls,state,pCode as nid  from ehr_dept");
		sql.append(" where pCode =?");
		return jdbctemplate.queryForList(sql.toString(),id);
	}
	@Override
	public List<Map<String, Object>> loadAllocationResourceTree(String resource) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select dept_code as id ,dept_name as text,pcode as nid from ehr_dept where 1=1");
		if(resource.equals("0")){
			sql.append(" and type in('1','2') ORDER BY DEPT_CODE");
		}else if(resource.equals("1")){
			sql.append(" and type in('1','2','3') ORDER BY DEPT_CODE");
		}
		
		return jdbctemplate.queryForList(sql.toString());
	}
	@Override
	public void saveRoleResource(RoleResourceEntity[] roleResourceEntity) {
		// TODO Auto-generated method stub
		StringBuffer sql_roleResource_del= new StringBuffer();
		sql_roleResource_del.append("delete from ehr_role_resource where role_id=?");
		StringBuffer sql_roles_update= new StringBuffer();
		sql_roles_update.append("update ehr_roles set resource = '0' where role_id=?");
		List<Object[]> params= new ArrayList<>();
		for(int i=0;i<roleResourceEntity.length;i++){
			params.add(new Object[]{
					roleResourceEntity[i].getRole_id(),
					roleResourceEntity[i].getRole_name(),
					roleResourceEntity[i].getResource_code(),
					roleResourceEntity[i].getResource_name()
					});
			
		}
		final String sql_roleResource_insert = "insert into ehr_role_resource (role_id,role_name,resource_code,resource_name) values(?,?,?,?)";
		jdbctemplate.update(sql_roleResource_del.toString(),roleResourceEntity[0].getRole_id());
		jdbctemplate.update(sql_roles_update.toString(),roleResourceEntity[0].getRole_id());
		jdbctemplate.batchUpdate(sql_roleResource_insert, params);
	}
	@Override
	public List<Map<String, Object>> loadRoleResource(String role_id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select role_id,role_name,resource_code,resource_name from ehr_role_resource where role_id=?");
		return jdbctemplate.queryForList(sql.toString(),role_id);
	}
}
