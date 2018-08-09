package com.babifood.clocked.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.clocked.dao.MoveDaKaDao;
import com.babifood.clocked.entrty.MobileDaKaLog;
import com.babifood.utils.CustomerContextHolder;
@Repository
public class MoveDaKaDaoImpl implements MoveDaKaDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public List<MobileDaKaLog> loadMobileDaKaDate(int year, int month) throws Exception {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_OA);
		SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// TODO Auto-generated method stub
		String s = String.valueOf(year);
		if (month < 10) {
			s = s + "-0" + month;
		} else {
			s = s + "-" + month;
		}
		List<MobileDaKaLog> mobileList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("a.clocktime, a.clockdate, a.locationtypeid,m.code ");
		sql.append("from a8xmobile_clocked_record_final a ");
		sql.append("inner join org_member m on a.memberid = m.id ");
		sql.append("where to_char(clocktime, 'yyyy-mm')=? ");
		sql.append("and m.code='100024' ");
		sql.append("order by m.code,a.clocktime");
		List<Map<String,Object>> list = jdbctemplate.queryForList(sql.toString(),s);
		if(list.size()>0){
			mobileList = new ArrayList<MobileDaKaLog>();
			for (Map<String, Object> map : list) {
				MobileDaKaLog m = new MobileDaKaLog();
				m.setWorkNum(map.get("code")==null?"":map.get("code").toString());
				m.setClockTime(map.get("clocktime")==null?"":dftime.format(map.get("clocktime")));
				m.setClockDate(map.get("clockdate")==null?"":map.get("clockdate").toString());
				m.setLocationTypeId(map.get("locationtypeid")==null?"":map.get("locationtypeid").toString());
				mobileList.add(m);
			}
		}
		return mobileList;
	}

}
