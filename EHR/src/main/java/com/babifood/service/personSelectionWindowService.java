package com.babifood.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface personSelectionWindowService {
	//部门树
	public List<Map<String,Object>> loadpersonSelectionWindowDept();
	
	//未选人员by部门id
	public List<Map<String,Object>> loadunSelectPersonByDeptID(String dept_id);
	
	//未选人员by人员姓名
	public List<Map<String,Object>> loadunSelectPersonByPersonName(String p_name);
	
	//已选人员by人员姓名
	public List<Map<String,Object>> loadinSelectPersonByPersonName(String p_name);
	
}
