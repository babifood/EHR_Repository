package com.babifood.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.dao.DormitoryDao;
import com.babifood.dao.SysParamDao;
import com.babifood.entity.DormitoryCostEntity;
import com.babifood.entity.DormitoryEntity;
import com.babifood.entity.DormitoryStayEntiry;
import com.babifood.entity.LoginEntity;
import com.babifood.service.DormitoryService;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.ExcelUtil;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;

@Service
public class DormitoryServiceImpl implements DormitoryService {

	Logger log = LoggerFactory.getLogger(DormitoryServiceImpl.class);

	@Autowired
	private DormitoryDao dormitoryDao;
	
	@Autowired
	private SysParamDao sysParamDao;
	
	@Override
	public Map<String, Object> operateDormitory(DormitoryEntity dorm,DormitoryStayEntiry dormStay) {
		if(UtilString.isEmpty(dormStay.getPersonName()) && UtilString.isEmpty(dormStay.getpNumber())){
			return saveDormitory(dorm);
		} else if(UtilString.isEmpty(dormStay.getOutTime())){
			if(UtilString.isEmpty(dormStay.getpNumber())){
				dormStay.setpNumber("ls" + sysParamDao.getLastNumber("dorm_ls_checking_number"));
			}
			return cheakingDormitory(dormStay);
		} else {
			return cheakoutDormitory(dormStay, "0");
		}
	}
	

	/**
	 * 保存宿舍信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DORMITORY)
	public Map<String, Object> saveDormitory(DormitoryEntity dormitory) {
		Map<String, Object> result = new HashMap<String, Object>();
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		try {
			if (dormitory.getId() != null) {
				LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EDIT);
				Map<String, Object> dormitoryInfo = dormitoryDao.findDormitoryById(dormitory.getId() + "");
				if (dormitoryInfo == null || dormitoryInfo.size() <= 0) {
					log.error("该床位信息不存在，id:" + dormitory.getId());
					LogManager.putContectOfLogInfo("保存宿舍信息失败，错误信息：该床位信息不存在，id:" + dormitory.getId());
					throw new Exception("数据异常");
				}
				putParam(dormitoryInfo, dormitory);
				dormitoryDao.updateDormitory(dormitoryInfo);
			} else {
				LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
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
			LogManager.putContectOfLogInfo("保存宿舍信息");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存信息失败");
			log.error("保存宿舍信息失败", e);
			LogManager.putContectOfLogInfo("保存宿舍信息失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 参数转换
	 * @param dormitoryInfo
	 * @param dormitory
	 */
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

	/**
	 * 删除宿舍信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DORMITORY)
	public Map<String, Object> removeDormitory(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			Map<String, Object> dormitory = dormitoryDao.findDormitoryById(id);
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
			if (dormitory != null && !UtilString.isEmpty(dormitory.get("pNumber")+"")) {
				result.put("code", "0");
				result.put("msg", "该床位已有员工入住，不能删除");
				log.error("删除床位信息失败，错误信息：该床位已有员工入住，不能删除");
				LogManager.putContectOfLogInfo("删除床位信息失败，错误信息：该床位已有员工入住，不能删除");
			} else {
				dormitory.put("isDelete", "1");
				dormitoryDao.updateDormitory(dormitory);
				result.put("code", "1");
				result.put("msg", "删除床位信息成功");
				LogManager.putContectOfLogInfo("删除床位信息");
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "删除床位信息失败");
			log.error("保存宿舍信息失败,id="+id, e);
			LogManager.putContectOfLogInfo("删除床位信息失败,id=" + id + ",错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 查询未入住床位信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DORMITORY)
	public Map<String, Object> getUnStayDormitory(Integer page,Integer rows,String floor, String roomNo, String sex, String stay, String dormType) {
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
		params.put("dormType", dormType);
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			int count = dormitoryDao.getCountOfUnStayDormitory(params);
			List<Map<String, Object>> dormitorys = dormitoryDao.getUnStayDormitory(params);
			result.put("code", "1");
			result.put("total", count);
			result.put("msg", "查询未入住床位信息成功");
			result.put("rows", dormitorys);
			LogManager.putContectOfLogInfo("查询未入住床位信息");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询未入住床位信息失败");
			log.error("查询未入住床位信息失败", e);
			LogManager.putContectOfLogInfo("查询未入住床位信息失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 查询已入住床位信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DORMITORY)
	public Map<String, Object> getStayDormitory(Integer page,Integer rows,String floor, String roomNo, String sex, String pNumber, String pName, String dormType, String checkingStart, String checkingEnd, String checkoutStart, String checkoutEnd) {
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
		params.put("dormType", dormType);
		params.put("checkingStart", checkingStart);
		params.put("checkingEnd", checkingEnd);
		params.put("checkoutStart", checkoutStart);
		params.put("checkoutEnd", checkoutEnd);
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			int count = dormitoryDao.getCountOfStayDormitory(params);
			List<Map<String, Object>> dormitorys = dormitoryDao.getStayDormitory(params);
			result.put("code", "1");
			result.put("total", count);
			result.put("msg", "查询已入住床位信息成功");
			result.put("rows", dormitorys);
			LogManager.putContectOfLogInfo("查询已入住床位信息");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询已入住床位信息失败");
			log.error("查询已入住床位信息失败", e);
			LogManager.putContectOfLogInfo("查询已入住床位信息失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 员工入住
	 */
	@LogMethod(module = ModuleConstant.DORMITORY)
	public Map<String, Object> cheakingDormitory(DormitoryStayEntiry dormStay) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_CHECKING);
			Map<String, Object> dormitory = dormitoryDao.findCheakingDormitory(dormStay.getpNumber());
			if (dormitory != null && dormitory.size() > 0) {
				result.put("code", "0");
				result.put("msg", "该员工已入住宿舍，不能重新入住");
				LogManager.putContectOfLogInfo("员工入住失败，错误信息：该员工已入住宿舍，不能重新入住");
			} else {
				dormitoryDao.insertCheakingDormitory(dormStay);
				result.put("code", "1");
				result.put("msg", "办理入住成功");
				LogManager.putContectOfLogInfo("员工入住");
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "办理入住失败");
			log.error("办理入住失败", e);
			LogManager.putContectOfLogInfo("员工入住失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 员工搬出宿舍
	 */
	@Override
	@LogMethod(module = ModuleConstant.DORMITORY)
	public Map<String, Object> cheakoutDormitory(DormitoryStayEntiry dormStay, String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_CHECKOUT);
			if("1".equals(type)){
				dormitoryDao.cheakoutDormitory(dormStay.getDormitoryId() + "", dormStay.getpNumber());
				LogManager.putContectOfLogInfo("员工搬出宿舍--实际搬出");
			} else {
				dormitoryDao.moveOutProcedure(dormStay.getDormitoryId() + "", dormStay.getpNumber(), dormStay.getOutTime());
				LogManager.putContectOfLogInfo("员工入住--办理手续");
			}
			result.put("code", "1");
			result.put("msg", "办理退住宿舍成功");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "办理退住宿舍失败");
			log.error("办理退住宿舍失败", e);
			LogManager.putContectOfLogInfo("办理退住宿舍失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 分页查询宿舍费用信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DORMITORY)
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
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
			int count = dormitoryDao.queryDormitoryCostCount(params);
			List<Map<String, Object>> costList = dormitoryDao.queryDormitoryCostList(params);
			result.put("code", "1");
			result.put("total", count);
			result.put("rows", costList);
			LogManager.putContectOfLogInfo("员工入住--办理手续");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询宿舍费用信息");
			log.error("查询宿舍费用信息失败", e);
			LogManager.putContectOfLogInfo("查询宿舍费用信息失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 保存宿舍费用信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DORMITORY)
	public Map<String, Object> saveCost(DormitoryCostEntity dormitoryCost) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			if(dormitoryCost.getId() != null){
				LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EDIT);
			} else {
				LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
			}
			dormitoryDao.saveCost(dormitoryCost);
			result.put("code", "1");
			result.put("msg", "保存住宿费用信息成功");
			LogManager.putContectOfLogInfo("保存宿舍费用信息");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存住宿费用信息失败");
			log.error("保存宿舍费用信息失败", e);
			LogManager.putContectOfLogInfo("保存宿舍费用信息失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 删除住宿费用信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DORMITORY)
	public Map<String, Object> removeCost(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
			dormitoryDao.removeCost(id);
			result.put("code", "1");
			result.put("msg", "删除住宿费用信息成功");
			LogManager.putContectOfLogInfo("保存宿舍费用信息");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "删除住宿费用信息失败");
			log.error("删除住宿费用信息失败,id=" + id, e);
			LogManager.putContectOfLogInfo("删除住宿费用信息失败,id=" + id + "，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 导出宿舍费用信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DORMITORY)
	public Map<String, Object> exportDormitoryCosts(OutputStream ouputStream, String type, String year, String month) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> row1Name = getRow1Name();
		String[] sort = new String[] { "year", "month", "pNumber", "pName", "floor", "roomNo", "bedNo", "dormBonus",
				"dormFee", "electricityFee", "dormDeduction" };
		List<Map<String, Object>> dormitoryCosts = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
			if("1".equals(type)){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("month", UtilString.isEmpty(month) ? UtilDateTime.getPreMonth() : month);
				param.put("year", UtilString.isEmpty(year) ? UtilDateTime.getYearOfPreMonth() : year);
				dormitoryCosts = dormitoryDao.queryDormitoryCostList(param);
				LogManager.putContectOfLogInfo("导出宿舍费用信息");
			} else {
				dormitoryCosts = new ArrayList<Map<String, Object>>();
				LogManager.putContectOfLogInfo("下载宿舍费用信息excel模板");
			}
			ExcelUtil.exportExcel("住宿费项列表", row1Name, dormitoryCosts, ouputStream, sort);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "导出excel失败");
			log.error("导出宿舍费用信息失败", e);
			LogManager.putContectOfLogInfo("导出宿舍费用信息失败,错误信息：" + e.getMessage());
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
		row1Name.put("dormFee", "住宿费用");
		row1Name.put("electricityFee", "宿舍电费");
		row1Name.put("dormBonus", "宿舍奖励");
		row1Name.put("dormDeduction", "宿舍扣款");
		return row1Name;
	}
	
	/**
	 * 导入宿舍费用信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.DORMITORY)
	public Map<String, Object> importDormitoryCost(MultipartFile file, String type) {
		Map<String, Object> result = new HashMap<>();
		Map<String, String> row1Name = getImportRow1Name();
		List<Map<String, Object>> values = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_IMPORT);
			values = ExcelUtil.importExcel(file, row1Name);
			if(values != null && values.size() > 0){
				Map<String, Object> user = checkValues(values);
				if(user == null || user.size() <= 0){
					List<Object[]> dormitoryCostParam = getDormitoryCostParam(values);
					dormitoryDao.saveDormitoryCosts(dormitoryCostParam);
					result.put("code", "1");
					result.put("msg", "导入数据成功");
				} else {
					result.put("code", "0");
					result.put("msg", "导入数据失败，"+user.get("pName") + ",工号"+user.get("pNumber")+"不是权限范围内员工");
				}
			} else {
				result.put("code", "1");
				result.put("msg", "导入数据成功");
			}
			LogManager.putContectOfLogInfo("导入宿舍费用信息");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", "excel数据异常，导入数据失败");
			log.error("导入宿舍费用信息失败,excel数据异常", e);
			LogManager.putContectOfLogInfo("导入宿舍费用信息失败,excel数据异常,错误信息：" + e.getMessage());
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存导入数据异常，导入数据失败");
			log.error("导入宿舍费用信息失败,保存导入数据异常", e);
			LogManager.putContectOfLogInfo("导入宿舍费用信息失败,保存导入数据异常,错误信息：" + e.getMessage());
		}
		return result;
	}
	
	public Map<String, Object> checkValues(List<Map<String, Object>> values) {
		Map<String, Object> user = new HashMap<String, Object>();
		List<Map<String, Object>> auths = dormitoryDao.loadUserDataAuthority();
		if(auths != null && auths.size() > 0){
			List<String> pNumberList = dormitoryDao.findPNumberList(auths);
			for(Map<String, Object> map : values){
				if(!pNumberList.contains(map.get("pNumber")+"")){
					user = map;
					break;
				}
			}
		}
		return user;
	}

	private List<Object[]> getDormitoryCostParam(List<Map<String, Object>> values) {
		List<Object[]> objList = new ArrayList<>();
		if (values != null && values.size() > 0) {
			for (Map<String, Object> map : values) {
				Object[] obj = new Object[] { map.get("year"), map.get("month"), map.get("pNumber"),
						BASE64Util.getDecodeStringTowDecimal(map.get("dormFee")),
						BASE64Util.getDecodeStringTowDecimal(map.get("electricityFee")),
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
		row1Name.put("住宿费用", "dormFee");
		row1Name.put("宿舍电费", "electricityFee");
		row1Name.put("宿舍奖励", "dormBonus");
		row1Name.put("宿舍扣款", "dormDeduction");
		return row1Name;
	}


	@Override
	public Map<String, Object> exportRecord(OutputStream ouputStream, String checkingStart, String checkingEnd,
			String checkoutStart, String checkoutEnd) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> row1Name = getRecordRow1Name();
		String[] sort = new String[] { "pNumber", "pName", "phone", "identity", "floor1", "roomNo", "bedNo", "dormitorType",
				"type1", "stayTime", "outTime", "remark" };
		List<Map<String, Object>> dormitoryCosts = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("checkingStart", checkingStart);
			param.put("checkingEnd", checkingEnd);
			param.put("checkoutStart", checkoutStart);
			param.put("checkoutEnd", checkoutEnd);
			param.put("start", 0);
			param.put("pageSize", 10000);
			dormitoryCosts = dormitoryDao.getStayDormitory(param);
			LogManager.putContectOfLogInfo("导出住宿记录信息");
			ExcelUtil.exportExcel("住宿记录", row1Name, dormitoryCosts, ouputStream, sort);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "导出excel失败");
			log.error("导出宿舍费用信息失败", e);
			LogManager.putContectOfLogInfo("导出宿舍费用信息失败,错误信息：" + e.getMessage());
		}
		return result;
	}


	private Map<String, String> getRecordRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("phone", "手机号码");
		row1Name.put("identity", "身份证号");
		row1Name.put("pNumber", "员工工号");
		row1Name.put("pName", "员工姓名");
		row1Name.put("floor1", "楼层");
		row1Name.put("roomNo", "房间号");
		row1Name.put("bedNo", "床位号");
		row1Name.put("type1", "住宿类型");
		row1Name.put("dormitorType", "宿舍类型");
		row1Name.put("stayTime", "入住时间");
		row1Name.put("outTime", "搬出时间");
		row1Name.put("remark", "备注");
		return row1Name;
	}

}
