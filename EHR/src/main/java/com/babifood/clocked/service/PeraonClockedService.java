package com.babifood.clocked.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.babifood.clocked.entrty.Person;
@Service
public interface PeraonClockedService {
	public List<Person> filtrateClockedPeraonData(int yaer,int month) throws Exception;
}
