package com.babifood.service;

import java.util.List;

import com.babifood.entity.InitAttendanceEntity;

public interface InitAttendanceService {

	public boolean isInitAttendance(String pNumber, String year, String month);

	public void addInitAttendance(List<InitAttendanceEntity> attendanceList);

}
