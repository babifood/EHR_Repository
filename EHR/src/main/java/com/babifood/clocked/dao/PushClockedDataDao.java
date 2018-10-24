package com.babifood.clocked.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.babifood.clocked.entrty.PushOaDataEntrty;

@Repository
public interface PushClockedDataDao {
	public List<Map<String,Object>> loadClockingInData(int year,int month) throws Exception;
	public void deleteOAClockingInData(List<PushOaDataEntrty> list,int year,int month) throws Exception;
}
