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
	@ResponseBody
	@RequestMapping("/loadRolesAll")
	public Map<String,Object> loadRolesAll(){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = newUsersService.loadRoleAll();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
}
