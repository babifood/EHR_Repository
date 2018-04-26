package com.babifood.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.HomePageDao;
import com.babifood.service.HomePageService;
@Service
public class HomePageServiceImpl implements HomePageService {
	@Autowired
	HomePageDao homePageDao;
	@Override
	public List<Map<String, Object>> LoadTerrMenu(String id) {
		// TODO Auto-generated method stub
		String strId = id==null||id.equals("")?"0":id;
		return homePageDao.LoadTreeMenu(strId);
	}

}
