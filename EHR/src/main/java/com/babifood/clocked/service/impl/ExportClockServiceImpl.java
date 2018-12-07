package com.babifood.clocked.service.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.clocked.service.ExportClockService;
import com.babifood.constant.OperationConstant;
import com.babifood.entity.LoginEntity;
import com.babifood.utils.ExcelUtil;
import com.cn.babifood.operation.LogManager;
@Service
public class ExportClockServiceImpl implements ExportClockService{
	public static final Logger log = Logger.getLogger(ExportClockServiceImpl.class);
	@Autowired
	LoadClockedResultServiceImpl loadClockedResultServiceImpl;
	@Override
	public Map<String, Object> exportSumClockedResult(OutputStream ouputStream,String searchKey,String searchVal,String myYear,String myMonth,String comp,String organ,String dept) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> row1Name = getSumClockedRowName();
		String[] sort = new String[]{"period","WorkNum","UserName","Company","Organ","Dept","Office","GroupName","Post",
				"CheckingType","PaiBanType","standardWorkLength","actualWorkLength","chiDao","zaoTui","kuangGongCiShu",
				"kuangGong","Queqin","Qingjia","nianJia","tiaoXiu","shiJia","bingJia","peixunJia","hunJia","chanJia",
				"PeiChanJia","SangJia","Yidong","Jiaban","Chuchai","Canbu"};
		List<Map<String, Object>> performanceList = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
			performanceList = loadClockedResultServiceImpl.loadSumClockedResultData(searchKey, searchVal, myYear, myMonth,comp,organ,dept);
			LogManager.putContectOfLogInfo("导出考勤结果汇总表");
			
			ExcelUtil.exportExcel("考勤结果汇总表", row1Name, performanceList, ouputStream, sort);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "导出excel失败");
			log.error("导出考勤结果汇总表失败",e);
			LogManager.putContectOfLogInfo("导出考勤结果汇总表失败，错误信息：" + e.getMessage());
		}
		return result;
	}
	/**
	 * excel首行
	 * @return
	 */
	private Map<String, String> getSumClockedRowName() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("period", "日期区间");
		row1Name.put("WorkNum", "工号");
		row1Name.put("UserName", "姓名");
		row1Name.put("Company", "公司名称");
		row1Name.put("Organ", "中心机构名称");
		row1Name.put("Dept", "部门名称");
		row1Name.put("Office", "科室名称");
		row1Name.put("GroupName", "班组名称");
		row1Name.put("Post", "岗位名称");
		row1Name.put("CheckingType", "考勤方式");
		row1Name.put("PaiBanType", "排班类型");
		row1Name.put("standardWorkLength", "标准工作时长");
		row1Name.put("actualWorkLength", "工作时长");
		row1Name.put("chiDao", "迟到");
		row1Name.put("zaoTui", "早退");
		row1Name.put("kuangGongCiShu", "旷工次数");
		row1Name.put("kuangGong", "旷工时长");
		row1Name.put("Queqin", "缺勤时长");
		row1Name.put("Qingjia", "请假时长");
		row1Name.put("nianJia", "年假");
		row1Name.put("tiaoXiu", "调休");
		row1Name.put("shiJia", "事假");
		row1Name.put("bingJia", "病假");
		row1Name.put("peixunJia", "培训假");
		row1Name.put("hunJia", "婚假");
		row1Name.put("chanJia", "产假");
		row1Name.put("PeiChanJia", "陪产假");
		row1Name.put("SangJia", "丧假");
		row1Name.put("Yidong", "异动时长");
		row1Name.put("Jiaban", "加班时长");
		row1Name.put("Chuchai", "出差时长");
		row1Name.put("Canbu", "餐补个数");
		return row1Name;
	}
	@Override
	public Map<String, Object> exportDetailsClockedResult(OutputStream ouputStream, Integer year, Integer month,
			String workNum, String periodEndDate) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> row1Name = getDetailsClockedRowName();
		String[] sort = new String[]{"Year","Month","WorkNum","UserName","Company","Organ","Dept",
				"Office","GroupName","Post","CheckingType","PaiBanType","checkingDate","Week",
				"beginTime","endTime","standardWorkLength","checkingBeginTime","checkingEndTime",
				"actualWorkLength","chiDao","zaoTui","kuangGongCiShu","kuangGong","Queqin","Qingjia",
				"nianJia","tiaoXiu","shiJia","bingJia","peixunJia","SangJia","Yidong","Jiaban","Chuchai","Canbu"};
		List<Map<String, Object>> performanceList = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
			performanceList = loadClockedResultServiceImpl.loadClockedResultData(year, month, workNum, periodEndDate);
			LogManager.putContectOfLogInfo("导出考勤结果个人明细表");
			
			ExcelUtil.exportExcel("考勤结果个人明细表", row1Name, performanceList, ouputStream, sort);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "导出excel失败");
			log.error("导出考勤结果个人明细表失败",e);
			LogManager.putContectOfLogInfo("导出考勤结果个人明细表失败，错误信息：" + e.getMessage());
		}
		return result;
	}
	/**
	 * excel首行
	 * @return
	 */
	private Map<String, String> getDetailsClockedRowName() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("Year", "年度");
		row1Name.put("Month", "月份");
		row1Name.put("WorkNum", "工号");
		row1Name.put("UserName", "姓名");
		row1Name.put("Company", "公司名称");
		row1Name.put("Organ", "中心机构名称");
		row1Name.put("Dept", "部门名称");
		row1Name.put("Office", "科室名称");
		row1Name.put("GroupName", "班组名称");
		row1Name.put("Post", "岗位名称");
		row1Name.put("CheckingType", "考勤方式");
		row1Name.put("PaiBanType", "排班类型");
		row1Name.put("checkingDate", "日期");
		row1Name.put("Week", "星期");
		row1Name.put("beginTime", "标准上班时间");
		row1Name.put("endTime", "标准下班时间");
		row1Name.put("standardWorkLength", "标准工作时长");
		row1Name.put("checkingBeginTime", "打卡起始时间");
		row1Name.put("checkingEndTime", "打卡结束时间");
		row1Name.put("actualWorkLength", "工作时长");
		row1Name.put("chiDao", "迟到");
		row1Name.put("zaoTui", "早退");
		row1Name.put("kuangGongCiShu", "旷工次数");
		row1Name.put("kuangGong", "旷工时长");
		row1Name.put("Queqin", "缺勤小时数");
		row1Name.put("Qingjia", "请假小时数");
		row1Name.put("nianJia", "年假");
		row1Name.put("tiaoXiu", "调休");
		row1Name.put("shiJia", "事假");
		row1Name.put("bingJia", "病假");
		row1Name.put("peixunJia", "培训假");
		row1Name.put("hunJia", "婚假");
		row1Name.put("chanJia", "产假");
		row1Name.put("PeiChanJia", "陪产假");
		row1Name.put("SangJia", "丧假");
		row1Name.put("Yidong", "异动小时数");
		row1Name.put("Jiaban", "加班小时数");
		row1Name.put("Chuchai", "出差小时数");
		row1Name.put("Canbu", "餐补个数");
		return row1Name;
	}
}
