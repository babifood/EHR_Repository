package com.babifood.clocked.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.clocked.entrty.Person;
import com.babifood.clocked.rule.ClockedYesNoRule;
import com.babifood.utils.UtilDateTime;
public class ClockedUtil {
	public static ClockedResultBases markClockedYesNo(Person person,Date systemFrontDate,ClockedYesNoRule clockedYesNoRule) throws Exception{
		ClockedResultBases clockedResult = new ClockedResultBases();
		clockedResult.setYear(UtilDateTime.getYear(systemFrontDate));
		clockedResult.setMonth(UtilDateTime.getMonth(systemFrontDate));
		clockedResult.setWorkNum(person.getWorkNum());
		clockedResult.setUserName(person.getUserName());
		clockedResult.setCompanyCode(person.getCompanyCode());
		clockedResult.setCompany(person.getCompany());
		clockedResult.setOrganCode(person.getOrganCode());
		clockedResult.setOrgan(person.getOrgan());
		clockedResult.setDeptCode(person.getDeptCode());
		clockedResult.setDept(person.getDept());
		clockedResult.setOfficeCode(person.getOfficeCode());
		clockedResult.setOffice(person.getOffice());
		clockedResult.setGroupCode(person.getGroupCode());
		clockedResult.setGroupName(person.getGroupName());
		clockedResult.setPostCode(person.getPostCode());
		clockedResult.setPost(person.getPost());
		clockedResult.setCheckingType(person.getDaKaType());//0行政考勤,1移动考勤,2不考勤
		//设置考勤日期
		clockedResult.setCheckingDate(systemFrontDate);
		clockedResult.setWeek(UtilDateTime.getWeekOfDate(systemFrontDate));
		//设置标准上下班时间及工作时长及排班类型
		clockedYesNoRule.setStandWorkTime(person, clockedResult, systemFrontDate);
		//设置考勤标志
		clockedYesNoRule.markClockedYesNo(person, clockedResult);
		//根据考勤标志判断当天是否显示标准上班时间和下班时间及工作时长
		if(clockedResult.getClockFlag()==0){
			clockedResult.setStandWorkLength(0d);
		}
		return clockedResult;
	}
	public static Date getDate(int year,int month,int day) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String temp = null;
		temp = String.valueOf(year);

		if (month < 10) {
			temp = temp + "-0" + month;
		} else {
			temp = temp + "-" + month;
		}

		if (day < 10) {
			temp = temp + "-0" + day;
		} else {
			temp = temp + "-" + day;
		}
		return sdf.parse(temp);
	}
}
