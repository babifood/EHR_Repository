package com.babifood.clocked.service;

import java.io.OutputStream;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public interface ExportClockService {

	Map<String, Object> exportSumClockedResult(OutputStream ouputStream,String searchKey,String searchVal,String myYear,String myMonth,String comp,String organ,String dept);

	Map<String, Object> exportDetailsClockedResult(OutputStream ouputStream, Integer year, Integer month,
			String workNum, String periodEndDate);
	
}
