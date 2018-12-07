package com.babifood.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.LoginDao;
import com.babifood.entity.LoginEntity;
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
	@Override
	public LoginEntity findLoginWhereUserName(String user_name) {
		// TODO Auto-generated method stub
		LoginEntity login = null;
		List<Map<String,Object>> list = loginDao.findLoginWhereUserName(user_name);
		if(list.size()<1){
			return null;
		}else{
			Map<String,Object> map = list.get(0);
			login = new LoginEntity();
			login.setUser_id(map.get("user_id").toString());
			login.setUser_name(map.get("user_name").toString());
			login.setUser_password(map.get("user_password").toString());
			login.setShow_name(map.get("show_name").toString());
			login.setPhone(map.get("phone")==null?null:map.get("phone").toString());
			login.setE_mail(map.get("e_mail")==null?null:map.get("e_mail").toString());
			login.setState(map.get("state").toString());
			return login;
		}
		
	}

}
