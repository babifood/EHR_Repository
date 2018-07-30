package com.babifood.clocked.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.babifood.clocked.entrty.Holiday;
@Repository
public interface ClokedHolidayDao {
	public List<Holiday> loadHolidayDatas(int year);
}
