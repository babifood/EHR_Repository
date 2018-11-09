package com.babifood.service.scheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.babifood.dao.scheduling.DakaSourceDao;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;

@Service
public class DakaSourceService {

	@Autowired
	private DakaSourceDao dakaSourceDao;
	
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	public void getDakaSource(String type) {
		// 获取最后打卡时间
		String beginDate = dakaSourceDao.findLastDay(type);
		if (UtilString.isEmpty(beginDate)) {
			beginDate = "2018-09-01 00:00:00.000";
		}
		// 查询未同步打卡数量
		final String endDate = UtilDateTime.getCurrentTime("yyyy-MM-dd") + " 23:59:59.000";
		Integer total = dakaSourceDao.findDakaRecordCount(beginDate, endDate);
		if (total > 0) {
			int count = 0;
			if("1".equals(type)){
				count = 500;
			} else {
				count = 400;
			}
			final Integer threadCount = count;
			int threadNum = total % threadCount == 0 ? total / threadCount : total / threadCount + 1;
			final String beginTime = beginDate;
			for (int i = 0; i < threadNum; i++) {
				final Integer index = i;
				Runnable thread = new Runnable() {
					@Override
					public void run() {
						syncDakaRecord(index, threadCount, beginTime, endDate, type);
					}
				};
				threadPoolTaskExecutor.execute(thread);
			}
		}
	}
	
	private void syncDakaRecord(Integer pageNum, Integer pageSize, String beginTime, String endDate, String type) {
		List<Map<String, Object>> dakaRecordList = dakaSourceDao.findDakaRecordList(pageNum * pageSize, pageSize, beginTime, endDate);
		if(dakaRecordList != null && dakaRecordList.size() > 0){
			String status = "0";
			if("2".equals(type)){
				status = "1";
			}
			List<Object[]> params = new ArrayList<Object[]>();
			for (Map<String, Object> map : dakaRecordList) {
				Object[] obj = new Object[]{
					map.get("id"), map.get("pNumber"), map.get("pName"), map.get("checkTime"), 
					map.get("checkType"), map.get("verifyCode"), map.get("snName"), "1",status
				};
				params.add(obj);
			}
			dakaSourceDao.saveDakaRecords(params);
		}
		
	}
}
