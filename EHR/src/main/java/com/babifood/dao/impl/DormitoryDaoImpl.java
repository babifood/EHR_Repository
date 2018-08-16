package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.DormitoryDao;
import com.babifood.entity.DormitoryCostEntity;
import com.babifood.entity.DormitoryEntity;
import com.babifood.utils.BASE64Util;
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
		sql.append("select a.id as id, a.floor as floor, a.room_no as roomNo, a.Bed_No as bedNo, b.stay_time as stayTime, ");
		sql.append("a.sex as sex, a.remark as remark,c.p_number AS pNumber, c.p_name AS pName, b.out_time as outTime, ");
		sql.append("IF(b.p_number IS NOT NULL,'已入住', '未入住') as stay  from ehr_dormitory a ");
		sql.append("LEFT JOIN (SELECT * FROM ehr_dormitory_stay where is_delete = '0') b on a.id = b.dormitory_id ");
		sql.append("LEFT JOIN ehr_person_basic_info c ON b.p_number = c.p_number ");
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
		sql.append("SELECT a.p_number AS pNumber, c.p_name AS pName, b.floor AS floor, ");
		sql.append("b.room_no AS roomNo, b.Bed_No AS bedNo, b.sex AS sex, a.stay_time AS stayTime, ");
		sql.append("a.out_time AS outTime ");
		sql.append("FROM ehr_dormitory_stay a ");
		sql.append("LEFT JOIN ehr_dormitory b ON a.dormitory_id = b.ID ");
		sql.append("LEFT JOIN ehr_person_basic_info c ON a.p_number = c.p_number");
		sql.append(" where 1=1 ");
		if(!UtilString.isEmpty(params.get("floor") + "")){
			sql.append(" AND b.floor = " + params.get("floor"));
		}
		if(!UtilString.isEmpty(params.get("sex") + "")){
			sql.append(" AND b.sex = " + params.get("sex"));
		}
		if(!UtilString.isEmpty(params.get("pNumber") + "")){
			sql.append(" AND a.p_number like '%" + params.get("pNumber") + "%'");
		}
		if(!UtilString.isEmpty(params.get("pName") + "")){
			sql.append(" AND c.p_name like '%" + params.get("pName") + "%'");
		}
		if(!UtilString.isEmpty(params.get("roomNo") + "")){
			sql.append(" AND b.room_no like '%" + params.get("roomNo") + "%'");
		}
		sql.append(" ORDER BY create_time DESC Limit ?,?");
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
	public Map<String, Object> findCheakingDormitory(String pnumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, p_number as pNumber, dormitory_id as dormitoryId, stay_time as stayTime, out_time as outTime,");
		sql.append("create_time as createTime, remark, is_delete as isDelete from ehr_dormitory_stay");
		sql.append(" where p_number = ? and is_delete = '0'");
		Map<String, Object> dormitory = null;
		List<Map<String, Object>> dormitorys = null;
		try {
			dormitorys = jdbcTemplate.queryForList(sql.toString(), pnumber);
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

	@Override
	public int queryDormitoryCostCount(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM EHR_DORMITORY_ALLOWANCES a ");
		sql.append("LEFT JOIN (SELECT * FROM ehr_dormitory_stay WHERE IS_DELETE = '0') b ON a.P_NUMBER = b.p_number ");
		sql.append("LEFT JOIN ehr_dormitory c ON c.ID = b.dormitory_id ");
		sql.append("LEFT JOIN ehr_person_basic_info d ON a.P_NUMBER = d.p_number ");
		sql.append("WHERE 1=1 ");
		if(!UtilString.isEmpty(params.get("floor") + "")){
			sql.append(" AND c.FLOOR = '" + params.get("floor") + "'");
		}
		if(!UtilString.isEmpty(params.get("pNumber") + "")){
			sql.append(" AND a.P_NUMBER LIKE '%" + params.get("pNumber") + "%'");
		}
		if(!UtilString.isEmpty(params.get("pName") + "")){
			sql.append(" AND d.p_name LIKE '%" + params.get("pName") + "%'");
		}
		if(!UtilString.isEmpty(params.get("roomNo") + "")){
			sql.append(" AND c.ROOM_NO LIKE '%" + params.get("roomNo") + "%'");
		}
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询费用信息条数失败",e.getMessage());
			throw e;
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> queryDormitoryCostList(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ID AS id, a.DORM_DEDUCTION AS dormDeduction, a.DORM_BONUS AS dormBonus, ");
		sql.append("a.`YEAR` AS `year`, a.`MONTH` AS `month`, a.P_NUMBER AS pNumber, d.p_name AS pName, ");
		sql.append("c.FLOOR AS floor, c.ROOM_NO AS roomNo, c.BED_NO AS bedNo FROM EHR_DORMITORY_ALLOWANCES a ");
		sql.append("LEFT JOIN (SELECT * FROM ehr_dormitory_stay WHERE IS_DELETE = '0') b ON a.P_NUMBER = b.p_number ");
		sql.append("LEFT JOIN ehr_dormitory c ON c.ID = b.dormitory_id ");
		sql.append("LEFT JOIN ehr_person_basic_info d ON a.P_NUMBER = d.p_number ");
		sql.append("WHERE 1=1 ");
		if(!UtilString.isEmpty(params.get("floor") + "")){
			sql.append(" AND c.FLOOR = '" + params.get("floor") + "'");
		}
		if(!UtilString.isEmpty(params.get("pNumber") + "")){
			sql.append(" AND a.P_NUMBER LIKE '%" + params.get("pNumber") + "%'");
		}
		if(!UtilString.isEmpty(params.get("pName") + "")){
			sql.append(" AND d.p_name LIKE '%" + params.get("pName") + "%'");
		}
		if(!UtilString.isEmpty(params.get("roomNo") + "")){
			sql.append(" AND c.ROOM_NO LIKE '%" + params.get("roomNo") + "%'");
		}
		if(!UtilString.isEmpty(params.get("start") + "") && !UtilString.isEmpty(params.get("pageSize") + "")){
			sql.append("GROUP BY a.`YEAR`, a.`MONTH`, a.P_NUMBER ORDER BY a.`YEAR` DESC, a.`MONTH` DESC ");
			sql.append(" Limit " + params.get("start") + ", " + params.get("pageSize"));
		}
		List<Map<String, Object>> costList = null;
		try {
			costList = jdbcTemplate.queryForList(sql.toString());
			BASE64Util.Base64DecodeMap(costList);
		} catch (Exception e) {
			log.error("查询费用信息列表失败",e.getMessage());
			throw e;
		}
		return costList;
	}

	@Override
	public void saveCost(DormitoryCostEntity dormitoryCost) {
		StringBuffer sql = new StringBuffer();
		sql.append("REPLACE INTO `ehr_dormitory_allowances` (`YEAR`, `MONTH`, `P_NUMBER`, `DORM_BONUS`, `DORM_DEDUCTION`)");
		sql.append(" VALUES (?, ?, ?, ?, ?)");
		try {
			jdbcTemplate.update(sql.toString(), dormitoryCost.getYear(), dormitoryCost.getMonth(), dormitoryCost.getpNumber(), 
					BASE64Util.getDecodeStringTowDecimal(dormitoryCost.getDormBonus()), BASE64Util.getDecodeStringTowDecimal(dormitoryCost.getDormDeduction()));
		} catch (Exception e) {
			log.error("保存住宿费用信息失败",e.getMessage());
			throw e;
		}
	}

	@Override
	public void removeCost(Integer id) {
		String sql = "DELETE FROM EHR_DORMITORY_ALLOWANCES WHERE id = ?";
		try {
			jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			log.error("删除住宿费用信息失败",e.getMessage());
			throw e;
		}
	}

	@Override
	public void moveOutProcedure(String dormitoryId, String pnumber, String outTime) {
		String sql = "update `ehr_dormitory_stay` set `out_time` = ? where p_number = ? and dormitory_id = ?";
		try {
			jdbcTemplate.update(sql, outTime, pnumber, dormitoryId);
		} catch (Exception e) {
			log.error("删除入住信息失败",e.getMessage());
			throw e;
		}
	}

	@Override
	public void saveDormitoryCosts(List<Object[]> performanceParam) {
		StringBuffer sql = new StringBuffer();
		sql.append("REPLACE INTO `ehr_dormitory_allowances` (`YEAR`, `MONTH`, `P_NUMBER`, `DORM_BONUS`, `DORM_DEDUCTION`)");
		sql.append(" VALUES (?, ?, ?, ?, ?)");
		try {
			jdbcTemplate.batchUpdate(sql.toString(),performanceParam);
		} catch (Exception e) {
			log.error("保存住宿费用信息失败",e.getMessage());
			throw e;
		}
		
	}

}
