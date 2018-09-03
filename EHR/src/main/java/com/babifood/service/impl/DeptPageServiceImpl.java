package com.babifood.service.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.dao.DeptPageDao;
import com.babifood.dao.personSelectionWindowDao;
import com.babifood.entity.DeptEntity;
import com.babifood.entity.LoginEntity;
import com.babifood.service.DeptPageService;
import com.babifood.utils.ExcelUtil;
import com.babifood.utils.IdGen;
import com.babifood.utils.UtilString;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;

/**
 * 部门信息service层
 * @author wangguocheng
 *
 */
@Service
public class DeptPageServiceImpl implements DeptPageService {
	
	private static Logger logger = Logger.getLogger(DeptPageServiceImpl.class);
	
	@Autowired
	private DeptPageDao deptPageDao;
	
	@Autowired
	private personSelectionWindowDao personSelectionWindowDao;
	
	/**
	 * 查询组织机构数
	 */
	@Override
	@LogMethod(module = ModuleConstant.DEPT)
	public List<Map<String,Object>> findOrganization(String deptCode) {
		deptCode = UtilString.isEmpty(deptCode) ? "0000" : deptCode;
		List<Map<String,Object>> deptList = null;
		try {
			Map<String, List<Map<String,Object>>> deptMapList = findAllDepts(deptCode);
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			if(deptMapList != null && deptMapList.size() > 0){
				deptList = deptMapList.get(deptCode.substring(0,deptCode.length() - 4));
				if(deptList != null && deptList.size() > 0){
					getOrganizeChildrens(deptList,deptMapList);
				}
			}
			LogManager.putContectOfLogInfo("查询组织机构树");
		} catch (Exception e) {
			logger.error("查询组织机构树失败", e);
			LogManager.putContectOfLogInfo("查询组织机构树失败，错误信息：" + e.getMessage());
		}
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
	 * 新增部门信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DEPT)
	public Map<String, Object> addDept(DeptEntity deptEntity) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
		deptEntity.setId(IdGen.uuid());
		deptEntity.setSource_type("0");
		deptEntity.setType("0");
		try {
			deptPageDao.insertDept(deptEntity);
			resultMap.put("code", "1");
			resultMap.put("msg", "添加部门成功");
			LogManager.putContectOfLogInfo("新增部门信息");
		} catch (Exception e) {
			resultMap.put("code", "0");
			resultMap.put("msg", "添加部门失败");
			logger.error("添加部门失败",e);
			LogManager.putContectOfLogInfo("新增部门信息失败,错误信息："+e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 修改部门信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DEPT)
	public Map<String, Object> updateDept(DeptEntity deptEntity) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EDIT);
		if(deptEntity == null){
			resultMap.put("code", "0");
			resultMap.put("msg", "修改部门信息失败");
			logger.error("部门信息为空deptEntity:" + deptEntity);
			LogManager.putContectOfLogInfo("修改部门信息失败,错误信息：部门信息为空");
			return resultMap;
		}
		if(deptEntity.getpCode() == null || deptEntity.getpCode().equals(deptEntity.getDeptCode())){
			resultMap.put("code", "0");
			resultMap.put("msg", "上级部门不能选择自己");
			LogManager.putContectOfLogInfo("修改部门信息失败,错误信息：上级部门不能选择自己");
			return resultMap;
		}
		try {
			deptPageDao.updateDept(deptEntity);
			resultMap.put("code", "1");
			resultMap.put("msg", "修改部门信息成功");
			LogManager.putContectOfLogInfo("修改部门信息");
		} catch (Exception e) {
			logger.error("修改部门信息失败",e);
			LogManager.putContectOfLogInfo("修改部门信息失败,错误信息："+e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 删除部门信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DEPT)
	public Map<String, Object> deleteDept(String deptCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
		if(UtilString.isEmpty(deptCode)){
			resultMap.put("code", "0");
			resultMap.put("msg", "部门编号为空");
			LogManager.putContectOfLogInfo("删除部门信息失败,错误信息：部门编号为空");
			return resultMap;
		}
		try {
			//判断是否有子部门
			List<Map<String, Object>> depts = deptPageDao.findOrganizeList(deptCode);
			if(!CollectionUtils.isEmpty(depts)){
				resultMap.put("code", "0");
				resultMap.put("msg", "该部门下有子部门，不能删除");
				LogManager.putContectOfLogInfo("删除部门信息失败,错误信息：该部门下有子部门，不能删除");
				return resultMap;
			}
			//判断是否有员工
			List<Map<String, Object>> person = personSelectionWindowDao.loadunSelectPersonByDeptID(deptCode);
			if(person != null && person.size() > 0){
				resultMap.put("code", "0");
				resultMap.put("msg", "该部门下有员工存在，不能删除");
				LogManager.putContectOfLogInfo("删除部门信息失败,错误信息：该部门下有员工存在，不能删除");
				return resultMap;
			}
			deptPageDao.deleteDept(deptCode);
			resultMap.put("code", "1");
			resultMap.put("msg", "删除部门信息成功");
			LogManager.putContectOfLogInfo("删除部门信息");
		} catch (Exception e) {
			logger.error("删除部门信息失败",e);
			LogManager.putContectOfLogInfo("删除部门信息失败,错误信息："+e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 根据部门编号查询部门信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DEPT)
	public Map<String, Object> findDeptByDeptCode(String deptCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		if(UtilString.isEmpty(deptCode)){
			resultMap.put("code", "0");
			resultMap.put("msg", "部门编号不存在");
			LogManager.putContectOfLogInfo("根据部门编号查询部门信息失败,错误信息：部门编号不存在");
			return resultMap;
		}
		Map<String, Object> dept = null;
		try {
			dept = deptPageDao.findDeptByDeptCode(deptCode);
			LogManager.putContectOfLogInfo("根据部门编号查询部门信息");
		} catch (Exception e) {
			logger.error("根据部门编号查询部门信息失败",e);
			LogManager.putContectOfLogInfo("根据部门编号查询部门信息失败,错误信息："+e.getMessage());
		}
		return dept;
	}

	/**
	 * 查询全部部门机构信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DEPT)
	public List<Map<String, Object>> findAll() {
		List<Map<String, Object>> deptList = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			deptList = deptPageDao.findAllDepts();
			LogManager.putContectOfLogInfo("查询全部部门机构信息");
		} catch (Exception e) {
			logger.error("查询全部部门机构信息失败",e);
			LogManager.putContectOfLogInfo("查询全部部门机构信息失败,错误信息："+e.getMessage());
		}
		return deptList;
	}

	/**
	 * 导出excel
	 */
	@Override
	@LogMethod(module = ModuleConstant.DEPT)
	public void exportExcel(OutputStream ouputStream,String type) throws Exception {
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
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
			LogManager.putContectOfLogInfo("excel导出部门信息");
		} catch (Exception e) {
			logger.error("excel导出部门信息失败",e);
			LogManager.putContectOfLogInfo("excel导出部门信息失败,错误信息："+e.getMessage());
		}
	}

	/**
	 * 查询子部门数量
	 */
	@Override
	@LogMethod(module = ModuleConstant.DEPT)
	public Map<String, Object> queryCountByDeptCode(String deptCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
		if(UtilString.isEmpty(deptCode)){
			resultMap.put("code", "0");
			resultMap.put("msg", "部门编号不存在");
			LogManager.putContectOfLogInfo("查询子部门数量失败,错误信息：部门编号不存在");
			return resultMap;
		}
		try {
			resultMap = deptPageDao.queryCountByDeptCode(deptCode);
			LogManager.putContectOfLogInfo("查询子部门数量");
		} catch (Exception e) {
			logger.error("查询部门数量失败",e);
			LogManager.putContectOfLogInfo("查询子部门数量失败,错误信息："+e.getMessage());
		}
		return resultMap;
	}

}
