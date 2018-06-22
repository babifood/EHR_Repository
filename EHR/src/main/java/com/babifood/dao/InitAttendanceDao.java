package com.babifood.dao;

import java.util.List;

import com.babifood.entity.InitAttendanceEntity;

public interface InitAttendanceDao {

	public int queryAttendanceCountAttendance(String pNumber, String year, String month);

	public void addInitAttendance(List<InitAttendanceEntity> attendanceList);

}
