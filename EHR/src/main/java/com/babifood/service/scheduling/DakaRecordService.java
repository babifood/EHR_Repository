package com.babifood.service.scheduling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.scheduling.DakaRecordDao;
import com.babifood.dao.scheduling.DakaSourceDao;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;

@Service
public class DakaRecordService {

	@Autowired
	private DakaRecordDao dakaRecordDao;
	
	@Autowired
	private DakaSourceDao dakaSourceDao;

	public void checkDakaRecord(String type) throws Exception {
		// 打卡记录最后日期
		Calendar calendar = Calendar.getInstance();
		String lastDay = dakaRecordDao.findLastDay();
		if (UtilString.isEmpty(lastDay)) {
			calendar.set(Calendar.YEAR, 2018);
			calendar.set(Calendar.MONTH, 8);//9月
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		} else {
			calendar.setTime(UtilDateTime.getDate(lastDay, "yyyy-MM-dd"));
		}
		if("1".equals(type)){
			calendar.add(Calendar.DAY_OF_YEAR, -7);
		} else if ("2".equals(type)) {
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		String beginTime = UtilDateTime.dateToString(calendar.getTime(), "yyyy-MM-dd 00:00:00");
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		String endTime = UtilDateTime.dateToString(calendar.getTime(), "yyyy-MM-dd 00:00:00");
		while (true) {
			List<Map<String, Object>> dakaRecord = dakaSourceDao.findDayDakaSources(beginTime, endTime);
			if(dakaRecord == null || dakaRecord.size() <= 0){
				break;
			}
			calculateDakaRecord(dakaRecord, beginTime.substring(0, 10));
			beginTime = endTime;
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			endTime = UtilDateTime.dateToString(calendar.getTime(), "yyyy-MM-dd 00:00:00");
		}
	}

	private void calculateDakaRecord(List<Map<String, Object>> dakaRecord, String day) {
		Map<String, List<Map<String, Object>>> dakaRecordMap = getDakaRecordMap(dakaRecord);
		if(dakaRecordMap != null && dakaRecordMap.size() > 0){
			List<Object[]> dakaParams = new ArrayList<Object[]>();
			for(List<Map<String, Object>> dakaList : dakaRecordMap.values()){
				Object[] obj = new Object[5];
				obj[0] = dakaList.get(0).get("pNumber");
				obj[1] = dakaList.get(0).get("pName");
				obj[2] = day;
				if(dakaList.size() == 1){
					String checkTime = dakaList.get(0).get("checkTime") + "";
					String time = checkTime.split(" ")[1].substring(0, 5);
					if(UtilDateTime.getHours(checkTime, day + " 12:00:00")){
						obj[3] = time;
					} else {
						obj[4] = time;
					}
				} else {
					sortList(dakaList);
					String start = dakaList.get(0).get("checkTime") + "";
					String end = dakaList.get(dakaList.size() - 1).get("checkTime") + "";
					obj[3] = start.split(" ")[1].substring(0, 5);
					obj[4] = end.split(" ")[1].substring(0, 5);
				}
				dakaParams.add(obj);
			}
			dakaRecordDao.saveDakaRecord(dakaParams);
		}
	}
	
	private void sortList(List<Map<String, Object>> dakaList) {
		Collections.sort(dakaList,new Comparator<Map<String, Object>>(){
            @Override
            public int compare(Map<String, Object> map1, Map<String, Object> map2) {
            	if(UtilDateTime.getHours(map1.get("checkTime") + "", map2.get("checkTime") + "")){
            		return -1;
            	} else {
					return 1;
				}
            }
         });
	}

	/**
	 * 整理每个员工的打卡数据
	 * @param dakaRecord
	 * @return
	 */
	private Map<String, List<Map<String, Object>>> getDakaRecordMap(List<Map<String, Object>> dakaRecord) {
		Map<String, List<Map<String, Object>>> dakaRecordMap = new HashMap<String, List<Map<String, Object>>>();
		for(Map<String, Object> map:dakaRecord){
			String pNumber = map.get("pNumber")+"";
			List<Map<String, Object>> list = dakaRecordMap.get(pNumber);
			if(list == null){
				list = new ArrayList<Map<String, Object>>();
			}
			list.add(map);
			dakaRecordMap.put(pNumber, list);
		}
		return dakaRecordMap;
	}

}
