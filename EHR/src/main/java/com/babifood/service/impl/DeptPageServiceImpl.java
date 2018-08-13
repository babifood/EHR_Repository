package com.babifood.service.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.babifood.constant.OperatingConstant;
import com.babifood.dao.DeptPageDao;
import com.babifood.entity.DeptEntity;
import com.babifood.service.DeptPageService;
import com.babifood.utils.ExcelUtil;
import com.babifood.utils.IdGen;
import com.babifood.utils.UtilString;

@Service
public class DeptPageServiceImpl implements DeptPageService {
	
	private static Logger logger = Logger.getLogger(DeptPageServiceImpl.class);
	
	@Autowired
	private DeptPageDao deptPageDao;
	
	@Override
	public List<Map<String,Object>> findOrganization(String deptCode) {
		deptCode = UtilString.isEmpty(deptCode) ? "0000" : deptCode;
		Map<String, List<Map<String,Object>>> deptMapList = findAllDepts(deptCode);
		List<Map<String,Object>> deptList = null;
		if(deptMapList != null && deptMapList.size() > 0){
			deptList = deptMapList.get(deptCode.substring(0,deptCode.length() - 4));
			if(deptList != null && deptList.size() > 0){
				getOrganizeChildrens(deptList,deptMapList);
			}
		}
		//当前部门
//		Map<String, Object> organize = deptPageDao.findOrganizeByDeptCode(deptCode);
//		if(MapUtils.isNotEmpty(organize)){
//			List<Map<String, Object>> organizeList = getOrganizeChildren(organize.get("deptCode")+"");
//			if(!CollectionUtils.isEmpty(organizeList)){
//				organize.put("children", organizeList);
//			}
//		}
//		OperatingLogUtil.recordOperatingLog("", OperatingConstant.OPERATING_TYPE_FIND, "1", this.getClass().getName(), "组织机构树查询");
		return deptList;
	}

	/**
	 * 获取对应的下级组织机构
	 * @param deptList
	 * @param deptMapList
	 */
	private void getOrganizeChildrens(List<Map<String, Object>> deptList,
		Map<String, List<Map<String, Object>>> deptMapList) {
		for(Map<String, Object> map : deptList){
			String deptCode = map.get("deptCode")+"";
			List<Map<String, Object>> depts = deptMapList.get(deptCode);
			if(depts != null && depts.size() > 0){
				getOrganizeChildrens(depts, deptMapList);
				map.put("children", depts);
			} else {
				map.put("state", "open");
			}
		}
	}

	/**
	 * 查询所有组织机构--Map
	 * @param pCode
	 * @return
	 */
	private Map<String, List<Map<String,Object>>> findAllDepts(String pCode) {
		Map<String, List<Map<String,Object>>> deptMapList = new HashMap<String, List<Map<String,Object>>>();
		List<Map<String,Object>> dataSource = deptPageDao.findAll(pCode);
		if(dataSource != null && dataSource.size() > 0){
			for(Map<String,Object> map : dataSource){
				String deptCode = map.get("deptCode") + "";
				String pcode = deptCode.substring(0, deptCode.length() - 4);
				List<Map<String, Object>> deptList = deptMapList.get(pcode);
				if(deptList == null){
					deptList = new ArrayList<Map<String, Object>>();
				}
				deptList.add(map);
				deptMapList.put(pcode, deptList);
			}
		}
		return deptMapList;
	}
	
	/**
	 * 获取下级所有下级
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getOrganizeChildren(String pCode){
		if(UtilString.isEmpty(pCode)){
			return null;
		}
		//根据pcode查询直属下级机构
		List<Map<String, Object>> organizeList = deptPageDao.findOrganizeList(pCode);
		if(!CollectionUtils.isEmpty(organizeList)){
			for (Map<String, Object> map : organizeList) {
				List<Map<String, Object>> organizes = getOrganizeChildren(map.get("deptCode")+"");
				if(!CollectionUtils.isEmpty(organizes)){
					map.put("children", organizes);
				} else {
					map.put("state", "");
				}
			}
		}
		return organizeList;
	}

	@Override
	public Map<String, Object> addDept(DeptEntity deptEntity) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(deptEntity == null){
			resultMap.put("code", "0");
			resultMap.put("msg", "添加部门失败");
			logger.error("部门信息为空deptEntity:" + deptEntity);
			return resultMap;
		}
		deptEntity.setId(IdGen.uuid());
		deptEntity.setSource_type("0");
		deptEntity.setType("0");
		try {
			deptPageDao.insertDept(deptEntity);
			resultMap.put("code", "1");
			resultMap.put("msg", "添加部门成功");
		} catch (Exception e) {
			resultMap.put("code", "0");
			resultMap.put("msg", "添加部门失败");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> updateDept(DeptEntity deptEntity) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(deptEntity == null){
			resultMap.put("code", "0");
			resultMap.put("msg", "修改部门信息失败");
			logger.error("部门信息为空deptEntity:" + deptEntity);
			return resultMap;
		}
		if(deptEntity.getpCode() == null || deptEntity.getpCode().equals(deptEntity.getDeptCode())){
			resultMap.put("code", "0");
			resultMap.put("msg", "上级部门不能选择自己");
			return resultMap;
		}
		deptPageDao.updateDept(deptEntity);
		resultMap.put("code", "1");
		resultMap.put("msg", "修改部门信息成功");
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteDept(String deptCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(UtilString.isEmpty(deptCode)){
			resultMap.put("code", "0");
			resultMap.put("msg", "部门编号不存在");
			return resultMap;
		}
		//判断是否有子部门
		List<Map<String, Object>> depts = deptPageDao.findOrganizeList(deptCode);
		if(!CollectionUtils.isEmpty(depts)){
			resultMap.put("code", "0");
			resultMap.put("msg", "该部门下有子部门，不能删除");
			return resultMap;
		}
		//判断是否有员工
		//TODO
		deptPageDao.deleteDept(deptCode);
		resultMap.put("code", "1");
		resultMap.put("msg", "删除部门信息成功");
		return resultMap;
	}

	@Override
	public Map<String, Object> findDeptByDeptCode(String deptCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(UtilString.isEmpty(deptCode)){
			resultMap.put("code", "0");
			resultMap.put("msg", "部门编号不存在");
			return resultMap;
		}
		return deptPageDao.findDeptByDeptCode(deptCode);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		return deptPageDao.findAllDepts();
	}

	@Override
	public void exportExcel(OutputStream ouputStream,String type) throws Exception {
//		String[] rowNames = new String[]{"部门名称","部门编号","上级部门名称","上级部门编号","备注"};
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("deptName", "部门名称");
		row1Name.put("deptCode", "部门编号");
		row1Name.put("pName", "上级部门名称");
		row1Name.put("pCode", "上级部门编号");
		row1Name.put("remark", "备注");
		String[] sort = new String[]{"deptName", "deptCode", "pName", "pCode", "remark"};
		List<Map<String, Object>> dataSource = null;
		if("0".equals(type)){//模板
			dataSource = new ArrayList<Map<String, Object>>();
		} else {//数据
			dataSource = deptPageDao.findAll(null);
		}
		ExcelUtil.exportExcel("部门信息列表", row1Name, dataSource, ouputStream, sort);
	}

	@Override
	public Map<String, Object> queryCountByDeptCode(String deptCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(UtilString.isEmpty(deptCode)){
			resultMap.put("code", "0");
			resultMap.put("msg", "部门编号不存在");
			return resultMap;
		}
		return deptPageDao.queryCountByDeptCode(deptCode);
	}

}
