package com.babifood.dao;

import java.util.List;
import java.util.Map;

public interface PerformanceDao {

	public Integer getPerformancesCount(Map<String, Object> params);

	public List<Map<String, Object>> queryPerformanceList(Map<String, Object> params);

	public void savePerformance(List<Object[]> performanceParam);

//	public Map<String, Object> getPerformanceInfo(String year, String month, String pNumber);

	public void updatePerformanceScore(String year, String month, String pNumber, String score, String salary);

	public List<Map<String, Object>> loadUserDataAuthority();

	public List<String> findPNumberList(List<Map<String, Object>> auths);

	public void savePerformancList(List<Map<String, Object>> performancList);

}
