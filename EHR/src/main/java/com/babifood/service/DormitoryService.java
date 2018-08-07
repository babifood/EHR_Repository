package com.babifood.service;

import java.util.Map;

import com.babifood.entity.DormitoryEntity;

public interface DormitoryService {
	
	public Map<String, Object> saveDormitory(DormitoryEntity dormitory);
	
	public Map<String, Object> removeDormitory(String id);

	public Map<String, Object> getUnStayDormitory(Integer page,Integer rows,String floor, String roomNo, String sex, String stay);

	public Map<String, Object> getStayDormitory(Integer page,Integer rows,String floor, String roomNo, String sex, String pNumber, String pName);

	public Map<String, Object> cheakingDormitory(String dormitoryId, String pnumber,String stayTime);

	public Map<String, Object> cheakoutDormitory(String dormitoryId, String pnumber);

}
