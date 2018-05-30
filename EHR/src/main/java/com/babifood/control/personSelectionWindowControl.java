package com.babifood.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.service.personSelectionWindowService;

@Controller
public class personSelectionWindowControl {
	@Autowired
	personSelectionWindowService personSelectionWindowService;
	/**
	 * 获取部门导航菜单列表
	 * @return 返回部门导航菜单对象集合
	 */
	@ResponseBody
	@RequestMapping("/personSelectionWindowDept")
	public List<Map<String,Object>> loadpersonSelectionWindowDept(){
		return personSelectionWindowService.loadpersonSelectionWindowDept();
	}
	
	/**
	 * 查询未选人员by部门ID
	 * @param dept_id 部门ID
	 * @return 返回人员json
	 */
	@ResponseBody
	@RequestMapping("/unSelectPersonByDeptID")
	public Map<String,Object> loadunSelectPersonByDeptID(String dept_id){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personSelectionWindowService.loadunSelectPersonByDeptID(dept_id);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	
	/**
	 * 查询未选人员by人员姓名
	 * @param p_name 人员姓名
	 * @return 返回人员json
	 */
	@ResponseBody
	@RequestMapping("/unSelectPersonByPersonName")
	public Map<String,Object> loadunSelectPersonByPersonName(String p_name){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personSelectionWindowService.loadunSelectPersonByPersonName(p_name);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
}
