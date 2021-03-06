package com.babifood.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.DeptPageDao;
import com.babifood.entity.DeptEntity;
import com.babifood.utils.UtilString;

@Repository
public class DeptPageDaoImpl implements DeptPageDao {

	private static Logger log = Logger.getLogger(DeptPageDaoImpl.class);

	@Autowired
	JdbcTemplate jdbctemplate;

	/**
	 * 查询子部门信息列表
	 */
	@Override
	public List<Map<String, Object>> findOrganizeList(String pCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.ID as id, a.dept_code as deptCode ,a.dept_name as name, type,state,");
		sql.append("a.dept_name as deptName ,a.dept_name as text , a.pCode as pCode ,a.remark as remark");
		sql.append(" FROM ehr_dept a ");
		sql.append(" where a.pCode = ?");
		List<Map<String, Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString(), pCode);
		} catch (Exception e) {
			log.error("查询子部门信息列表", e);
			throw e;
		}
		return list;
	}

	/**
	 * 查询组织机构
	 */
	@Override
	public Map<String, Object> findOrganizeByDeptCode(String deptCode) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.ID as id, a.dept_code as deptCode ,a.dept_name as name ,a.dept_name as deptName ,a.dept_name as text, a.pCode pCode ,type,a.remark as remark,state");
		sql.append(" FROM ehr_dept a ");
		sql.append(" where a.dept_code = ?");
		Map<String, Object> organize = null;
		try {
			organize = jdbctemplate.queryForMap(sql.toString(), deptCode);
		} catch (Exception e) {
			log.error("查询组织机构失败", e);
			throw e;
		}
		return organize;
	}

	/**
	 * 新增组织机构
	 */
	@Override
	public void insertDept(DeptEntity deptEntity) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `ehr_dept` ");
		sql.append(
				" (`id`, `dept_name`, `dept_code`, `type`, `pCode`, `remark`,`source_type`,`createTime`,`updateTime`) ");
		sql.append(
				" VALUES (?,?, ?, ?, ?, ?,?,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'),DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) ");
		try {
			jdbctemplate.update(sql.toString(), deptEntity.getId(),deptEntity.getDeptName(), deptEntity.getDeptCode(),
					deptEntity.getType(), deptEntity.getpCode(), deptEntity.getRemark(), deptEntity.getSource_type());
		} catch (Exception e) {
			log.error("新增组织机构失败", e);
			throw e;
		}
	}

	/**
	 * 修改组织机构
	 */
	@Override
	public Integer updateDept(DeptEntity deptEntity) {
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_dept set dept_name = ?,dept_code = ?,pCode = ? ,remark = ? ,updateTime = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') where ID = ?");
		Integer count = null;
		try {
			count = jdbctemplate.update(sql.toString(), deptEntity.getDeptName(),deptEntity.getDeptCode(), deptEntity.getpCode(),
					deptEntity.getRemark(), deptEntity.getId());
		} catch (Exception e) {
			log.error("修改组织机构失败", e);
			throw e;
		}
		return count;
	}

	/**
	 * 删除组织机构
	 */
	@Override
	public void deleteDept(String deptCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ehr_dept where dept_code = ?");
		try {
			jdbctemplate.update(sql.toString(), deptCode);
		} catch (Exception e) {
			log.error("删除组织机构失败", e);
			throw e;
		}
	}

	/**
	 * 根据id查询组织机构信息
	 */
	@Override
	public Map<String, Object> findDeptByDeptCode(String deptCode) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select ID as id,dept_code as detpCode,dept_name as deptName,pCode as pCode,remark as remark from ehr_dept where dept_code = ?");
		Map<String, Object> dept = new HashMap<String, Object>();
		try {
			dept = jdbctemplate.queryForMap(sql.toString(), deptCode);
			dept.put("code", "1");
		} catch (Exception e) {
			dept.put("code", "0");
			dept.put("msg", "查询数据异常");
			log.error("根据id查询组织机构信息失败", e);
			throw e;
		}
		return dept;
	}

	/**
	 * 查询所有组织机构信息
	 */
	@Override
	public List<Map<String, Object>> findAllDepts() {
		StringBuffer sql = new StringBuffer();
		sql.append("select ID as id,dept_code as deptCode,dept_name as deptName,");
		sql.append("pCode as pCode,remark as remark from ehr_dept");
		List<Map<String, Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			log.error("查询所有组织机构信息失败", e);
			throw e;
		}
		return list;
	}
	
	/**
	 * 查询所有组织机构信息
	 */
	@Override
	public List<Map<String, Object>> findAll(String pcode) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.dept_name as deptName,a.dept_code as deptCode , a.state, a.type as type,");
		sql.append("a.remark as remark ,a.pCode as pCode, b.dept_name as pName ,a.dept_name as text ");
		sql.append("from ehr_dept a left join ehr_dept b on a.pCode = b.dept_code ");
		if(!UtilString.isEmpty(pcode)){
			sql.append(" where a.dept_code like '%" + pcode + "%'");
		}
		List<Map<String, Object>> list = null;
		try {
			list = jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			log.error("查询所有组织机构信息失败", e);
			throw e;
		}
		return list;
	}

	/**
	 * 根据id查询组织机构信息
	 */
	@Override
	public Map<String, Object> queryCountByDeptCode(String deptCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select count(*) from ehr_dept where dept_code = ?";
		int count = 0;
		try {
			count = jdbctemplate.queryForInt(sql, deptCode);
			result.put("code", "1");
			result.put("count", count);
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "系统异常，请稍后重试");
			log.error("根据id查询组织机构信息失败", e);
			throw e;
		}
		return result;
		
	}

	/**
	 * 根据部门编号查询部门列表
	 */
	@Override
	public List<Map<String, Object>> findDeptsByDeptCodes(Object[] deptCodeList) {
		StringBuffer sql = new StringBuffer();
		sql.append("select dept_code as deptCode,dept_name as deptName,type from ehr_dept ");
		sql.append(" where dept_code in (");
		for(int i=0;i<deptCodeList.length;i++){
			if(i == deptCodeList.length - 1){
				sql.append("?");
			} else {
				sql.append("?,");
			}
		}
		sql.append(" )");
		List<Map<String, Object>> deptList = null;
		try {
			deptList = jdbctemplate.queryForList(sql.toString(), deptCodeList);
		} catch (Exception e) {
			log.error("根据deptCodes查询部门列表失败", e);
			throw e;
		}
		return deptList;
	}

}
