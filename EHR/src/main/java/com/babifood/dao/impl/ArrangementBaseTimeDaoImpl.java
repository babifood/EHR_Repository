package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.ArrangementBaseTimeDao;
import com.babifood.entity.ArrangementBaseTimeEntity;

@Repository
public class ArrangementBaseTimeDaoImpl implements ArrangementBaseTimeDao {

	Logger log = LoggerFactory.getLogger(ArrangementBaseTimeDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Map<String, Object>> findAllArrangementBaseTimes() {
		StringBuffer sql = new StringBuffer(); 
		sql.append("select ID as id,arrangement_name as arrangementName,start_time as startTime,end_time as endTime,concat(start_time,concat('~',end_time)) as time,remark");
		sql.append(" from ehr_arrangement_base_time");
		return jdbcTemplate.queryForList(sql.toString());
	}

	@Override
	public int addArrangementBaseTime(ArrangementBaseTimeEntity arrangement) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("insert into ehr_arrangement_base_time (arrangement_name,start_time,end_time,remark)");
		sql.append(" values(?,?,?,?)");
		return jdbcTemplate.update(sql.toString(),arrangement.getArrangementName(),arrangement.getStartTime(),arrangement.getEndTime(),arrangement.getRemark());
	}
	
	@Override
	public Map<String, Object> findArrangementBaseTimeById(Integer id) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("select ID as id,arrangement_name as arrangementName,start_time as startTime,end_time as endTime,concat(start_time,concat('~',end_time)) as time,remark");
		sql.append(" from ehr_arrangement_base_time");
		sql.append(" where id = ?");
		Map<String, Object> baseTime = null;
		try {
			baseTime = jdbcTemplate.queryForMap(sql.toString(), id);
		} catch (Exception e) {
			log.error("根据id查询基础作息时间失败",e.getMessage());
		}
		return baseTime;
	}

	@Override
	public int updateArrangementBaseTime(ArrangementBaseTimeEntity arrangement) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("update ehr_arrangement_base_time");
		sql.append(" set arrangement_name = ?,start_time = ?,end_time = ?,remark = ?");
		sql.append(" where ID = ?");
		return jdbcTemplate.update(sql.toString(),arrangement.getArrangementName(),arrangement.getStartTime(),arrangement.getEndTime(),arrangement.getRemark(),arrangement.getId());
	}

	@Override
	public int removeArrangementBaseTime(Integer id) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("delete from ehr_arrangement_base_time where ID = ?");
		return jdbcTemplate.update(sql.toString(),id);
	}

	@Override
	public List<Map<String, Object>> findPageBaseTimes(int start, int pageSize) {
		String sql = "SELECT * from ehr_arrangement_base_time limit ?,?";
		return jdbcTemplate.queryForList(sql,start,pageSize);
	}

}
