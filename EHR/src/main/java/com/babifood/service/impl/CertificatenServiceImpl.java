package com.babifood.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.constant.OperationConstant;
import com.babifood.dao.CertificatenDao;
import com.babifood.entity.Certificaten;
import com.babifood.entity.LoginEntity;
import com.babifood.service.CertificatenService;
import com.cn.babifood.operation.LogManager;
@Service
public class CertificatenServiceImpl implements CertificatenService {
	public static final Logger log = Logger.getLogger(CertificatenServiceImpl.class);
	@Autowired
	CertificatenDao certificatenDao;
	@Override
	public List<Map<String, Object>> loadCertificaten(String c_p_number,String c_p_name) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			// TODO Auto-generated method stub
			list = certificatenDao.loadCertificaten(c_p_number, c_p_name);
			LogManager.putContectOfLogInfo("参数:"+c_p_number+c_p_name);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadPersonInFo:"+e.getMessage());
		}
		return list;
	}
	@Override
	public Integer removeCertificaten(String c_id) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
		int rows =-1;
		try {
			// TODO Auto-generated method stub
			rows = certificatenDao.removeCertificaten(c_id);
			LogManager.putContectOfLogInfo("参数:"+c_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadPersonInFo:"+e.getMessage());
		}
		return rows;
	}
	@Override
	public Integer saveCertificaten(Certificaten certificaten) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
		int rows = 1;
		try {
			// TODO Auto-generated method stub
			certificatenDao.saveCertificaten(certificaten);
			LogManager.putContectOfLogInfo("参数:"+certificaten);
		} catch (Exception e) {
			// TODO: handle exception
			rows = -1;
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadPersonInFo:"+e.getMessage());
		}
		return rows;
	}

}
