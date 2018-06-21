package com.babifood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.babifood.entity.LoginEntity;
@Repository
public interface LoginDao {
	public LoginEntity findLogin(String user_name,String password);
	public List<Map<String, Object>> findLoginWhereUserName(String user_name);
}
