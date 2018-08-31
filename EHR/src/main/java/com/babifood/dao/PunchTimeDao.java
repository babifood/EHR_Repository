package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.PunchTimeEntity;

public interface PunchTimeDao {

	public abstract Integer getPagePunchTimeInfoCount(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> findPunchTimeInfo(Map<String, Object> paramMap);

	public abstract void savePagePunchTimeInfo(PunchTimeEntity paramPunchTimeEntity);

	public abstract void removePagePunchTimeInfo(Integer paramInteger);
	
}
