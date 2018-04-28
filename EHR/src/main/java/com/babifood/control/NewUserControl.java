package com.babifood.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public Map<String,Object> loadUsersAll(){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = newUsersService.loadUserAll();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	/**
	 * 查询所有角色
	 * @return 返回角色json
	 */
	@ResponseBody
	@RequestMapping("/loadRolesAll")
	public Map<String,Object> loadRolesAll(){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = newUsersService.loadRoleAll();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
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
}
