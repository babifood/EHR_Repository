package com.babifood.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.dao.HomePageDao;
import com.babifood.entity.LoginEntity;
import com.babifood.service.HomePageService;
import com.babifood.utils.UtilDateTime;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;
@Service
public class HomePageServiceImpl implements HomePageService {
	public static final Logger log = Logger.getLogger(HomePageServiceImpl.class);
	@Autowired
	HomePageDao homePageDao;
	@Override
	@LogMethod(module = ModuleConstant.HOMEPAGE)
	public List<Map<String, Object>> LoadTerrMenu(String id,String role_id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		String strId = id==null||id.equals("")?"0":id;
		List<Map<String, Object>> terrMenuList = null;
		try {
			terrMenuList = homePageDao.LoadTreeMenu(strId, role_id);
			LogManager.putContectOfLogInfo("执行LoadTerrMenu方法,查询菜单");
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("LoadTerrMenu:"+e.getMessage());
		}
		for (Map<String, Object> map : terrMenuList) {
			if(map.get("flag").equals("1")){
				map.put("state", "open");
			}
		}
		return terrMenuList;
	}
	@Override
	@LogMethod(module = ModuleConstant.HOMEPAGE)
	public List<Map<String, Object>> loadBirthday() {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String beginDate = df.format(new Date());
		String endDate = df.format(UtilDateTime.getSystemFrontDate(15));
		List<Map<String, Object>> list = null;
		try {
			list = homePageDao.loadBirthday(beginDate, endDate);
			LogManager.putContectOfLogInfo("执行loadBirthday方法,查询员工生日");
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadBirthday:"+e.getMessage());
		}
		for (Map<String, Object> map : list) {
			map.put("work_birthday", map.get("p_month")+"月"+map.get("p_day")+"日");
		}
		return list;
	}
	@Override
	@LogMethod(module = ModuleConstant.HOMEPAGE)
	public List<Map<String, Object>> loadZhuanZheng() {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String beginDate = df.format(new Date());
		String endDate = df.format(UtilDateTime.getSystemFrontDate(15));
		List<Map<String, Object>> list = null;
		try {
			list = homePageDao.loadZhuanZheng(beginDate, endDate);
			LogManager.putContectOfLogInfo("执行loadZhuanZheng方法,查询员工转正信息");
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadZhuanZheng:"+e.getMessage());
		}
		for (Map<String, Object> map : list) {
			try {
				map.put("shiyongqi", UtilDateTime.getDaySpace(map.get("p_in_date").toString(),map.get("p_turn_date").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				log.error("loadZhuanZheng方法日期转换异常:"+e.getMessage());
			}
		}
		return list;
	}
	@Override
	@LogMethod(module = ModuleConstant.HOMEPAGE)
	public List<Map<String, Object>> loadCertificateExpire() {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String beginDate = df.format(new Date());
		String endDate = df.format(UtilDateTime.getSystemFrontDate(15));
		List<Map<String, Object>> list = null;
		try {
			list = homePageDao.loadCertificateExpire(beginDate, endDate);
			LogManager.putContectOfLogInfo("执行loadCertificateExpire方法,查询证件到期信息");
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadCertificateExpire:"+e.getMessage());
		}
		return list;
	}
	@Override
	@LogMethod(module = ModuleConstant.HOMEPAGE)
	public List<Map<String, Object>> loadWorkInOutForms() {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
		String thisYear = df.format(new Date());
		List<Map<String, Object>> list = null;
		try {
			list = homePageDao.loadWorkInOutForms(thisYear);
			LogManager.putContectOfLogInfo("执行loadWorkInOutForms方法,统计当年入离职人员信息");
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadWorkInOutForms:"+e.getMessage());
		}
		return list;
	}
	//劳动合同到期提醒
	@Override
	@LogMethod(module = ModuleConstant.HOMEPAGE)
	public List<Map<String, Object>> loadContractExpire() {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String beginDate = df.format(new Date());
		String endDate = df.format(UtilDateTime.getSystemFrontDate(15));
		List<Map<String, Object>> list = null;
		try {
			list = homePageDao.loadContractExpire(beginDate, endDate);
			LogManager.putContectOfLogInfo("执行loadContractExpire方法,查询合同到期信息");
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadContractExpire:"+e.getMessage());
		}
		return list;
	}

}
