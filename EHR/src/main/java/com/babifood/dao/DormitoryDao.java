package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.DormitoryCostEntity;
import com.babifood.entity.DormitoryEntity;
import com.babifood.entity.DormitoryStayEntiry;

public interface DormitoryDao {

	public void addDormitory(DormitoryEntity dormitory);
	
	public void updateDormitory(Map<String, Object> dormitoryInfo);
	
	public Map<String, Object> findDormitoryById(String id);
	
	public List<Map<String, Object>> getUnStayDormitory(Map<String, Object> params);

	public List<Map<String, Object>> getStayDormitory(Map<String, Object> params);

	public Map<String, Object> findCheakingDormitory(String dormitoryId);

	public void insertCheakingDormitory(DormitoryStayEntiry dormStay);

	public int cheakoutDormitory(String bedNo, String pnumber);

	public Map<String, Object> findDormitoryInfo(String floor, String roomNo, String bedNo);

	public int getCountOfUnStayDormitory(Map<String, Object> params);

	public int getCountOfStayDormitory(Map<String, Object> params);

	public int queryDormitoryCostCount(Map<String, Object> params);

	public List<Map<String, Object>> queryDormitoryCostList(Map<String, Object> params);

	public void saveCost(DormitoryCostEntity dormitoryCost);

	public void removeCost(Integer id);

	public void moveOutProcedure(String dormitoryId, String pnumber, String outTime);

	public void saveDormitoryCosts(List<Object[]> performanceParam);

	public List<Map<String, Object>> loadUserDataAuthority();

	public List<String> findPNumberList(List<Map<String, Object>> auths);


}
