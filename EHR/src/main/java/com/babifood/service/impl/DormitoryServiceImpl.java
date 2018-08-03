package com.babifood.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.DormitoryDao;
import com.babifood.entity.DormitoryEntity;
import com.babifood.service.DormitoryService;
import com.babifood.utils.UtilString;

@Service
public class DormitoryServiceImpl implements DormitoryService {

	Logger log = LoggerFactory.getLogger(DormitoryServiceImpl.class);

	@Autowired
	private DormitoryDao dormitoryDao;

	@Override
	public Map<String, Object> saveDormitory(DormitoryEntity dormitory) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (dormitory.getId() != null) {
				Map<String, Object> dormitoryInfo = dormitoryDao.findDormitoryById(dormitory.getId() + "");
				if (dormitoryInfo == null || dormitoryInfo.size() <= 0) {
					log.error("该床位信息不存在，id:" + dormitory.getId());
					throw new Exception("数据异常");
				}
				putParam(dormitoryInfo, dormitory);
				dormitoryDao.updateDormitory(dormitoryInfo);
			} else {
				Map<String, Object> dormitoryInfo = dormitoryDao.findDormitoryInfo(dormitory.getFloor(),
						dormitory.getRoomNo(), dormitory.getBedNo());
				if(dormitoryInfo == null || dormitoryInfo.size() <= 0){
					dormitoryDao.addDormitory(dormitory);
					result.put("code", "1");
				} else {
					result.put("code", "0");
					result.put("msg", "该床位已存在，不能新建");
				}
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存信息失败");
		}
		return result;
	}

	private void putParam(Map<String, Object> dormitoryInfo, DormitoryEntity dormitory) {
		if (dormitory.getId() != null && !"".equals(dormitory.getId())) {
			dormitoryInfo.put("id", dormitory.getId());
		}
		if (dormitory.getFloor() != null && !"".equals(dormitory.getFloor())) {
			dormitoryInfo.put("floor", dormitory.getFloor());
		}
		if (dormitory.getRoomNo() != null && !"".equals(dormitory.getRoomNo())) {
			dormitoryInfo.put("roomNo", dormitory.getRoomNo());
		}
		if (dormitory.getBedNo() != null && !"".equals(dormitory.getBedNo())) {
			dormitoryInfo.put("bedNo", dormitory.getBedNo());
		}
		if (dormitory.getSex() != null && !"".equals(dormitory.getSex())) {
			dormitoryInfo.put("sex", dormitory.getSex());
		}
		if (dormitory.getRemark() != null && !"".equals(dormitory.getRemark())) {
			dormitoryInfo.put("remark", dormitory.getRemark());
		}
	}

	@Override
	public Map<String, Object> removeDormitory(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> dormitory = dormitoryDao.findDormitoryById(id);
			if (dormitory != null && !UtilString.isEmpty(dormitory.get("pNumber")+"")) {
				result.put("code", "0");
				result.put("msg", "该床位已有员工入住，不能删除");
			} else {
				dormitory.put("isDelete", "1");
				dormitoryDao.updateDormitory(dormitory);
				result.put("code", "1");
				result.put("msg", "删除床位信息成功");
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "删除床位信息失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> getUnStayDormitory(Integer page,Integer rows,String floor, String roomNo, String sex, String stay) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Integer pageNum = page == null? 1 : page <= 0 ? 1 : page;
		Integer pageSize = rows == null? 10 : rows <= 0 ? 10 : rows;
		params.put("start", (pageNum - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("floor", floor);
		params.put("roomNo", roomNo);
		params.put("sex", sex);
		params.put("stay", stay);
		try {
			int count = dormitoryDao.getCountOfUnStayDormitory(params);
			List<Map<String, Object>> dormitorys = dormitoryDao.getUnStayDormitory(params);
			result.put("code", "1");
			result.put("total", count);
			result.put("msg", "查询未入住床位信息成功");
			result.put("rows", dormitorys);
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询未入住床位信息失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> getStayDormitory(Integer page,Integer rows,String floor, String roomNo, String sex, String pNumber, String pName) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		Integer pageNum = page == null? 1 : page <= 0 ? 1 : page;
		Integer pageSize = rows == null? 10 : rows <= 0 ? 10 : rows;
		params.put("start", (pageNum - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("floor", floor);
		params.put("roomNo", roomNo);
		params.put("sex", sex);
		params.put("pNumber", pNumber);
		params.put("pName", pName);
		try {
			int count = dormitoryDao.getCountOfStayDormitory(params);
			List<Map<String, Object>> dormitorys = dormitoryDao.getStayDormitory(params);
			result.put("code", "1");
			result.put("total", count);
			result.put("msg", "查询已入住床位信息成功");
			result.put("rows", dormitorys);
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询已入住床位信息失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> cheakingDormitory(String dormitoryId, String pnumber, String stayTime) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> dormitory = dormitoryDao.findCheakingDormitory(dormitoryId, pnumber);
			if (dormitory != null && dormitory.size() > 0) {
				result.put("code", "0");
				result.put("msg", "该员工已入住宿舍，不能重新入住");
			} else {
				dormitoryDao.insertCheakingDormitory(dormitoryId, pnumber, stayTime);
				result.put("code", "1");
				result.put("msg", "办理入住成功");
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "办理入住失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> cheakoutDormitory(String dormitoryId, String pnumber) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			dormitoryDao.cheakoutDormitory(dormitoryId, pnumber);
			result.put("code", "1");
			result.put("msg", "办理退住宿舍成功");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "办理退住宿舍失败");
		}
		return result;
	}

}
