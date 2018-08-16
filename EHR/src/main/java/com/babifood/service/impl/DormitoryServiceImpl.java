package com.babifood.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.babifood.dao.DormitoryDao;
import com.babifood.entity.DormitoryCostEntity;
import com.babifood.entity.DormitoryEntity;
import com.babifood.service.DormitoryService;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.ExcelUtil;
import com.babifood.utils.UtilDateTime;
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
			Map<String, Object> dormitory = dormitoryDao.findCheakingDormitory(pnumber);
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
	public Map<String, Object> cheakoutDormitory(String dormitoryId, String pnumber, String outTime, String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if("1".equals(type)){
				dormitoryDao.cheakoutDormitory(dormitoryId, pnumber);
			} else {
				dormitoryDao.moveOutProcedure(dormitoryId, pnumber, outTime);
			}
			result.put("code", "1");
			result.put("msg", "办理退住宿舍成功");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "办理退住宿舍失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> queryDormitoryCostList(Integer page, Integer rows, String floor, String roomNo,
			String pNumber, String pName) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Integer pageNum = page == null? 1 : page <= 0 ? 1 : page;
		Integer pageSize = rows == null? 10 : rows <= 0 ? 10 : rows;
		params.put("start", (pageNum - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("floor", floor);
		params.put("roomNo", roomNo);
		params.put("pNumber", pNumber);
		params.put("pName", pName);
		try {
			int count = dormitoryDao.queryDormitoryCostCount(params);
			List<Map<String, Object>> costList = dormitoryDao.queryDormitoryCostList(params);
			result.put("code", "1");
			result.put("total", count);
			result.put("rows", costList);
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询住宿费用信息失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> saveCost(DormitoryCostEntity dormitoryCost) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			dormitoryDao.saveCost(dormitoryCost);
			result.put("code", "1");
			result.put("msg", "保存住宿费用信息成功");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存住宿费用信息失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> removeCost(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			dormitoryDao.removeCost(id);
			result.put("code", "1");
			result.put("msg", "删除住宿费用信息成功");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "删除住宿费用信息失败");
		}
		return result;
	}

	@Override
	public Map<String, Object> exportDormitoryCosts(OutputStream ouputStream, String type, String year, String month) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> row1Name = getRow1Name();
		String[] sort = new String[]{"year", "month", "pNumber", "pName", "floor", "roomNo", "bedNo", "dormBonus", "dormDeduction"};
		List<Map<String, Object>> dormitoryCosts = null;
		try {
			if("1".equals(type)){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("month", UtilString.isEmpty(month) ? UtilDateTime.getPreMonth() : month);
				param.put("year", UtilString.isEmpty(year) ? UtilDateTime.getYearOfPreMonth() : year);
				dormitoryCosts = dormitoryDao.queryDormitoryCostList(param);
			} else {
				dormitoryCosts = new ArrayList<Map<String, Object>>();
			}
			ExcelUtil.exportExcel("住宿费项列表", row1Name, dormitoryCosts, ouputStream, sort);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "导出excel失败");
		}
		return result;
	}

	private Map<String, String> getRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("year", "年");
		row1Name.put("month", "月");
		row1Name.put("pNumber", "员工工号");
		row1Name.put("pName", "员工姓名");
		row1Name.put("floor", "楼层");
		row1Name.put("roomNo", "房间号");
		row1Name.put("bedNo", "床位号");
		row1Name.put("dormBonus", "宿舍奖励");
		row1Name.put("dormDeduction", "住宿扣款");
		return row1Name;
	}
	
	@Override
	public Map<String, Object> importDormitoryCost(CommonsMultipartFile file, String type) {
		Map<String, Object> result = new HashMap<>();
		Map<String, String> row1Name = getImportRow1Name();
		List<Map<String, Object>> values = null;
		try {
			values = ExcelUtil.importExcel(file, row1Name);
			if(values != null && values.size() > 0){
				List<Object[]> dormitoryCostParam = getDormitoryCostParam(values);
				dormitoryDao.saveDormitoryCosts(dormitoryCostParam);
			}
			result.put("code", "1");
			result.put("msg", "导入数据成功");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", "excel数据异常，导入数据失败");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存导入数据异常，导入数据失败");
		}
		return result;
	}

	private List<Object[]> getDormitoryCostParam(List<Map<String, Object>> values) {
		List<Object[]> objList = new ArrayList<>();
		if (values != null && values.size() > 0) {
			for (Map<String, Object> map : values) {
				Object[] obj = new Object[] { map.get("year"), map.get("month"), map.get("pNumber"),
						BASE64Util.getDecodeStringTowDecimal(map.get("dormBonus")),
						BASE64Util.getDecodeStringTowDecimal(map.get("dormDeduction")) };
				objList.add(obj);
			}
		}
		return objList;
	}

	private Map<String, String> getImportRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("年", "year");
		row1Name.put("月", "month");
		row1Name.put("员工工号", "pNumber");
		row1Name.put("员工姓名", "pName");
		row1Name.put("楼层", "floor");
		row1Name.put("房间号", "roomNo");
		row1Name.put("床位号", "bedNo");
		row1Name.put("宿舍奖励", "dormBonus");
		row1Name.put("住宿扣款", "dormDeduction");
		return row1Name;
	}
	
}
