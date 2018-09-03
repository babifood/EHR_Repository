package com.babifood.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.dao.BaseArrangementDao;
import com.babifood.dao.PersonInFoDao;
import com.babifood.entity.BaseArrangementEntity;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.PersonBasrcEntity;
import com.babifood.entity.SpecialArrangementEntity;
import com.babifood.service.BaseArrangementService;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;

/**
 * 排班数据操作类
 * @author wangguocheng
 *
 */
@Service
@Transactional
public class BaseArrangementServiceImpl implements BaseArrangementService {

	private static Logger logger = Logger.getLogger(BaseArrangementServiceImpl.class);
	
	@Autowired
	private BaseArrangementDao baseArrangementDao;
	
	@Autowired
	private PersonInFoDao personInFoDao;

	/**
	 * 查询全部排班信息
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public List<Map<String, Object>> findBaseArrangements() {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> arrangements = new ArrayList<Map<String, Object>>();
		try {
			arrangements = baseArrangementDao.findAllBaseArrangement();
			LogManager.putContectOfLogInfo("查询全部基础作息时间");
		} catch (Exception e) {
			logger.error("查询所有基础排班信息失败",e);
			LogManager.putContectOfLogInfo("查询全部基础作息时间失败，错误信息："+e.getMessage());
		}
		return arrangements;
	}

	/**
	 * 保存基础作息时间
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public Map<String, Object> saveBaseArrangement(BaseArrangementEntity arrangement) {
		Map<String, Object> result = new HashMap<String, Object>();
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		try {
			if (arrangement.getId() != null && arrangement.getId() > 0) {
				baseArrangementDao.updateBaseArrangement(arrangement);
				LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
			} else {
				baseArrangementDao.addBaseArrangement(arrangement);
				LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EDIT);
			}
			result.put("code", "1");
			result.put("msg", "保存基本作息时间成功");
			LogManager.putContectOfLogInfo("保存基础作息时间");
		} catch (Exception e) {
			logger.error("保存基础排班信息失败",e);
			result.put("code", "0");
			result.put("msg", "保存基本作息时间失败");
		}
		return result;
	}

	/**
	 * 删除特殊排班
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public Map<String, Object> removeBaseArrangement(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
			List<Map<String, Object>> arrangementTargets = baseArrangementDao.findArrangementTargets(id);
			if(arrangementTargets != null && arrangementTargets.size() > 0){
				result.put("code", "0");
				result.put("msg", "该基础作息时间有相关部门或人员使用不能删除");
				LogManager.putContectOfLogInfo("该基础作息时间有相关部门或人员使用不能删除,id="+id);
			} else {
				baseArrangementDao.removeBaseArrangement(id);
				result.put("code", "1");
				result.put("msg", "删除基本作息时间成功");
				LogManager.putContectOfLogInfo("删除基本作息时间，id="+id);
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "删除基本作息时间失败");
			logger.error("保删除基础排班信息失败,id:"+id,e);
			LogManager.putContectOfLogInfo("删除基本作息时间失败，id="+id+","+e.getMessage());
		}
		return result;
	}

	/**
	 * 查询特殊排班列表
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public Map<String, Object> findSpecialArrangementList(String date, String arrangementId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> arrangementList = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			arrangementList = baseArrangementDao.findSpecialArrangementList(date, arrangementId);
			if (arrangementList != null && arrangementList.size() > 0) {
				for (Map<String, Object> map : arrangementList) {
					String day = (map.get("date") + "").split("-")[2];
					map.put("day", day);
				}
			}
			result.put("code", "1");
			result.put("msg", "查询特殊排版列表成功");
			result.put("arrangementList", arrangementList);
			LogManager.putContectOfLogInfo("查询特殊排班列表");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询特殊排版列表失败");
			logger.error("查询特殊排版列表失败,arrangementId:"+arrangementId,e);
			LogManager.putContectOfLogInfo("查询特殊排班列表失败，date="+ date +",arrangementId="+arrangementId+",错误信息:"+e.getMessage());
		}
		return result;
	}

	/**
	 * 保存特殊排班信息
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public Map<String, Object> saveSpecialArrangement(SpecialArrangementEntity specialArrangement) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			if (UtilDateTime.isLaterThanCurrentTime(specialArrangement.getDate(), "yyyy-MM-dd")) {
				if("0".equals(specialArrangement.getIsAttend())){
					Map<String, Object> arrangement = baseArrangementDao.findBaseArrangementById(specialArrangement.getArrangementId());
					specialArrangement.setStartTime(arrangement.get("startTime") + "");
					specialArrangement.setEndTime(arrangement.get("endTime") + "");
				}
				if (UtilString.isEmpty(specialArrangement.getId())) {
					baseArrangementDao.addSpecialArrangement(specialArrangement);
					LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
				} else {
					baseArrangementDao.updateSpecialArrangement(specialArrangement);
					LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EDIT);
				}
				result.put("code", "1");
				result.put("msg", "保存特殊排班成功");
				LogManager.putContectOfLogInfo("保存特殊排班列表");
			} else {
				result.put("code", "0");
				result.put("msg", "排班日期不能为当月或之前日期");
				LogManager.putContectOfLogInfo("保存特殊排班列表失败,错误信息:排班日期不能为当月或之前日期");
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存特殊排班失败");
			logger.error("保存特殊排班失败",e);
			LogManager.putContectOfLogInfo("保存特殊排班列表失败,错误信息:"+e.getMessage());
		}
		return result;
	}

	/**
	 * 根据id删除特殊排班
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public Map<String, Object> removeSpecialArrangement(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
			Map<String, Object> arrangement = baseArrangementDao.findSpecialArrangementById(id);
			if(arrangement != null && UtilDateTime.isLaterThanCurrentTime(arrangement.get("date")+"", "yyyy-MM-dd")){
				result.put("code", "0");
				result.put("msg", "当月或之前的特殊排班不能删除");
				logger.info("当月或之前的特殊排班不能删除");
				LogManager.putContectOfLogInfo("删除特殊排班,错误信息：当月或之前的特殊排班不能删除");
			} else {
				baseArrangementDao.updateSpecialArrangement(id);
				result.put("code", "1");
				result.put("msg", "删除特殊排班成功");
				LogManager.putContectOfLogInfo("删除特殊排班");
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "特殊排班不存在或删除失败");
			logger.error("删除特殊排班失败",e);
			LogManager.putContectOfLogInfo("删除特殊排班失败,错误信息："+e.getMessage());
		}
		return result;
	}

	/**
	 * 根据id查询特殊排班
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public Map<String, Object> findSpecialArrangementById(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> arrangement = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			arrangement = baseArrangementDao.findSpecialArrangementById(id);
			result.put("code", "1");
			result.put("msg", "查询特殊排班成功");
			result.put("arrangement", arrangement);
			LogManager.putContectOfLogInfo("根据id查询特殊排班");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询特殊排班失败");
			logger.error("根据Id查询特殊排班失败",e);
			LogManager.putContectOfLogInfo("根据id查询特殊排班失败,id=" + id + ",错误信息:"+e.getMessage());
		}
		return result;
	}

	/**
	 * 根据时间，部门id，或者员工编号查询特殊排班列表
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public Map<String, Object> findSpecialArrangementList(String date, String deptCode, String pNumber) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> arrangementList = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			Map<String, Object> arrangement = getArrangementId(deptCode, pNumber);
			if(arrangement != null){
				arrangementList = baseArrangementDao.findSpecialArrangementList(date, arrangement.get("arrangementId")+"");
				if (arrangementList.size() > 0) {
					for (Map<String, Object> map : arrangementList) {
						String day = (map.get("date") + "").split("-")[2];
						map.put("day", day);
					}
				}
				result.putAll(arrangement);
			}
			result.put("code", "1");
			result.put("msg", "查询特殊排版列表成功");
			result.put("arrangementList", arrangementList);
			LogManager.putContectOfLogInfo("根据特殊排班列表");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询特殊排版列表失败");
			logger.error("根据部门或人员编号查询特殊排班失败",e);
			LogManager.putContectOfLogInfo("根据id查询特殊排班失败,date=" + date + ",deptCode="+ deptCode + ",pNumber="+ pNumber +",错误信息:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据部门id，或者员工编号查询特殊排班列表
	 * @param deptCode
	 * @param pNumber
	 * @return
	 */
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public Map<String, Object> getArrangementId(String deptCode, String pNumber) {
		Map<String, Object> arrangement = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			List<String> targetIds = new ArrayList<String>();
			if (!UtilString.isEmpty(pNumber)) {
				PersonBasrcEntity person = (PersonBasrcEntity) personInFoDao.getPersonByPnumber(pNumber);
				if(person != null){
					if (!UtilString.isEmpty(person.getP_company_id())) {
						targetIds.add(person.getP_company_id());
					}
					if (!UtilString.isEmpty(person.getP_department_id())) {
						targetIds.add(person.getP_department_id());
					}
					if (!UtilString.isEmpty(person.getP_organization_id())) {
						targetIds.add(person.getP_organization_id());
					}
					if (!UtilString.isEmpty(person.getP_section_office_id())) {
						targetIds.add(person.getP_section_office_id());
					}
					if(!UtilString.isEmpty(person.getP_group_id())){
						targetIds.add(person.getP_group_id());
					}
					targetIds.add(pNumber);
				}
			} else if (!UtilString.isEmpty(deptCode)) {
				for (int i = 0; i < deptCode.length() / 4; i++) {
					String code = deptCode.substring(0, (i + 1) * 4);
					targetIds.add(code);
				}
			}
			if(targetIds.size() > 0){
				List<Map<String, Object>> arrangementList = baseArrangementDao.findArrangementByTargetId(targetIds);
				if (arrangementList != null && arrangementList.size() > 0) {
					arrangement = arrangementList.get(0);
				}
			}
			LogManager.putContectOfLogInfo("查询特殊排班列表");
		} catch (Exception e) {
			logger.error("查询特殊排班列表失败",e);
			LogManager.putContectOfLogInfo("根据特殊排班列表失败，deptCode="+deptCode + ",pNumber="+pNumber + ",错误信息:"+e.getMessage());
		}
		return arrangement;
	}

	/**
	 * 根据部门id，或者员工编号查询特殊排班列表
	 * @param deptCode
	 * @param pNumber
	 * @return
	 */
	@Override
	public Map<String, Object> findSpecialArrangementId(String deptCode, String pNumber) {
		return getArrangementId(deptCode,pNumber);
	}

	/**
	 * 绑定基础作息时间
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public Map<String, Object> bindArrangement(String targetId, String type, String arrangementId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_SETTING);
//			baseArrangementDao.deleteSettedArrangement(targetId);
			baseArrangementDao.bindArrangement(targetId, type, arrangementId);
//			String aString = null;
//			System.out.println(aString.length());
			result.put("code", "1");
			result.put("msg", "绑定排班成功");
			LogManager.putContectOfLogInfo("绑定基础作息时间");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "绑定排班失败");
			logger.error("绑定排班失败",e);
			LogManager.putContectOfLogInfo("绑定基础作息时间失败,错误信息："+ e.getMessage());
		}
		return result;
	}

	/**
	 * 查询当前月的特殊排班
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public List<Map<String, Object>> findCurrentMonthAllSpecialArrangement(String start ,String endTime) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		LogManager.putContectOfLogInfo("查询当前月所有特殊排班");
		return baseArrangementDao.findCurrentMonthAllSpecialArrangement(start ,endTime);
	}

	/**
	 * 查询指定年月的特殊排班
	 */
	@Override
	@LogMethod(module= ModuleConstant.ARRANGEMENT)
	public List<Map<String, Object>> findSpecialArrangementOfMonth(String year, String month) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		if(month.length() == 1){
			month = "0"+month;
		}
		int days = UtilDateTime.getDaysOfCurrentMonth(Integer.valueOf(year), Integer.valueOf(month));
		String startDay = year + "-" + month + "-01";
		String endDay = year + "-" + month + days;
		LogManager.putContectOfLogInfo("查询月度所有特殊排班");
		return baseArrangementDao.findSpecialArrangementOfMonth(startDay, endDay);
	}

}
