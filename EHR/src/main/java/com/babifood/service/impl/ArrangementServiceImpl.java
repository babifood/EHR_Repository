package com.babifood.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.ArrangementBaseTimeDao;
import com.babifood.dao.ArrangementDao;
import com.babifood.dao.DeptPageDao;
import com.babifood.dao.PersonInFoDao;
import com.babifood.entity.ArrangementEntity;
import com.babifood.service.ArrangementBaseTimeService;
import com.babifood.service.ArrangementService;
import com.babifood.utils.UtilString;

@Service
public class ArrangementServiceImpl implements ArrangementService {

	@Autowired
	private ArrangementDao arrangementDao;
	
	@Autowired
	private DeptPageDao deptPageDao;
	
	@Autowired
	private PersonInFoDao personInFoDao;
	
	@Autowired
	private ArrangementBaseTimeDao baseTimeDao;
	
	/**
	 * 查询所有排班信息
	 */
	@Override
	public Map<String, Object> findListArrangements(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		int count = 0;
		List<Map<String, Object>> arrangementList = new ArrayList<Map<String, Object>>();
		try {
			count = arrangementDao.queryArrangementsCount();
			arrangementList = arrangementDao.findListArrangements(map);
			result.put("code", "1");
			result.put("total", count);
			result.put("rows", arrangementList);
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询数据失败");
		}
		return result;
	}
	
	/**
	 * 保存排班信息
	 */
	@Override
	public Map<String, Object> saveArrangement(ArrangementEntity arrangement,String deptCodes,String pIds) {
		Map<String, Object> result = new HashMap<String, Object>();
		int[] counts = null;
		try {
			//查询相关机构部门信息
			List<Map<String, Object>> depts = findDepts(deptCodes);
			//查询相关员工信息
			List<Map<String, Object>> persons = findPerson(pIds);
			//获取需保存的排班信息
			List<Object[]> arrangementList = getArrangementList(arrangement,depts,persons);
			if(arrangementList != null && arrangementList.size() > 0){//保存排班信息
				counts = arrangementDao.saveArrangementList(arrangementList);
			}
			if(counts != null && counts[0] >= 1){
				result.put("code", "1");
				result.put("msg", "数据保存成功");
			} else {
				result.put("code", "0");
				result.put("msg", "数据保存失败");
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "数据异常");
		}
		return result;
	}
	
	/**
	 * 获取需保存的排班信息
	 * @param arrangement
	 * @param depts
	 * @param persons
	 * @return
	 * @throws Exception
	 */
	private List<Object[]> getArrangementList(ArrangementEntity arrangement, List<Map<String, Object>> depts,
			List<Map<String, Object>> persons) throws Exception{
		List<Object[]> arrangementList = new ArrayList<Object[]>();
		Map<String, Object> baseTime = baseTimeDao.findArrangementBaseTimeById(arrangement.getBaseTimeId());
		if(depts != null && depts.size() > 0){//相关部门排班信息
			for (Map<String, Object> map : depts) {
				Object[] arrangementEntity = new Object[]{
					arrangement.getDate(),arrangement.getBaseTimeId(),baseTime.get("startTime")+"",
					baseTime.get("endTime")+"",arrangement.getAttendance(),arrangement.getIsAttend(),
					map.get("type")+"",map.get("deptCode")+"",map.get("deptName")+"",arrangement.getCreatorId(),
					arrangement.getCreatorName(),arrangement.getRemark()
				};
				arrangementList.add(arrangementEntity);
			}
		}
		if(persons != null && persons.size() > 0){//相关员工排班信息
			for (Map<String, Object> map : persons) {
				Object[] arrangementEntity = new Object[]{
					arrangement.getDate(),arrangement.getBaseTimeId(),baseTime.get("startTime")+"",
					baseTime.get("endTime")+"",arrangement.getAttendance(),arrangement.getIsAttend(),
					"10",map.get("pId")+"",map.get("pName")+"",arrangement.getCreatorId(),
					arrangement.getCreatorName(),arrangement.getRemark()
				};
				arrangementList.add(arrangementEntity);
			}
		}
		return arrangementList;
	}

	/**
	 * 根据员工id查询员工信息
	 * @param pIds
	 * @return
	 */
	private List<Map<String, Object>> findPerson(String pIds) {
		if(UtilString.isEmpty(pIds)){
			return new ArrayList<Map<String, Object>>();
		}
		String[] ids = pIds.split(",");
		return personInFoDao.findPersonListByIds(ids);
	}

	/**
	 * 根据部门编号批量查询部门信息
	 * @param deptCodes
	 * @return
	 */
	private List<Map<String, Object>> findDepts(String deptCodes){
		if(UtilString.isEmpty(deptCodes)){
			return new ArrayList<Map<String, Object>>();
		}
		String[] deptCodeList = deptCodes.split(",");
		return deptPageDao.findDeptsByDeptCodes(deptCodeList);
	}

	/**
	 * 删除排班信息
	 */
	@Override
	public Map<String, Object> removeArrangement(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		int count = 0;
		try {
			count = arrangementDao.removeArrangement(id);
			if(count >= 1){
				result.put("code", "1");
				result.put("msg", "删除成功");
			} else {
				result.put("code", "0");
				result.put("msg", "删除失败");
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "数据异常");
		}
		return result;
	}

}
