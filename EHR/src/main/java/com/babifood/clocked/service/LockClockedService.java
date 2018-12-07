package com.babifood.clocked.service;

import org.springframework.stereotype.Service;

@Service
public interface LockClockedService {

	int[] lockData(Integer sysYear, Integer sysMonth);
	
}
