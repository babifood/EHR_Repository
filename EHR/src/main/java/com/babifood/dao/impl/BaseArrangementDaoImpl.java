package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.BaseArrangementDao;
import com.babifood.entity.BaseArrangementEntity;
import com.babifood.entity.SpecialArrangementEntity;

@Repository
public class BaseArrangementDaoImpl implements BaseArrangementDao {

	Logger log = LoggerFactory.getLogger(BaseArrangementDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Map<String, Object>> findAllBaseArrangement() {
		StringBuffer sql = new StringBuffer(); 
		sql.append("select ID as id,arrangement_name as arrangementName,");
		sql.append("CASE arrangement_type WHEN '1' THEN '大小周' WHEN '2' THEN '1.5休' WHEN '3' THEN '双休' WHEN '4' THEN '单休' END as type,");
		sql.append("arrangement_type as arrangementType,");
		sql.append("start_time as startTime,end_time as endTime,concat(start_time,concat('~',end_time)) as time,remark");
		sql.append(" from ehr_base_arrangement where isDelete = '0'");
		return jdbcTemplate.queryForList(sql.toString());
	}//(case arrangement_type WHEN 1 THEN '大小周' WHEN 2 THEN '1.5休' WHEN 3 THEN '双休' WHEN 4 THEN '单休' end)

	@Override
	public int updateBaseArrangement(BaseArrangementEntity arrangement) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("update ehr_base_arrangement");
		sql.append(" set arrangement_name = ?,arrangement_type = ?,start_time = ?,end_time = ?,remark = ?");
		sql.append(" where isDelete = '0' and ID = ?");
		return jdbcTemplate.update(sql.toString(),arrangement.getArrangementName(),arrangement.getArrangementType(),arrangement.getStartTime(),arrangement.getEndTime(),arrangement.getRemark(),arrangement.getId());
	}

	@Override
	public int addBaseArrangement(BaseArrangementEntity arrangement) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("insert into ehr_base_arrangement (arrangement_name,arrangement_type,start_time,end_time,remark)");
		sql.append(" values(?,?,?,?,?)");
		return jdbcTemplate.update(sql.toString(),arrangement.getArrangementName(),arrangement.getArrangementType(),arrangement.getStartTime(),arrangement.getEndTime(),arrangement.getRemark());
	}

	@Override
	public int removeBaseArrangement(Integer id) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("update ehr_base_arrangement set isDelete = '1' where ID = ?");
		return jdbcTemplate.update(sql.toString(),id);
	}

	@Override
	public List<Map<String, Object>> findSpecialArrangementList(String date, String arrangementId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.ID as id,a.DATE as date,a.arrangement_id as arrangementId,b.arrangement_name as arrangementName,a.is_attend as isAttend,a.start_time as startTime,a.end_time as endTime,a.remark ");
		sql.append("from ehr_special_arrangement a LEFT JOIN ehr_base_arrangement b ON a.arrangement_id = b.ID where a.arrangement_id = ? and a.isDelete = '0' and b.isDelete = '0' and a.Date like ?");
		List<Map<String, Object>> specialArrangements = null;
		try {	
			specialArrangements = jdbcTemplate.queryForList(sql.toString(),arrangementId, date + "%");
		} catch (Exception e) {
			log.error("查询特殊排版列表失败", e.getMessage());
			throw e;
		}
		return specialArrangements;
	}

	@Override
	public int addSpecialArrangement(SpecialArrangementEntity arrangement) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `ehr_special_arrangement` ");
		sql.append("(`DATE`, `arrangement_id`, `is_attend`,`start_time`, `end_time`, `remark`, `isDelete`)");
		sql.append(" VALUES (?,?,?,?,?,?,0)");
		int count = 0;
		try {
			count = jdbcTemplate.update(sql.toString(), arrangement.getDate(),arrangement.getArrangementId(),
					arrangement.getIsAttend(),arrangement.getStartTime(),arrangement.getEndTime(),arrangement.getRemark());
		} catch (Exception e) {
			log.error("保存特殊排版列表失败", e.getMessage());
			throw e;
		}
		return count;
	}

	@Override
	public int updateSpecialArrangement(SpecialArrangementEntity arrangement) {
		String sql = "update `ehr_special_arrangement` set date = ?,arrangement_id = ?, start_time = ?,end_time = ? ,is_attend = ?,remark = ? where id = ? and isDelete = '0'";
		int count = 0;
		try {
			count = jdbcTemplate.update(sql, arrangement.getDate(),arrangement.getArrangementId(),arrangement.getStartTime(),
					arrangement.getEndTime(),arrangement.getIsAttend(),arrangement.getRemark(),arrangement.getId());
		} catch (Exception e) {
			log.error("修改特殊排版列表失败", e.getMessage());
			throw e;
		}
		return count;
	}

	@Override
	public int updateSpecialArrangement(String id) {
		String sql = "update `ehr_special_arrangement` set isDelete = '1' where id = ?";
		int count = 0;
		try {
			count = jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			log.error("修改特殊排版列表失败", e.getMessage());
			throw e;
		}
		return count;
	}

	@Override
	public Map<String, Object> findSpecialArrangementById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.ID as id,a.DATE as date,a.arrangement_id as arrangementId,a.is_attend as isAttend,a.start_time as startTime,a.end_time as endTime,a.remark ");
		sql.append("from ehr_special_arrangement a where a.ID = ? and isDelete = '0'");
		Map<String, Object> arrangement = null;
		try {
			arrangement = jdbcTemplate.queryForMap(sql.toString(), id);
		} catch (Exception e) {
			log.error("根据id查询特殊排版失败", e.getMessage());
			throw e;
		}
		return arrangement;
	}

	@Override
	public List<Map<String, Object>> findArrangementByTargetId(List<String> targetIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.arrangement_id as arrangementId,(CASE a.target_type WHEN '10' THEN '按人员' ");
		sql.append(" WHEN '5' THEN '按班组' WHEN '4' THEN '按科室' WHEN '3' THEN '按部门' WHEN '2' THEN '按中心' ");
		sql.append(" WHEN '1' THEN '按公司' END) as arrangementType, b.arrangement_name as arrangementName,");
		sql.append(" CONCAT(b.start_time,' ~ ',b.end_time) as standardTime ");
		sql.append(" from ehr_arrangement_target a LEFT JOIN ehr_base_arrangement b on a.arrangement_id = b.ID ");
		sql.append(" where a.target_id in (");
		for(int i = 0 ; i < targetIds.size() ;i++){
			if(i == targetIds.size() - 1){
				sql.append("?");
			} else {
				sql.append("?,");
			}
		}
		sql.append(") ORDER BY a.target_type");
		List<Map<String, Object>> arrangementList = null;
		try {
			arrangementList = jdbcTemplate.queryForList(sql.toString(), targetIds.toArray());
		} catch (Exception e) {
			log.error("根据目标id查询排班失败", e.getMessage());
			throw e;
		}
		return arrangementList;
	}

	@Override
	public int bindArrangement(String targetId, String type, String arrangementId) {
		String sql = "INSERT INTO `ehr_arrangement_target` (`target_id`, `target_type`, `arrangement_id`) VALUES (?, ?, ?)";
		int count = 0;
		try {
			count = jdbcTemplate.update(sql, targetId,type,arrangementId);
		} catch (Exception e) {
			log.error("绑定排班失败", e.getMessage());
			throw e;
		}
		return count;
	}

	@Override
	public void deleteSettedArrangement(String targetId) {
		String sql = "delete from `ehr_arrangement_target` where `target_id` = ?";
		try {
			jdbcTemplate.update(sql, targetId);
		} catch (Exception e) {
			log.error("删除已绑定排班失败", e.getMessage());
		}
	}
	
	
	@Override
	public List<Map<String, Object>> findCurrentMonthAllSpecialArrangement(String start ,String endTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("select arrangement_id as arrangementId, is_attend as isAttend ,start_time as startTime, ");
		sql.append("end_time as endTime,DATE as date from ehr_special_arrangement where DATE between ? and ? and isDelete = '0'" );
		List<Map<String, Object>> specialArrangementList = null;
		try {
			specialArrangementList = jdbcTemplate.queryForList(sql.toString(), start , endTime);
		} catch (Exception e) {
			log.error("查询当月排班列表失败", e.getMessage());
			throw e;
		}
		return specialArrangementList;
	}

	@Override
	public List<Map<String, Object>> findSpecialArrangementOfMonth(String startDay, String endDay) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.ID as id,a.DATE as date,a.arrangement_id as arrangementId,a.is_attend as isAttend,a.start_time as startTime,a.end_time as endTime,a.remark ");
		sql.append("from ehr_special_arrangement a where (a.DATE between ? and ?) and isDelete = '0'");
		List<Map<String, Object>> specialArrangementList = null;
		try {
			specialArrangementList = jdbcTemplate.queryForList(sql.toString(), startDay , endDay);
		} catch (Exception e) {
			log.error("查询当月排班列表失败", e.getMessage());
			throw e;
		}
		return specialArrangementList;
	}

	@Override
	public Map<String, Object> findBaseArrangementById(String arrangementId) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("select ID as id,arrangement_name as arrangementName,");
		sql.append("CASE arrangement_type WHEN '1' THEN '大小周' WHEN '2' THEN '1.5休' WHEN '3' THEN '双休' WHEN '4' THEN '单休' END as type,");
		sql.append("arrangement_type as arrangementType,");
		sql.append("start_time as startTime,end_time as endTime,concat(start_time,concat('~',end_time)) as time,remark");
		sql.append(" from ehr_base_arrangement where isDelete = '0' and ID = ?");
		List<Map<String, Object>> arrangementList = null;
		Map<String, Object> arrangement = null;
		try {
			arrangementList = jdbcTemplate.queryForList(sql.toString(), arrangementId);
		} catch (Exception e) {
			log.error("根据Id查询基础排班信息失败", e.getMessage());
			throw e;
		}
		if(arrangementList != null && arrangementList.size() > 0){
			arrangement = arrangementList.get(0);
		}
		return arrangement;
	}

}
