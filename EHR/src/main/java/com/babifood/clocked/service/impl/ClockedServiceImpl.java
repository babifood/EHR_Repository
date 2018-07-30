package com.babifood.clocked.service.impl;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.clocked.dao.ClockedResultBaseDao;
import com.babifood.clocked.dao.PersonDao;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.clocked.entrty.Person;
import com.babifood.clocked.rule.ClockedYesNoRule;
import com.babifood.clocked.service.ClockedService;
import com.babifood.clocked.util.ClockedUtil;
import com.babifood.utils.UtilDateTime;
@Service
public class ClockedServiceImpl implements ClockedService {
	@Autowired
	PersonDao personDao;
	@Autowired
	ClockedResultBaseDao clockedResult;
	@Autowired
	ClockedYesNoRule clockedYesNoRule;
	@Override
	public int[] init(int year,int month) throws Exception {
		//初始化系统当前日期的上一天
//		List<ClockedResultBases> dataList  = new ArrayList<ClockedResultBases>();
//		Calendar tempCal = Calendar.getInstance();
//		int year = tempCal.get(Calendar.YEAR);
//		int month = tempCal.get(Calendar.MONTH)+1;
//		// TODO Auto-generated method stub
//		//获取当前系统时间的前一天
//		Date systemFrontDate =  UtilDateTime.getSystemFrontDate();
//		//根据员工档案获取考勤人员信息
//		List<Person> personList = personDao.loadPeraonClockedInfo();
//		int persronSize = personList == null ? 0 : personList.size();
//		if (persronSize > 0) {
//			clockedYesNoRule.init(year, month);
//		}
//		Person tmpPerson = null;
//		for (int k = 0; k < persronSize; k++) {
//			tmpPerson = personList.get(k);
//			dataList.add(ClockedUtil.markClockedYesNo(tmpPerson,systemFrontDate,clockedYesNoRule));
//		}
//		//保存数据
//		clockedResult.saveClockedResultBase(dataList);
		//初始化当前系统日期的整月信息
		List<ClockedResultBases> dataList  = null; 
		Calendar tempCal = Calendar.getInstance();
		int sysYear = year==0?tempCal.get(Calendar.YEAR):year;
		int sysMonth = month==0?tempCal.get(Calendar.MONTH)+1:month;
		// TODO Auto-generated method stub
		
		//根据员工档案获取考勤人员信息
		List<Person> personList = personDao.loadPeraonClockedInfo(sysYear,sysMonth);
		int daySize = UtilDateTime.getDaySize(sysYear, sysMonth);
		int persronSize = personList == null ? 0 : personList.size();
		dataList = new ArrayList<ClockedResultBases>(daySize*persronSize);
		if (persronSize > 0) {
			clockedYesNoRule.init(sysYear, sysMonth);
		}
		Person tmpPerson = null;
		for(int day = 1; day <= daySize; day++){
			for (int k = 0; k < persronSize; k++) {
				tmpPerson = personList.get(k);
				dataList.add(ClockedUtil.markClockedYesNo(tmpPerson,ClockedUtil.getDate(sysYear, sysMonth, day),clockedYesNoRule));
			}
		}
		
		//保存数据
		return clockedResult.saveClockedResultBase(dataList);
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		//1加载数据
		//2根据打卡记录计算相关数据
		//3根据考勤业务计算相关数据
		//4保存数据
		//5推送OA考勤结果
	}

	@Override
	public void destory() {
		// TODO Auto-generated method stub

	}
//	public static void main(String[] args) {
//		Calendar tempCal = Calendar.getInstance();
//		int year = tempCal.get(Calendar.YEAR);
//		int month = tempCal.get(Calendar.MONTH)+1;
//		System.out.println(year);
//		System.out.println(month);
//	}
}
