package com.babifood.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface HomePageService {
	public List<Map<String,Object>> LoadTerrMenu(String id,String role_id);

	public List<Map<String, Object>> loadBirthday();

	public List<Map<String, Object>> loadZhuanZheng();

	public List<Map<String, Object>> loadCertificateExpire();
	
}
