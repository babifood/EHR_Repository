package com.babifood.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.LoginEntity;
import com.babifood.entity.RoleMenuEntity;
import com.babifood.service.NewUsersService;

@Controller
public class NewUserControl {
	@Autowired
	NewUsersService newUsersService;
	/**
	 * 查询所有用户
	 * @return 返回用户json
	 */
	@ResponseBody
	@RequestMapping("/loadUsersAll")
	public Map<String,Object> loadUsersAll(String user_name,String show_name){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = newUsersService.loadUserAll(user_name,show_name);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	/**
	 * 保存用户
	 * @param userEntity 用户对象
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/saveUser")
	public Map<String,Object> saveUser(LoginEntity userEntity){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = newUsersService.saveUser(userEntity);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 修改用户
	 * @param userEntity 用户对象
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/editUser")
	public Map<String,Object> editUser(LoginEntity userEntity){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = newUsersService.editUser(userEntity);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 删除用户
	 * @param user_id 用户id
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/removeUser")
	public Map<String,Object> removeUser(String user_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = newUsersService.removeUser(user_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 查询所有角色
	 * @return 返回角色json
	 */
	@ResponseBody
	@RequestMapping("/loadRolesAll")
	public Map<String,Object> loadRolesAll(String role_name){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = newUsersService.loadRoleAll(role_name);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	/**
	 * 加载角色下拉框数据
	 * @return 返回角色json
	 */
	@ResponseBody
	@RequestMapping("/loadComboboxData")
	public List<Map<String, Object>> loadComboboxData(){
		List<Map<String, Object>> list = newUsersService.loadComboboxData();
		return list;
	}
	/**
	 * 保存角色
	 * @param role_name 角色名称
	 * @param role_desc 角色描述
	 * @param state 角色状态
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/saveRole")
	public Map<String,Object> saveRole(String role_name,String role_desc,String state){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = newUsersService.saveRole(role_name, role_desc, state);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 修改角色
	 * @param role_id 角色id
	 * @param role_name 角色名称
	 * @param role_desc 角色描述
	 * @param state 角色状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editRole")
	public Map<String,Object> editRole(String role_id,String role_name,String role_desc,String state){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = newUsersService.editRole(role_id, role_name, role_desc, state);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 删除角色
	 * @param role_id 角色id
	 * @return map
	 */
	@ResponseBody
	@RequestMapping("/removeRole")
	public Map<String,Object> removeRole(String role_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = newUsersService.removeRole(role_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	@ResponseBody
	@RequestMapping("/saveMenuRole")
	public Map<String,Object> saveMenuRole(@RequestBody RoleMenuEntity[] roleMenuEntity){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = newUsersService.saveMenuRole(roleMenuEntity);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
		
	}
	/**
	 * 获取角色授权的对应ids
	 * @return 返回角色json
	 */
	@ResponseBody
	@RequestMapping("/getMenuIdsCheck")
	public List<Map<String, Object>> getMenuIds(String role_id){
		List<Map<String, Object>> list = newUsersService.getMenuIds(role_id);
		return list;
	}
	/**
	 * 获取导航菜单列表
	 * @param id父及菜单id
	 * @return 返回菜单对象集合
	 */
	@ResponseBody
	@RequestMapping("/loadCheckTreeMenu")
	public List<Map<String,Object>> loadCheckTreeMenu(String id){
		return newUsersService.loadCheckTreeMenu(id);
	}
}
