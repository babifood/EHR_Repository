package com.babifood.service.scheduling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

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

	public void getDakaSource(String type) throws Exception {
		// 获取最后打卡时间
		String beginDate = dakaSourceDao.findLastDay();
		if (UtilString.isEmpty(beginDate)) {
			beginDate = "2018-09-01 00:00:00";
		}
		Date begin = UtilDateTime.getDate(beginDate, "yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		if("1".equals(type)){//自动任务
			calendar.add(Calendar.DAY_OF_YEAR, -7);
		} else if ("2".equals(type)) {//手动自定义
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		String year = calendar.get(Calendar.YEAR)+"";
		String month = calendar.get(Calendar.MONTH) > 8 ? (calendar.get(Calendar.MONTH) + 1) + "":("0"+(calendar.get(Calendar.MONTH + 1)));
		String day = calendar.get(Calendar.DAY_OF_MONTH) > 9 ? calendar.get(Calendar.DAY_OF_MONTH) + "": "0"+calendar.get(Calendar.DAY_OF_MONTH);
		beginDate = year + "-" + month + "-" + day + " 00:00:00";
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
			List<Future<String>> list = new ArrayList<>();
			for (int i = 0; i < threadNum; i++) {
				final Integer index = i;
				Callable<String> c1 = new Callable<String>() {
					@Override
					public String call() throws Exception {
						syncDakaRecord(index, threadCount, beginTime, endDate);
						return index + "";
					}
				};
				Future<String> f1 = threadPoolTaskExecutor.submit(c1);
				list.add(f1);
//				Runnable thread = new Runnable() {
//					@Override
//					public void run() {
//						syncDakaRecord(index, threadCount, beginTime, endDate);
//					}
//				};
//				threadPoolTaskExecutor.execute(thread);
			}
			for(Future<String> future : list){
				System.out.println(future.get());
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
