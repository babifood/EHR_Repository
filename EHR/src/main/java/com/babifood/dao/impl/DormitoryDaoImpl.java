package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.DormitoryDao;
import com.babifood.entity.DormitoryEntity;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;

@Repository
public class DormitoryDaoImpl implements DormitoryDao {
	
	Logger log = LoggerFactory.getLogger(DormitoryDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addDormitory(DormitoryEntity dormitory) {
		String sql = "INSERT INTO `ehr_dormitory` (`floor`, `room_no`, `Bed_No`, `sex`, `remark`, `is_delete`) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			jdbcTemplate.update(sql, dormitory.getFloor(), dormitory.getRoomNo(), dormitory.getBedNo(),
					dormitory.getSex(), dormitory.getRemark(), "0");
		} catch (Exception e) {
			log.error("新增宿舍床位信息失败",e.getMessage());
			throw e;
		}
	}

	@Override
	public void updateDormitory(Map<String, Object> dormitory) {
		String sql = "UPDATE `ehr_dormitory` SET `floor`=?, `room_no`=?, `Bed_No`=?, `sex`=?, `remark`=?, `is_delete`=? WHERE `ID`=?";
		try {
			jdbcTemplate.update(sql, dormitory.get("floor"), dormitory.get("roomNo"), dormitory.get("bedNo"),
					dormitory.get("sex"), dormitory.get("remark"), dormitory.get("isDelete"), dormitory.get("id"));
		} catch (Exception e) {
			log.error("更新宿舍床位信息失败",e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Object> findDormitoryById(String id) {
//		String sql = "SELECT id, floor, room_no as roomNo, Bed_No as bedNo, sex, remark,is_delete as isDelete from ehr_dormitory WHERE ID = ? and is_delete = '0'";
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id as id, a.floor as floor, a.room_no as roomNo, a.Bed_No as bedNo,");
		sql.append("a.sex as sex, a.remark as remark,");
		sql.append("b.p_number as pNumber from ehr_dormitory a ");
		sql.append("LEFT JOIN (SELECT * FROM ehr_dormitory_stay where is_delete = '0') b on a.id = b.dormitory_id");
		sql.append(" where a.is_delete = '0' and a.id = ?");
		Map<String, Object> dormitory = null;
		List<Map<String, Object>> dormitorys = null;
		try {
			dormitorys = jdbcTemplate.queryForList(sql.toString(), id);
		} catch (Exception e) {
			log.error("根据ID查询床位信息失败",e.getMessage());
			throw e;
		}
		if(dormitorys != null && dormitorys.size() > 0){
			dormitory = dormitorys.get(0);
		}
		return dormitory;
	}
	
	@Override
	public List<Map<String, Object>> getUnStayDormitory(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id as id, a.floor as floor, a.room_no as roomNo, a.Bed_No as bedNo,");
		sql.append("a.sex as sex, a.remark as remark,");
		sql.append("IF(b.p_number IS NOT NULL,'已入住', '未入住') as stay  from ehr_dormitory a ");
		sql.append("LEFT JOIN (SELECT * FROM ehr_dormitory_stay where is_delete = '0') b on a.id = b.dormitory_id");
		sql.append(" where a.is_delete = '0'");
		if(!UtilString.isEmpty(params.get("floor") + "")){
			sql.append(" AND a.floor = " + params.get("floor"));
		}
		if(!UtilString.isEmpty(params.get("sex") + "")){
			sql.append(" AND a.sex = " + params.get("sex"));
		}
		if(!UtilString.isEmpty(params.get("stay") + "") && "1".equals(params.get("stay") + "")){
			sql.append(" AND b.stay_time is not null");
		}
		if(!UtilString.isEmpty(params.get("stay") + "") && "0".equals(params.get("stay") + "")){
			sql.append(" AND b.stay_time is null");
		}
		if(!UtilString.isEmpty(params.get("roomNo") + "")){
			sql.append(" AND a.room_no like '%" + params.get("roomNo") + "%'");
		}
		sql.append(" Limit ?,?");
		List<Map<String, Object>> dormitorys = null;
		try {
			dormitorys = jdbcTemplate.queryForList(sql.toString(),params.get("start") ,params.get("pageSize"));
		} catch (Exception e) {
			log.error("查询未入住宿舍信息失败",e.getMessage());
			throw e;
		}
		return dormitorys;
	}

	@Override
	public List<Map<String, Object>> getStayDormitory(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id as id, a.floor as floor, a.room_no as roomNo, a.Bed_No as bedNo, a.remark as remark, ");
		sql.append("a.sex as sex,IF(b.p_number IS NOT NULL,'已入住', '未入住') as stay, ");
		sql.append("b.stay_time as stayTime, c.p_number as pNumber, c.p_name as pName ");
		sql.append("from ehr_dormitory a ");
		sql.append("LEFT JOIN (SELECT * FROM ehr_dormitory_stay where is_delete = '0') b on a.id = b.dormitory_id ");
		sql.append("LEFT JOIN ehr_person_basic_info c on b.p_number = c.p_number");
		sql.append(" where a.is_delete = '0' and b.p_number is not null");
		if(!UtilString.isEmpty(params.get("floor") + "")){
			sql.append(" AND a.floor = " + params.get("floor"));
		}
		if(!UtilString.isEmpty(params.get("sex") + "")){
			sql.append(" AND a.sex = " + params.get("sex"));
		}
		if(!UtilString.isEmpty(params.get("pNumber") + "")){
			sql.append(" AND b.p_number like '%" + params.get("pNumber") + "%'");
		}
		if(!UtilString.isEmpty(params.get("pName") + "")){
			sql.append(" AND c.p_name like '%" + params.get("pName") + "%'");
		}
		if(!UtilString.isEmpty(params.get("roomNo") + "")){
			sql.append(" AND a.room_no like '%" + params.get("roomNo") + "%'");
		}
		sql.append(" Limit ?,?");
		List<Map<String, Object>> dormitorys = null;
		try {
			dormitorys = jdbcTemplate.queryForList(sql.toString(),params.get("start") ,params.get("pageSize"));
		} catch (Exception e) {
			log.error("查询已入住宿舍信息失败",e.getMessage());
			throw e;
		}
		return dormitorys;
	}

	@Override
	public Map<String, Object> findCheakingDormitory(String dormitoryId, String pnumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, p_number as pNumber, dormitory_id as dormitoryId, stay_time as stayTime, out_time as outTime,");
		sql.append("create_time as createTime, remark, is_delete as isDelete from ehr_dormitory_stay");
		sql.append(" where p_number = ? and dormitory_id = ? and is_delete = '0'");
		Map<String, Object> dormitory = null;
		List<Map<String, Object>> dormitorys = null;
		try {
			dormitorys = jdbcTemplate.queryForList(sql.toString(), pnumber, dormitoryId);
		} catch (Exception e) {
			log.error("根据ID查询床位信息失败",e.getMessage());
			throw e;
		}
		if(dormitorys != null && dormitorys.size() > 0){
			dormitory = dormitorys.get(0);
		}
		return dormitory;
	}

	@Override
	public void insertCheakingDormitory(String dormitoryId, String pnumber,String stayTime) {
		String sql = "INSERT INTO `ehr_dormitory_stay` (`p_number`, `stay_time`, `out_time`, `dormitory_id`, "
				+ "`create_time`, `is_delete`) VALUES ( ?, ?, ?, ?, ?, ?)";
		try {
			jdbcTemplate.update(sql, pnumber, stayTime, "", dormitoryId, UtilDateTime.getCurrentTime("yyyy-MM-dd HH:mm:ss"), "0");
		} catch (Exception e) {
			log.error("新增入住信息失败",e.getMessage());
			throw e;
		}
	}

	@Override
	public int cheakoutDormitory(String dormitoryId, String pnumber) {
		String sql = "update `ehr_dormitory_stay` set `is_delete` = '1' where p_number = ? and dormitory_id = ?";
		int count = 0;
		try {
			count = jdbcTemplate.update(sql, pnumber, dormitoryId);
		} catch (Exception e) {
			log.error("删除入住信息失败",e.getMessage());
			throw e;
		}
		return count;
	}

	@Override
	public Map<String, Object> findDormitoryInfo(String floor, String roomNo, String bedNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, floor, room_no as roomNo, Bed_No as bedNo, sex, remark,is_delete as isDelete ");
		sql.append("from ehr_dormitory WHERE floor = ? and room_no = ? and Bed_No = ? and is_delete = '0'");
		Map<String, Object> dormitory = null;
		List<Map<String, Object>> dormitorys = null;
		try {
			dormitorys = jdbcTemplate.queryForList(sql.toString(), floor, roomNo, bedNo);
		} catch (Exception e) {
			log.error("询床位信息失败",e.getMessage());
			throw e;
		}
		if(dormitorys != null && dormitorys.size() > 0){
			dormitory = dormitorys.get(0);
		}
		return dormitory;
		
	}

	@Override
	public int getCountOfUnStayDormitory(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ehr_dormitory a ");
		sql.append("LEFT JOIN (SELECT * FROM ehr_dormitory_stay where is_delete = '0') b on a.id = b.dormitory_id");
		sql.append(" where a.is_delete = '0'");
		if(!UtilString.isEmpty(params.get("floor") + "")){
			sql.append(" AND a.floor = " + params.get("floor"));
		}
		if(!UtilString.isEmpty(params.get("sex") + "")){
			sql.append(" AND a.sex = " + params.get("sex"));
		}
		if(!UtilString.isEmpty(params.get("stay") + "") && "1".equals(params.get("stay") + "")){
			sql.append(" AND b.p_number is not null");
		}
		if(!UtilString.isEmpty(params.get("stay") + "") && "0".equals(params.get("stay") + "")){
			sql.append(" AND b.stay_time is null");
		}
		if(!UtilString.isEmpty(params.get("roomNo") + "")){
			sql.append(" AND a.room_no like '%" + params.get("roomNo") + "%'");
		}
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询未入住宿舍信息失败",e.getMessage());
			throw e;
		}
		return count;
	}

	@Override
	public int getCountOfStayDormitory(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) ");
		sql.append("from ehr_dormitory a ");
		sql.append("LEFT JOIN (SELECT * FROM ehr_dormitory_stay where is_delete = '0') b on a.id = b.dormitory_id ");
		sql.append("LEFT JOIN ehr_person_basic_info c on b.p_number = c.p_number");
		sql.append(" where a.is_delete = '0' and b.p_number is not null");
		if(!UtilString.isEmpty(params.get("floor") + "")){
			sql.append(" AND a.floor = " + params.get("floor"));
		}
		if(!UtilString.isEmpty(params.get("sex") + "")){
			sql.append(" AND a.sex = " + params.get("sex"));
		}
		if(!UtilString.isEmpty(params.get("pNumber") + "")){
			sql.append(" AND b.p_number like '%" + params.get("pNumber") + "%'");
		}
		if(!UtilString.isEmpty(params.get("pName") + "")){
			sql.append(" AND c.p_name like '%" + params.get("pName") + "%'");
		}
		if(!UtilString.isEmpty(params.get("roomNo") + "")){
			sql.append(" AND a.room_no like '%" + params.get("roomNo") + "%'");
		}
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询已入住宿舍信息失败",e.getMessage());
			throw e;
		}
		return count;
	}

}
