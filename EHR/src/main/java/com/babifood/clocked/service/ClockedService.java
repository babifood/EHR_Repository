package com.babifood.clocked.service;

import org.springframework.stereotype.Service;

@Service
public interface ClockedService {
	public int[] init(int year,int month);
}
