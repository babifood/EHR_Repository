package com.babifood.service;

import java.util.Map;

import com.babifood.entity.ArrangementEntity;

public interface ArrangementService {

	Map<String, Object> findListArrangements(Map<String, Object> map);

	Map<String, Object> saveArrangement(ArrangementEntity arrangement,String deptCodes,String pIds);

	Map<String, Object> removeArrangement(Integer id);

}
