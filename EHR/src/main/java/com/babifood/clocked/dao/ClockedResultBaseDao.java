package com.babifood.clocked.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.babifood.clocked.entrty.ClockedResultBases;
@Repository
public interface ClockedResultBaseDao {
	public int[] saveClockedResultBase(List<ClockedResultBases> saveDataList,int year,int month) throws Exception;
	public int[] updateClockedResultBase(List<ClockedResultBases> saveDataList) throws Exception;
	public List<Map<String,Object>> loadClockedResultData(int year,int month,String workNum,String periodEndDate) throws Exception;
	public List<Map<String,Object>> loadClockedResultDataList(int year,int month) throws Exception;
	public List<Map<String,Object>> loadSumClockedResultData(String searchKey,String searchVal,String myYear,String myMonth) throws Exception;
}
