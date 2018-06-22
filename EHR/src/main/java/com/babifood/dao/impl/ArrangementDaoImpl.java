package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.ArrangementDao;
import com.babifood.entity.ArrangementEntity;

@Repository
public class ArrangementDaoImpl implements ArrangementDao {
	
	Logger log = LoggerFactory.getLogger(ArrangementDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int queryArrangementsCount() {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ehr_arrangement");
		return jdbcTemplate.queryForInt(sql.toString());
	}

	@Override
	public List<Map<String, Object>> findListArrangements(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ID as id,DATE as date,concat(start_time,concat('~',end_time)) as time,attendance as attendance");
		sql.append(",IF( is_attend = 1 , '考勤' , '不考勤' ) as isAttend ,creator_name as creatorName,base_time_id as baseTimeId,target_name as targetName ");
		sql.append(",case target_type WHEN '1' THEN '集团' WHEN '2' THEN '中心' WHEN '3' THEN '部门' WHEN '4' THEN '科室' WHEN '5' THEN '组' WHEN '10' THEN '员工' END as targetType");
		sql.append(" from ehr_arrangement");
//		sql.append(" where ");
		sql.append(" limit ? ,?");
		int index = 0;
		Object[] object = new Object[map.keySet().size()];
		Integer pageNum = (Integer) map.get("pageNum");
		Integer pageSize = (Integer) map.get("pageSize");
		object[index] = (pageNum-1) * pageSize;
		index++;
		object[index] = pageNum * pageSize;
		return jdbcTemplate.queryForList(sql.toString(), object);
	}

	@Override
	public int removeArrangement(Integer id) {
		String sql = "delete from ehr_arrangement where id = ?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int saveArrangement(ArrangementEntity arrangement) {
		String sql = "insert into ehr_arrangement (DATE,base_time_id,start_time,end_time,attendance,is_attend,target_type,target_id,target_name,creator_id,creator_name,remark) values (?,?,?,?,?,?,?,?,?,?,?)";
		Object[] object = getParam(arrangement);
		return jdbcTemplate.update(sql, object);
	}

	private Object[] getParam(ArrangementEntity arrangement){
		Object[] object = new Object[11];
		int index = -1;
		if(arrangement.getDate() != null){
			index++;
			object[index] = arrangement.getDate();
		}
		if(arrangement.getBaseTimeId() != null){
			index++;
			object[index] = arrangement.getDate();
		}
		if(arrangement.getStartTime() != null){
			index++;
			object[index] = arrangement.getStartTime();
		}
		if(arrangement.getEndTime() != null){
			index++;
			object[index] = arrangement.getEndTime();
		}
		if(arrangement.getAttendance() != null){
			index++;
			object[index] = arrangement.getAttendance();
		}
		if(arrangement.getIsAttend() != null){
			index++;
			object[index] = arrangement.getIsAttend();
		}
		if(arrangement.getTargetType() != null){
			index++;
			object[index] = arrangement.getTargetType();
		}
		if(arrangement.getTargetId() != null){
			index++;
			object[index] = arrangement.getTargetId();
		}
		if(arrangement.getCreatorId() != null){
			index++;
			object[index] = arrangement.getCreatorId();
		}
		if(arrangement.getCreatorName() != null){
			index++;
			object[index] = arrangement.getCreatorName();
		}
		if(arrangement.getRemark() != null){
			index++;
			object[index] = arrangement.getRemark();
		}
		return object;
	}

	@Override
	public int[] saveArrangementList(List<Object[]> arrangementList) {
		String sql = "insert into ehr_arrangement (DATE,base_time_id,start_time,end_time,attendance,is_attend,target_type,target_id,target_name,creator_id,creator_name,remark) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] counts = null;
		try {
			counts = jdbcTemplate.batchUpdate(sql, arrangementList);
		} catch (Exception e) {
			log.error("插入排班信息失败",e.getMessage());
		}
		return counts;
	}
	
}
