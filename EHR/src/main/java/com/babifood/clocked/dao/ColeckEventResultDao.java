package com.babifood.clocked.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.babifood.clocked.entrty.ClockedBizData;
@Repository
public interface ColeckEventResultDao {
	public List<ClockedBizData> loadColeckEventResultData(int year,int month) throws Exception;
}
