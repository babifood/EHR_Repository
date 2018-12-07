package com.babifood.clocked.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.babifood.clocked.entrty.ClockedResultBases;

@Repository
public interface LockClockedDao {

	int[] updateLockDataFlag(List<ClockedResultBases> updateDataList)  throws Exception ;
	
}
