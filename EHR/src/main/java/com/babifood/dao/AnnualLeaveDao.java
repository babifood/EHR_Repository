package com.babifood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



@Repository
public interface AnnualLeaveDao {
	
	//当前年假记录
	public List<Map<String,Object>> loadNowAnnualLeave(String npname);
	
	//历史年假记录
	public List<Map<String,Object>> loadHistoryAnnualLeave(String lpname);
		
	//取员工入职日期等信息
	public List<Map<String,Object>> GetHireDate();
	
	//查当前年假记录表中有没有变更的员工，有的话，取数据存到历史表去，当前年假表中删除对应数据后，最后把新数据插入当前年假表
	public int[] SaveAnnualLeave(List<Map<String, Object>> list);

	public List<Map<String, Object>> findOANianjia(String start);

	public List<Map<String, Object>> findCurrentNianjiaLit(String year);

	public void updateEmpCompanyAge(String pNumber, Integer companyAge);

	public List<Map<String, Object>> findTotalBingjia(String year);

	public void pushAnnualToOA(List<String> ids, List<Map<String, Object>> list, String year);
	
}
