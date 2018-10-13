package com.babifood.clocked.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.babifood.clocked.entrty.Person;
@Repository
public interface PersonDao {
	public List<Map<String,Object>> loadPeraonClockedInfo(int yaer,int month);
}
