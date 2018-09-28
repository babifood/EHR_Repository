package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.InitAttendanceEntity;

public interface InitAttendanceDao {

	public int queryAttendanceCountAttendance(String pNumber, String year, String month);

	public void addInitAttendance(List<InitAttendanceEntity> attendanceList);

	public Map<String, Object> summaryArrangementInfo(String year, String month, String pNumber);

	public Double findYearSickHours(String year, String month, String pNumber, String endDate);

	public List<Map<String, Object>> findCurrentMonthSick(String year, String month, String pNumber);

}
