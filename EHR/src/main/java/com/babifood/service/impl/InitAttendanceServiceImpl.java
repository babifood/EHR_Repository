package com.babifood.service.impl;

import java.util.List;
import java.util.Map;

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

	@Override
	public Map<String, Object> summaryArrangementInfo(String year, String month, String pNumber) {
		return initAttendanceDao.summaryArrangementInfo(year, month, pNumber);
	}

	@Override
	public Double findYearSickHours(String year, String month, String pNumber, String endDate) {
		return initAttendanceDao.findYearSickHours(year, month, pNumber,endDate);
	}

	@Override
	public List<Map<String, Object>> findCurrentMonthSick(String year, String month, String pNumber) {
		return initAttendanceDao.findCurrentMonthSick(year, month, pNumber);
	}

}
