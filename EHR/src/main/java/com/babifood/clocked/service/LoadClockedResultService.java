package com.babifood.clocked.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.babifood.clocked.entrty.ClockedResultBases;
@Service
public interface LoadClockedResultService {
	public List<Map<String,Object>> loadClockedResultData(int year,int month,String workNum,String periodEndDate);
	public List<Map<String,Object>> loadSumClockedResultData(String searchKey,String searchVal,String myYear,String myMonth,String comp,String organ,String dept);
	public List<ClockedResultBases> loadClockedResultDataList(int year,int month) throws Exception;
}
