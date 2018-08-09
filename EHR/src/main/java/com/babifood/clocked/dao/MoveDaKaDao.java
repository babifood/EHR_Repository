package com.babifood.clocked.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.babifood.clocked.entrty.MobileDaKaLog;
@Repository
public interface MoveDaKaDao {
	public List<MobileDaKaLog> loadMobileDaKaDate(int year,int month) throws Exception;
}
