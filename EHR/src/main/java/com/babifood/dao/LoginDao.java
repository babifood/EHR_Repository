package com.babifood.dao;

import org.springframework.stereotype.Repository;

import com.babifood.entity.LoginEntity;
@Repository
public interface LoginDao {
	public LoginEntity findLogin(String user_name,String password);
}
