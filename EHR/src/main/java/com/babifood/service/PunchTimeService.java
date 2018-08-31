package com.babifood.service;

import java.util.Map;

import com.babifood.entity.PunchTimeEntity;

public interface PunchTimeService {

	public abstract Map<String, Object> findPagePunchTimeInfo(Integer paramInteger1, Integer paramInteger2,
			String paramString1, String paramString2);

	public abstract Map<String, Object> savePagePunchTimeInfo(PunchTimeEntity paramPunchTimeEntity);

	public abstract Map<String, Object> removePagePunchTimeInfo(Integer paramInteger);
}
