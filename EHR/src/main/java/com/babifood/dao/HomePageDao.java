package com.babifood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface HomePageDao {
	public List<Map<String,Object>> LoadTreeMenu(String id,String role_id);

	public List<Map<String, Object>> loadBirthday(String beginDate, String endDate);

	public List<Map<String, Object>> loadZhuanZheng(String beginDate, String endDate);

	public List<Map<String, Object>> loadCertificateExpire(String beginDate, String endDate);

	public List<Map<String, Object>> loadWorkInOutForms(String thisYear);

	
}
