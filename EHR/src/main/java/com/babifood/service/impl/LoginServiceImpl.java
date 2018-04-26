package com.babifood.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.LoginDao;
import com.babifood.dao.impl.LoginDaoImpl;
import com.babifood.service.LoginService;
import com.babifood.utils.MD5;
@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	LoginDao loginDao;
	@Override
	public Object loginServiceMethod(String user_name, String password) {
		// TODO Auto-generated method stub
		
		return loginDao.findLogin(user_name, MD5.gtePasswordMd5(password));
			
	}

}
