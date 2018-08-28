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

/**
 * 排班数据操作类
 * @author wangguocheng
 *
 */
@Service
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
	public List<Map<String, Object>> findBaseArrangements() {
		List<Map<String, Object>> arrangements = new ArrayList<Map<String, Object>>();
		try {
			arrangements = baseArrangementDao.findAllBaseArrangement();
		} catch (Exception e) {
			logger.error("查询所有基础排班信息失败",e);
		}
		return arrangements;
	}

	
	@Override
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
		} catch (Exception e) {
			logger.error("保存基础排班信息失败",e);
			result.put("code", "0");
			result.put("msg", "保存基本作息时间失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> removeBaseArrangement(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> arrangementTargets = baseArrangementDao.findArrangementTargets(id);
			if(arrangementTargets != null && arrangementTargets.size() > 0){
				result.put("code", "0");
				result.put("msg", "该基础作息时间有相关部门或人员使用不能删除");
			} else {
				baseArrangementDao.removeBaseArrangement(id);
				result.put("code", "1");
				result.put("msg", "删除基本作息时间成功");
			}
		} catch (Exception e) {
			logger.error("保删除基础排班信息失败,id:"+id,e);
			result.put("code", "0");
			result.put("msg", "删除基本作息时间失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> findSpecialArrangementList(String date, String arrangementId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> arrangementList = null;
		try {
			arrangementList = baseArrangementDao.findSpecialArrangementList(date, arrangementId);
			if (arrangementList == null) {
				result.put("code", "0");
				result.put("msg", "查询特殊排版列表失败");
				return result;
			} else if (arrangementList.size() > 0) {
				for (Map<String, Object> map : arrangementList) {
					String day = (map.get("date") + "").split("-")[2];
					map.put("day", day);
				}
			}
			result.put("code", "1");
			result.put("msg", "查询特殊排版列表成功");
			result.put("arrangementList", arrangementList);
		} catch (Exception e) {
			logger.error("查询特殊排版列表失败,arrangementId:"+arrangementId,e);
			result.put("code", "0");
			result.put("msg", "查询特殊排版列表失败");
		}
		return result;
	}

	/**
	 * 保存特殊排班信息
	 */
	@Override
	public Map<String, Object> saveSpecialArrangement(SpecialArrangementEntity specialArrangement) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (UtilDateTime.isLaterThanCurrentMonth(specialArrangement.getDate(), "yyyy-MM-dd")) {
				if("0".equals(specialArrangement.getIsAttend())){
					Map<String, Object> arrangement = baseArrangementDao.findBaseArrangementById(specialArrangement.getArrangementId());
					specialArrangement.setStartTime(arrangement.get("startTime") + "");
					specialArrangement.setEndTime(arrangement.get("endTime") + "");
				}
				if (UtilString.isEmpty(specialArrangement.getId())) {
					baseArrangementDao.addSpecialArrangement(specialArrangement);
				} else {
					baseArrangementDao.updateSpecialArrangement(specialArrangement);
				}
				result.put("code", "1");
				result.put("msg", "保存特殊排班成功");
			} else {
				result.put("code", "0");
				result.put("msg", "排班日期不能为当月或之前日期");
			}
		} catch (Exception e) {
			logger.error("保存特殊排班失败",e);
			result.put("code", "0");
			result.put("msg", "保存特殊排班失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> removeSpecialArrangement(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			baseArrangementDao.updateSpecialArrangement(id);
			result.put("code", "1");
			result.put("msg", "删除特殊排班成功");
		} catch (Exception e) {
			logger.error("删除特殊排班失败",e);
			result.put("code", "0");
			result.put("msg", "特殊排班不存在或删除失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> findSpecialArrangementById(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> arrangement = null;
		try {
			arrangement = baseArrangementDao.findSpecialArrangementById(id);
			result.put("code", "1");
			result.put("msg", "查询特殊排班成功");
			result.put("arrangement", arrangement);
		} catch (Exception e) {
			logger.error("根据基础作息时间Id查询特殊排班失败",e);
			result.put("code", "0");
			result.put("msg", "查询特殊排班失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> findSpecialArrangementList(String date, String deptCode, String pNumber) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> arrangementList = null;
		try {
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
		} catch (Exception e) {
			logger.error("根据部门或人员编号查询特殊排班失败",e);
			result.put("code", "0");
			result.put("msg", "查询特殊排版列表失败");
		}
		return result;
	}
	
	public Map<String, Object> getArrangementId(String deptCode, String pNumber) {
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
				// if(!UtilString.isEmpty(person.getP_company_id())){
				// targetIds.add(person.getP_company_id());
				// }
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
				return arrangementList.get(0);
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> findSpecialArrangementId(String deptCode, String pNumber) {
		return getArrangementId(deptCode,pNumber);
	}

	@Transactional(rollbackFor=Exception.class)//TODO 事务无效
	@Override
	public Map<String, Object> bindArrangement(String targetId, String type, String arrangementId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			baseArrangementDao.deleteSettedArrangement(targetId);
//			String aString = null;
//			aString.length();
			baseArrangementDao.bindArrangement(targetId, type, arrangementId);
			result.put("code", "1");
			result.put("msg", "绑定排班成功");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "绑定排班失败");
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> findCurrentMonthAllSpecialArrangement(String start ,String endTime) {
		return baseArrangementDao.findCurrentMonthAllSpecialArrangement(start ,endTime);
	}

	@Override
	public List<Map<String, Object>> findSpecialArrangementOfMonth(String year, String month) {
		if(month.length() == 1){
			month = "0"+month;
		}
		int days = UtilDateTime.getDaysOfCurrentMonth(Integer.valueOf(year), Integer.valueOf(month));
		String startDay = year + "-" + month + "-01";
		String endDay = year + "-" + month + days;
		return baseArrangementDao.findSpecialArrangementOfMonth(startDay, endDay);
	}

}
