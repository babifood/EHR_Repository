package com.babifood.clocked.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.clocked.dao.PushClockedDataDao;
import com.babifood.clocked.entrty.PushOaDataEntrty;
import com.babifood.clocked.service.PushClockedDataService;
import com.babifood.clocked.util.MapToObjectUtil;
import com.babifood.constant.OperationConstant;
import com.babifood.entity.LoginEntity;
import com.babifood.utils.AuthorityServiceTest;
import com.babifood.utils.MessageTempleteManager;
import com.cn.babifood.operation.LogManager;
import com.seeyon.client.CTPRestClient;
@Service
public class PushClockedDataServiceImpl implements PushClockedDataService {
	public static final Logger log = Logger.getLogger(PushClockedDataServiceImpl.class);
	@Autowired
	PushClockedDataDao pushClockedDataDao;
	@Override
	public String pushDataToOA(int year, int month) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATING_LOG_TYPE_PUSH);
		List<Map<String, Object>> list =null;
		String status = "-1";
		if (AuthorityServiceTest.getInstence().authenticate()) {
			try {
				CTPRestClient client = AuthorityServiceTest.getInstence().getClient();
				Map<String, String> res = new HashMap<String, String>();
				list = pushClockedDataDao.loadClockingInData(year, month);
				List<PushOaDataEntrty> pushDataList = MapToObjectUtil.MapToPushOaDataEntrty(list);
				//推送数据之前先删除OA端当前月份的数据
				List<PushOaDataEntrty> newList = MapToObjectUtil.CopyList(pushDataList);
				pushClockedDataDao.deleteOAClockingInData(newList, year, month);
				
				Map<String,Object> map =new HashMap<String,Object>();
				map.put("checkDataList", pushDataList);
				String dataXml = MessageTempleteManager.process("pushClockedData.ftl",map);
				res.put("loginName", "lizhiqiang");
				res.put("dataXml", dataXml);
				status = client.post("form/import/KQRESULT",res,String.class);
				LogManager.putContectOfLogInfo("查询考勤数据推送OA");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LogManager.putContectOfLogInfo(e.getMessage());
				log.error("loadClockedResultData:"+e.getMessage());
			}
		}
		return status;
	}
	
}
