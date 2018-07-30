package com.babifood.clocked.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.clocked.dao.ClockedResultBaseDao;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.clocked.service.ClockedService;
import com.babifood.clocked.service.LoadClockedResultService;
import com.babifood.utils.UtilDateTime;
@Service
public class LoadClockedResultServiceImpl implements LoadClockedResultService {
	@Autowired
	ClockedResultBaseDao clockedResultBaseDao;
	@Autowired
	ClockedService clockedServiceImpl;
	@Override
	public List<Map<String, Object>> loadClockedResultData(int year,int month,String workNum,String periodEndDate) throws Exception {
		// TODO Auto-generated method stub
		return clockedResultBaseDao.loadClockedResultData(year,month,workNum,periodEndDate);
	}
	@Override
	public List<ClockedResultBases> loadClockedResultDataList(int year, int month) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ClockedResultBases> clockedResultList = null;
		List<Map<String, Object>> list = clockedResultBaseDao.loadClockedResultDataList(year,month);
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
				c.setCheckingDate(map.get("checkingDate")==null?null:df.parse(map.get("checkingDate").toString()));
				c.setWeek(map.get("Week")==null?"":map.get("Week").toString());
				c.setBeginTime(map.get("beginTime")==null?"":map.get("beginTime").toString());
				c.setEndTime(map.get("endTime")==null?"":map.get("endTime").toString());
				c.setStandWorkLength(map.get("standardWorkLength")==null?0d:Double.parseDouble(map.get("standardWorkLength").toString()));
				c.setCheckingBeginTime(map.get("checkingBeginTime")==null?null:dftime.parse(map.get("checkingBeginTime").toString()));
				c.setCheckingEndTime(map.get("checkingEndTime")==null?null:dftime.parse(map.get("checkingEndTime").toString()));
				c.setOriginalCheckingLength(map.get("originalCheckingLength")==null?0d:Double.parseDouble(map.get("originalCheckingLength").toString()));
				c.setActualWorkLength(map.get("actualWorkLength")==null?0d:Double.parseDouble(map.get("actualWorkLength").toString()));
				c.setChiDao(map.get("chiDao")==null?0:Integer.parseInt(map.get("chiDao").toString()));
				c.setZaoTui(map.get("zaoTui")==null?0:Integer.parseInt(map.get("zaoTui").toString()));
				c.setKuangGong(map.get("kuangGong")==null?0d:Double.parseDouble(map.get("kuangGong").toString()));
				c.setNianJia(map.get("nianJia")==null?0d:Double.parseDouble(map.get("nianJia").toString()));
				c.setTiaoXiu(map.get("tiaoXiu")==null?0d:Double.parseDouble(map.get("tiaoXiu").toString()));
				c.setShiJia(map.get("shiJia")==null?0d:Double.parseDouble(map.get("shiJia").toString()));
				c.setBingJia(map.get("bingJia")==null?0d:Double.parseDouble(map.get("bingJia").toString()));
				c.setPeiXunJia(map.get("peixunJia")==null?0d:Double.parseDouble(map.get("peixunJia").toString()));
				c.setHunJia(map.get("hunJia")==null?0d:Double.parseDouble(map.get("hunJia").toString()));
				c.setChanJia(map.get("chanJia")==null?0d:Double.parseDouble(map.get("chanJia").toString()));
				c.setPeiChanJia(map.get("PeiChanJia")==null?0d:Double.parseDouble(map.get("PeiChanJia").toString()));
				c.setSangJia(map.get("SangJia")==null?0d:Double.parseDouble(map.get("SangJia").toString()));
				c.setOtherQingJia(map.get("Qita")==null?0d:Double.parseDouble(map.get("Qita").toString()));
				c.setQueQin(map.get("Queqin")==null?0d:Double.parseDouble(map.get("Queqin").toString()));
				c.setQingJia(map.get("Qingjia")==null?0d:Double.parseDouble(map.get("Qingjia").toString()));
				c.setYiDong(map.get("Yidong")==null?0d:Double.parseDouble(map.get("Yidong").toString()));
				c.setJiaBan(map.get("Jiaban")==null?0d:Double.parseDouble(map.get("Jiaban").toString()));
				c.setChuCha(map.get("Chuchai")==null?0d:Double.parseDouble(map.get("Chuchai").toString()));
				c.setCanBu(map.get("Canbu")==null?0:Integer.parseInt(map.get("Canbu").toString()));
				c.setEventBeginTime(map.get("EventBeginTime")==null?null:dftime.parse(map.get("EventBeginTime").toString()));
				c.setEventEndTime(map.get("EventEndTime")==null?null:dftime.parse(map.get("EventEndTime").toString()));
				c.setClockFlag(map.get("Clockflag")==null?0:Integer.parseInt(map.get("Clockflag").toString()));
				clockedResultList.add(c);
			}
		}
		return clockedResultList;
	}
	@Override
	public List<Map<String, Object>> loadSumClockedResultData(String workNum, String userName) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String periodEndDate = "";
		String sysDate = df.format(new Date());
		Calendar tempCal = Calendar.getInstance();
		int sysYear = tempCal.get(Calendar.YEAR);
		int sysMonth = tempCal.get(Calendar.MONTH)+1;	
		List<Map<String, Object>> sumList = clockedResultBaseDao.loadSumClockedResultData(workNum, userName);
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
