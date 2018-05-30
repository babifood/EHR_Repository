package com.babifood.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.personSelectionWindowDao;
import com.babifood.service.personSelectionWindowService;
@Service
public class personSelectionWindowServiceImpl implements personSelectionWindowService {
	@Autowired
	personSelectionWindowDao personSelectionWindowDao;
	//部门树
	@Override
	public List<Map<String, Object>> loadpersonSelectionWindowDept() {
		// TODO Auto-generated method stub
		return personSelectionWindowDao.loadpersonSelectionWindowDept();
	}
	//未选人员by部门id
	@Override
	public List<Map<String, Object>> loadunSelectPersonByDeptID(String dept_id) {
		// TODO Auto-generated method stub
		return personSelectionWindowDao.loadunSelectPersonByDeptID(dept_id);
	}
	//未选人员by人员姓名
	@Override
	public List<Map<String, Object>> loadunSelectPersonByPersonName(String p_name) {
		// TODO Auto-generated method stub
		return personSelectionWindowDao.loadunSelectPersonByPersonName(p_name);
	}
	//已选人员by人员姓名
	@Override
	public List<Map<String, Object>> loadinSelectPersonByPersonName(String p_name) {
		// TODO Auto-generated method stub
		return personSelectionWindowDao.loadinSelectPersonByPersonName(p_name);
	}

}
