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

	public void getDakaSource() {
		// 获取最后打卡时间
		String beginDate = dakaSourceDao.findLastDay();
		if (UtilString.isEmpty(beginDate)) {
			beginDate = "2018-07-01 00:00:00.000";
		}
		// 查询未同步打卡数量
		final String endDate = UtilDateTime.getCurrentTime("yyyy-MM-dd") + " 00:00:00.000";
		Integer total = dakaSourceDao.findDakaRecordCount(beginDate, endDate);
		if (total > 0) {
			final Integer threadCount = 500;
			int threadNum = total % threadCount == 0 ? total / threadCount : total / threadCount + 1;
			final String beginTime = beginDate;
			System.out.println("==========================total:" + total);
			for (int i = 0; i < threadNum; i++) {
				final Integer index = i;
				Runnable thread = new Runnable() {
					@Override
					public void run() {
						syncDakaRecord(index, threadCount, beginTime, endDate);
					}
				};
				threadPoolTaskExecutor.execute(thread);
			}
		}
	}
	
	private void syncDakaRecord(Integer pageNum, Integer pageSize, String beginTime, String endDate) {
		List<Map<String, Object>> dakaRecordList = dakaSourceDao.findDakaRecordList(pageNum * pageSize, pageSize, beginTime, endDate);
		if(dakaRecordList != null && dakaRecordList.size() > 0){
			List<Object[]> params = new ArrayList<Object[]>();
			for (Map<String, Object> map : dakaRecordList) {
				Object[] obj = new Object[]{
					map.get("id"), map.get("pNumber"), map.get("pName"), map.get("checkTime"), 
					map.get("checkType"), map.get("verifyCode"), map.get("snName")
				};
				params.add(obj);
			}
			dakaSourceDao.saveDakaRecords(params);
		}
		
	}
}
