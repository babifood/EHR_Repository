package com.babifood.clocked.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.babifood.clocked.entrty.OfficeDaKaRecord;
@Repository
public interface OfficeDaKaDao {
	public List<OfficeDaKaRecord> loadOfficeDaKaData(int year,int month) throws Exception;
}
