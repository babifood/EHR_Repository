package com.babifood.clocked.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.babifood.clocked.entrty.BasicWorkCalendar;
import com.babifood.clocked.entrty.SpecialWorkCalendar;
@Repository
public interface WorkCalendarDao {
	public List<BasicWorkCalendar> loadBasicWorkCalendarDatas(int year,int month);
	public List<SpecialWorkCalendar> loadSpecialWorkCalendarDatas(int year,int month);
}
