package com.babifood.service;

import org.springframework.stereotype.Service;

import com.babifood.entity.LoginEntity;

@Service
public interface LoginService {
	public Object loginServiceMethod(String user_name,String password);
	public LoginEntity findLoginWhereUserName(String user_name);
}
