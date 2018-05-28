package com.babifood.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.UserTreeDao;
import com.babifood.service.UserTreeService;
@Service
public class UserTreeServiceImpl implements UserTreeService {
	@Autowired
	UserTreeDao userTreeDao;
	//部门树
	@Override
	public List<Map<String, Object>> loaduserTreeDept() {
		// TODO Auto-generated method stub
		return userTreeDao.loaduserTreeDept();
	}
	//未选人员by部门id
	@Override
	public List<Map<String, Object>> loadunSelectPersonByDeptID(String dept_id) {
		// TODO Auto-generated method stub
		return userTreeDao.loadunSelectPersonByDeptID(dept_id);
	}
	//未选人员by人员姓名
	@Override
	public List<Map<String, Object>> loadunSelectPersonByPersonName(String p_name) {
		// TODO Auto-generated method stub
		return userTreeDao.loadunSelectPersonByPersonName(p_name);
	}
	//已选中人员by奖惩列表中的记录ID----这个要改的，要改成根据查询按钮的那个input直接显示里面的值
	@Override
	public List<Map<String, Object>> loadinSelectPersonByRapId(Integer rap_id) {
		// TODO Auto-generated method stub
		return userTreeDao.loadinSelectPersonByRapId(rap_id);
	}
	//已选人员by人员姓名
	@Override
	public List<Map<String, Object>> loadinSelectPersonByPersonName(String p_name) {
		// TODO Auto-generated method stub
		return userTreeDao.loadinSelectPersonByPersonName(p_name);
	}

}
