package com.babifood.service;

import org.springframework.stereotype.Service;

@Service
public interface LoginService {
	public Object loginServiceMethod(String user_name,String password);
}
