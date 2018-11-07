package com.babifood.service;

import java.io.OutputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.babifood.entity.DormitoryCostEntity;
import com.babifood.entity.DormitoryEntity;
import com.babifood.entity.DormitoryStayEntiry;

public interface DormitoryService {
	
	public Map<String, Object> saveDormitory(DormitoryEntity dormitory);
	
	public Map<String, Object> removeDormitory(String id);

	public Map<String, Object> getUnStayDormitory(Integer page,Integer rows,String floor, String roomNo, String sex, String stay, String type);

	public Map<String, Object> getStayDormitory(Integer page,Integer rows,String floor, String roomNo, String sex, String pNumber, String pName, String dormType, String checkingStart, String checkingEnd, String checkoutStart, String checkoutEnd);

//	public Map<String, Object> cheakingDormitory(String dormitoryId, String pnumber,String stayTime);

	public Map<String, Object> cheakoutDormitory(DormitoryStayEntiry dormStay, String type);

	public Map<String, Object> queryDormitoryCostList(Integer page, Integer rows, String floor, String roomNo,
			String pNumber, String pName);

	public Map<String, Object> saveCost(DormitoryCostEntity dormitoryCost);

	public Map<String, Object> removeCost(Integer id);

	public Map<String, Object> exportDormitoryCosts(OutputStream ouputStream, String type, String year, String month);

	public Map<String, Object> importDormitoryCost(MultipartFile file, String type);

	public Map<String, Object> operateDormitory(DormitoryEntity dorm,DormitoryStayEntiry dormStay);

	public Map<String, Object> exportRecord(OutputStream ouputStream, String checkingStart, String checkingEnd,
			String checkoutStart, String checkoutEnd);

}
