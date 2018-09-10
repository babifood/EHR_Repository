package com.babifood.clocked.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.clocked.dao.ClockedResultBaseDao;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.clocked.service.ClockedService;
import com.babifood.clocked.service.LoadClockedResultService;
import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.entity.LoginEntity;
import com.babifood.utils.UtilDateTime;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;
@Service
public class LoadClockedResultServiceImpl implements LoadClockedResultService {
	public static final Logger log = Logger.getLogger(LoadClockedResultServiceImpl.class);
	@Autowired
	ClockedResultBaseDao clockedResultBaseDao;
	@Autowired
	ClockedService clockedServiceImpl;
	@Override
	@LogMethod(module = ModuleConstant.CLOCKED)
	public List<Map<String, Object>> loadClockedResultData(int year,int month,String workNum,String periodEndDate){
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list =null;
		try {
			clockedResultBaseDao.loadClockedResultData(year,month,workNum,periodEndDate);
			LogManager.putContectOfLogInfo("考勤明细查询");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadClockedResultData:"+e.getMessage());
		}
		return list;
	}
	@Override
	@LogMethod(module = ModuleConstant.CLOCKED)
	public List<ClockedResultBases> loadClockedResultDataList(int year, int month){
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ClockedResultBases> clockedResultList = null;
		List<Map<String, Object>> list = null;
		try {
			list = clockedResultBaseDao.loadClockedResultDataList(year,month);
			LogManager.putContectOfLogInfo("查询考勤业务表信息");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadClockedResultDataList:"+e.getMessage());
		}
		if(list.size()>0){
			clockedResultList = new ArrayList<ClockedResultBases>();
			for (Map<String, Object> map : list) {
				ClockedResultBases c = new ClockedResultBases();
				c.setYear(Integer.parseInt(map.get("Year")==null?"0":map.get("Year").toString()));
				c.setMonth(Integer.parseInt(map.get("Month")==null?"0":map.get("Month").toString()));
				c.setWorkNum(map.get("WorkNum")==null?"":map.get("WorkNum").toString());
				c.setUserName(map.get("UserName")==null?"":map.get("UserName").toString());
				c.setCompanyCode(map.get("CompanyCode")==null?"":map.get("CompanyCode").toString());
				c.setCompany(map.get("Company")==null?"":map.get("Company").toString());
				c.setOrganCode(map.get("OrganCode")==null?"":map.get("OrganCode").toString());
				c.setOrgan(map.get("Organ")==null?"":map.get("Organ").toString());
				c.setDeptCode(map.get("DeptCode")==null?"":map.get("DeptCode").toString());
				c.setDept(map.get("Dept")==null?"":map.get("Dept").toString());
				c.setOfficeCode(map.get("OfficeCode")==null?"":map.get("OfficeCode").toString());
				c.setOffice(map.get("Office")==null?"":map.get("Office").toString());
				c.setGroupCode(map.get("GroupCode")==null?"":map.get("GroupCode").toString());
				c.setGroupName(map.get("GroupName")==null?"":map.get("GroupName").toString());
				c.setPostCode(map.get("PostCode")==null?"":map.get("PostCode").toString());
				c.setPost(map.get("Post")==null?"":map.get("Post").toString());
				c.setCheckingType(map.get("CheckingType")==null?"":map.get("CheckingType").toString());
				c.setPaiBanType(map.get("PaiBanType")==null?"":map.get("PaiBanType").toString());
				try {
					c.setCheckingDate(map.get("checkingDate")==null?null:df.parse(map.get("checkingDate").toString()));
					LogManager.putContectOfLogInfo("日期类型转换错误");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					LogManager.putContectOfLogInfo(e.getMessage());
					log.error("日期类型转换错误:"+e.getMessage());
				}
				c.setWeek(map.get("Week")==null?"":map.get("Week").toString());
				c.setBeginTime(map.get("beginTime")==null?"":map.get("beginTime").toString());
				c.setEndTime(map.get("endTime")==null?"":map.get("endTime").toString());
				c.setStandWorkLength(map.get("standardWorkLength")==null?0d:Double.parseDouble(map.get("standardWorkLength").toString()));
				
				c.setCheckingBeginTime(null);
				c.setCheckingEndTime(null);
				c.setOriginalCheckingLength(0d);
				c.setActualWorkLength(0d);
				c.setChiDao(0);
				c.setZaoTui(0);
				c.setKuangGong(0d);
				c.setNianJia(0d);
				c.setTiaoXiu(0d);
				c.setShiJia(0d);
				c.setBingJia(0d);
				c.setPeiXunJia(0d);
				c.setHunJia(0d);
				c.setChanJia(0d);
				c.setPeiChanJia(0d);
				c.setSangJia(0d);
				c.setOtherQingJia(0d);
				c.setQueQin(0d);
				c.setQingJia(0d);
				c.setYiDong(0d);
				c.setJiaBan(0d);
				c.setChuCha(0d);
				c.setCanBu(0);
				c.setEventBeginTime(null);
				c.setEventEndTime(null);
				
				c.setClockFlag(map.get("Clockflag")==null?0:Integer.parseInt(map.get("Clockflag").toString()));
				clockedResultList.add(c);
			}
		}
		return clockedResultList;
	}
	@Override
	@LogMethod(module = ModuleConstant.CLOCKED)
	public List<Map<String, Object>> loadSumClockedResultData(String workNum, String userName){
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String periodEndDate = "";
		String sysDate = df.format(new Date());
		Calendar tempCal = Calendar.getInstance();
		int sysYear = tempCal.get(Calendar.YEAR);
		int sysMonth = tempCal.get(Calendar.MONTH)+1;	
		List<Map<String, Object>> sumList =null;
		try {
			sumList = clockedResultBaseDao.loadSumClockedResultData(workNum, userName);
			LogManager.putContectOfLogInfo("查询考勤汇总信息");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadSumClockedResultData:"+e.getMessage());
		}
		for(int i=0;i<sumList.size();i++){
			Map<String, Object> newMap = sumList.get(i);
			int year = Integer.parseInt(newMap.get("Year").toString());
			int month = Integer.parseInt(newMap.get("Month").toString());
			if(sysYear==year&&sysMonth==month){
				periodEndDate = sysDate;
			}else{
				periodEndDate = df.format(UtilDateTime.getMonthEndSqlDate(year,month));
			}
			newMap.put("period", newMap.get("Year").toString()+"-"+newMap.get("Month").toString()+"-01至"+periodEndDate);
			newMap.put("periodEndDate",periodEndDate);
		}
		return sumList;
	}

}
