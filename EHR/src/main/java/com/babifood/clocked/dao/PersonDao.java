package com.babifood.clocked.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.babifood.clocked.entrty.Person;
@Repository
public interface PersonDao {
	public List<Person> loadPeraonClockedInfo(int yaer,int month);
}
