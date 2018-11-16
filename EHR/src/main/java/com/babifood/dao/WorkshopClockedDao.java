package com.babifood.dao;

import java.util.List;
import java.util.Map;

public interface WorkshopClockedDao {

	List<Map<String, Object>> loadWorkshopClocked(String workNumber, String userName);

	void saveimportExcelWorkshopClocked(List<Object[]> workshopClockedParam);

	List<Map<String, Object>> loadUserDataAuthority();

	List<String> findPNumberList(List<Map<String, Object>> auths);

}
