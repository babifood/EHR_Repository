package com.babifood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface UserTreeDao {
	//部门树
	public List<Map<String,Object>> loaduserTreeDept();
	//未选人员by部门id
	public List<Map<String,Object>> loadunSelectPersonByDeptID(String dept_id);
	//未选人员by人员姓名
	public List<Map<String,Object>> loadunSelectPersonByPersonName(String p_name);
	//已选中人员by奖惩列表中的记录ID----这个要改的，要改成根据查询按钮的那个input直接显示里面的值
	public List<Map<String,Object>> loadinSelectPersonByRapId(Integer rap_id);
	//已选人员by人员姓名
	public List<Map<String,Object>> loadinSelectPersonByPersonName(String p_name);
}
