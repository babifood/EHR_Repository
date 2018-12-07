package com.babifood.clocked.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.clocked.dao.ClockedResultBaseDao;
import com.babifood.clocked.dao.LockClockedDao;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.clocked.service.LockClockedService;
import com.babifood.constant.OperationConstant;
import com.babifood.entity.LoginEntity;
import com.cn.babifood.operation.LogManager;
@Service
public class LockClockedServiceImpl implements LockClockedService {
	public static final Logger log = Logger.getLogger(LockClockedServiceImpl.class);
	@Autowired
	LockClockedDao lockClockedDao;
	@Autowired
	ClockedResultBaseDao clockedResultBaseDao;
	@Override
	public int[] lockData(Integer sysYear, Integer sysMonth) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ARCHIVE);
		int [] rows = null;
		try {
			List<ClockedResultBases> list = loadClockedResultDataList(sysYear,sysMonth);
			rows =  lockClockedDao.updateLockDataFlag(list);
			LogManager.putContectOfLogInfo("更新数据状态");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("更新数据状态:"+e.getMessage());
		}
		return rows;
	}
	public List<ClockedResultBases> loadClockedResultDataList(Integer year, Integer month) throws Exception{
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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
				c.setKuangGongCiShu(0);
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
				c.setInOutJob(0d);
				c.setClockFlag(map.get("Clockflag")==null?0:Integer.parseInt(map.get("Clockflag").toString()));
				c.setDataflag("3");
				clockedResultList.add(c);
			}
		}
		return clockedResultList;
	}

}
