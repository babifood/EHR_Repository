package com.babifood.clocked.service.impl;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.clocked.dao.ClockedResultBaseDao;
import com.babifood.clocked.dao.PersonDao;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.clocked.entrty.Person;
import com.babifood.clocked.rule.ClockedYesNoRule;
import com.babifood.clocked.service.ClockedService;
import com.babifood.clocked.service.PeraonClockedService;
import com.babifood.clocked.util.ClockedUtil;
import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.entity.LoginEntity;
import com.babifood.utils.UtilDateTime;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;
@Service
public class ClockedServiceImpl implements ClockedService {
	public static final Logger log = Logger.getLogger(ClockedServiceImpl.class);
	@Autowired
	PeraonClockedService peraonClockedService;
	@Autowired
	ClockedResultBaseDao clockedResult;
	@Autowired
	ClockedYesNoRule clockedYesNoRule;
	@Override
	@LogMethod(module = ModuleConstant.CLOCKED)
	public int[] init(int year,int month){
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATING_TYPE_INITIALIZE);
		//初始化当前系统日期的整月信息
		List<ClockedResultBases> dataList  = null; 
		Calendar tempCal = Calendar.getInstance();
		int sysYear = year==0?tempCal.get(Calendar.YEAR):year;
		int sysMonth = month==0?tempCal.get(Calendar.MONTH)+1:month;
		// TODO Auto-generated method stub
		
		//根据员工档案获取考勤人员信息
		List<Person> personList=null;
		int [] rows =null;
		try {
				personList = peraonClockedService.filtrateClockedPeraonData(sysYear,sysMonth);
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
				rows = clockedResult.saveClockedResultBase(dataList,sysYear,sysMonth);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LogManager.putContectOfLogInfo(e.getMessage());
				log.error("init():"+e.getMessage());
			}
		return rows;
	}
}
