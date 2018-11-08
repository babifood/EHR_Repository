package com.babifood.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface AnnualLeaveService {
	
	//当前年假记录
	public List<Map<String,Object>> loadNowAnnualLeave(String npname, String npnumber);
	
	//历史年假记录
	public List<Map<String,Object>> loadHistoryAnnualLeave(String lpname);

	//取员工入职日期等信息
	public List<Map<String,Object>> GetHireDate();
	
	public List<Map<String, Object>> setCompanyDayFlag(List<Map<String, Object>> list);
	
	public Map<String, Integer> computeCompanyDayFlag(String p_in_date);
	
	public List<Map<String, Object>> setAnnualLeavelist(List<Map<String, Object>> list) throws ParseException;
	
	//查当前年假记录表中有没有变更的员工，有的话，取数据存到历史表去，当前年假表中删除对应数据后，最后把新数据插入当前年假表
	public void SaveAnnualLeave(List<Map<String, Object>> list);

}
