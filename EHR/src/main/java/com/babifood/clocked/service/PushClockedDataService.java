package com.babifood.clocked.service;

import org.springframework.stereotype.Service;

@Service
public interface PushClockedDataService {
	public String pushDataToOA(int year,int month);
}
