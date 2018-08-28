package com.babifood.dao;

import java.util.List;
import java.util.Map;

public interface SynchronousOaAccountInfoDao {
	public List<Map<String, Object>> loadOaWorkNumInFo(String workNum,String userName);
}
