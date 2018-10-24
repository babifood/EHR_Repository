package com.babifood.clocked.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.clocked.dao.ColeckEventResultDao;
import com.babifood.clocked.entrty.ClockedBizData;
import com.babifood.utils.CustomerContextHolder;
import com.babifood.utils.UtilDateTime;
@Repository
public class ColeckEventResultDaoImpl implements ColeckEventResultDao {
	public static final Logger log = Logger.getLogger(ColeckEventResultDaoImpl.class);
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public List<ClockedBizData> loadColeckEventResultData(int year, int month) throws Exception {
		// TODO Auto-generated method stub
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_OA);
		SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// TODO Auto-generated method stub
		List<ClockedBizData> clockedBizList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("year,month,m.code as worknum,createtime,billdate,billnum,billtype,timelength,begintime,endtime,validflag,bizflag1 ");
		sql.append("from a8xclockedbizdata a inner join org_member m on a.memberid=m.id ");
		sql.append("where to_char(endtime,'yyyy-mm-dd') >=? and to_char(begintime,'yyyy-mm-dd')<=?");
		Object[] params=new Object[2];
		params[0]=df.format(UtilDateTime.getMonthStartSqlDate(year,month));
		params[1]=df.format(UtilDateTime.getMonthEndSqlDate(year,month));
		List<Map<String,Object>> list=null;
		list = jdbctemplate.queryForList(sql.toString(), params);
		if(list.size()>0){
			clockedBizList = new ArrayList<ClockedBizData>();
			for (Map<String, Object> map : list) {
				ClockedBizData c = new ClockedBizData();
				c.setYear(map.get("year")==null?0:Integer.parseInt(map.get("year").toString()));
				c.setMonth(map.get("month")==null?0:Integer.parseInt(map.get("month").toString()));
				c.setWorkNum(map.get("worknum")==null?"":map.get("worknum").toString());
				c.setCreateTime(map.get("createtime")==null?null:dftime.parse(map.get("createtime").toString()));
				c.setBillDate(map.get("billdate")==null?null:dftime.parse(map.get("billdate").toString()));
				c.setBillNum(map.get("billnum")==null?"":map.get("billnum").toString());
				c.setBillType(map.get("billtype")==null?"":map.get("billtype").toString());
				c.setTimeLength(map.get("timeLength")==null?0d:Double.parseDouble(map.get("timeLength").toString()));
				c.setBeginTime(map.get("begintime")==null?null:dftime.parse(map.get("begintime").toString()));
				c.setEndTime(map.get("endtime")==null?null:dftime.parse(map.get("endtime").toString()));
				c.setValidFlag(map.get("validflag")==null?"":map.get("validflag").toString());
				c.setBizFlag1(map.get("bizflag1")==null?"":map.get("bizflag1").toString());
				clockedBizList.add(c);
			}
		}
		return clockedBizList;
	}

}
