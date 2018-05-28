package com.babifood.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.service.UserTreeService;

@Controller
public class userTreeControl {
	@Autowired
	UserTreeService userTreeService;
	/**
	 * 获取部门导航菜单列表
	 * @return 返回部门导航菜单对象集合
	 */
	@ResponseBody
	@RequestMapping("/userTreeDept")
	public List<Map<String,Object>> loaduserTreeDept(){
		return userTreeService.loaduserTreeDept();
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
		List<Map<String, Object>> list = userTreeService.loadunSelectPersonByDeptID(dept_id);
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
		List<Map<String, Object>> list = userTreeService.loadunSelectPersonByPersonName(p_name);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	/**
	 * 查询已选中人员by奖惩列表中的记录ID----这个要改的，要改成根据查询按钮的那个input直接显示里面的值
	 * @param rap_id 记录ID
	 * @return 返回人员json
	 */
	@ResponseBody
	@RequestMapping("/inSelectPersonByRapId")
	public Map<String,Object> loadinSelectPersonByRapId(Integer rap_id){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = userTreeService.loadinSelectPersonByRapId(rap_id);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
}
