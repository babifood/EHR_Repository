package com.babifood.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.InitAttendanceDao;
import com.babifood.entity.InitAttendanceEntity;
import com.babifood.service.InitAttendanceService;

@Service
public class InitAttendanceServiceImpl implements InitAttendanceService {

	@Autowired
	private InitAttendanceDao initAttendanceDao;
	
	@Override
	public boolean isInitAttendance(String pNumber, String year, String month) {
		int count = initAttendanceDao.queryAttendanceCountAttendance(pNumber,year,month);
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public void addInitAttendance(List<InitAttendanceEntity> attendanceList) {
		initAttendanceDao.addInitAttendance(attendanceList);
	}

}
