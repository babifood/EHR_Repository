package com.babifood.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.babifood.constant.OperationConstant;
import com.babifood.dao.AllowanceDao;
import com.babifood.entity.LoginEntity;
import com.babifood.service.AllowanceService;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.ExcelUtil;
import com.babifood.utils.UtilString;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;

@Service
public class AllowanceServiceImpl implements AllowanceService {

	@Autowired
	private AllowanceDao allowanceDao;
	
	@Override
	public Map<String, Object> findEmployAllowance(String year, String month, String pNumber) {
		Map<String,	Object> param = new HashMap<String, Object>();
		param.put("year", year);
		param.put("pNumber", pNumber);
		param.put("month", month);
		List<Map<String, Object>> employAllowance = allowanceDao.findEmployAllowance(param);
		BASE64Util.Base64DecodeMap(employAllowance);
		if (employAllowance != null && employAllowance.size() > 0) {
			return employAllowance.get(0);
		}
		return null;
	}

	/**
	 * 导入Excel数据
	 */
	@Override
	public Map<String, Object> importExcel(MultipartFile file, String type) {
		Map<String, Object> result = new HashMap<>();
		Map<String, String> row1Name = getRow1Name();
		List<Map<String, Object>> values = null;
		try {
			values = ExcelUtil.importExcel(file, row1Name);
			if ("1".equals(type)) {//覆盖导入
				getEmployAllowanceParamCover(values);
			} else {//忽略导入
				getEmployAllowanceParamIgnore(values);
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

	/**
	 * 对应Excel 第一行数据
	 * @return
	 */
	private Map<String, String> getRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("年", "year");
		row1Name.put("月", "month");
		row1Name.put("员工工号", "pNumber");
		row1Name.put("员工姓名", "pName");
		row1Name.put("高温津贴", "highTem");
		row1Name.put("低温津贴", "lowTem");
		row1Name.put("夜班津贴", "nightShift");
		row1Name.put("早班津贴", "morningShift");
		row1Name.put("驻外/住宿津贴", "stay");
		row1Name.put("其它补贴", "otherAllowance");
		row1Name.put("绩效奖金", "performanceBonus");
		row1Name.put("其它奖励", "otherBonus");
		row1Name.put("礼金/补偿金", "compensatory");
		row1Name.put("安全奖金", "security");
		row1Name.put("加其它", "addOther");
		row1Name.put("餐补扣除", "mealDeduction");
		row1Name.put("宿舍扣款", "dormDeduction");
		row1Name.put("加班工资", "overSalary");
		row1Name.put("代缴社保", "insurance");
		row1Name.put("其它扣款（税前）", "beforeDeduction");
		row1Name.put("公积金", "providentFund");
		row1Name.put("其它扣款（税后）", "afterOtherDeduction");
		row1Name.put("预留字段1", "reserved1");
		row1Name.put("预留字段2", "reserved2");
		row1Name.put("预留字段3", "reserved3");
		row1Name.put("预留字段4", "reserved4");
		row1Name.put("预留字段5", "reserved5");
		row1Name.put("预留字段6", "reserved6");
		row1Name.put("预留字段7", "reserved7");
		row1Name.put("预留字段8", "reserved8");
		row1Name.put("预留字段9", "reserved9");
		row1Name.put("预留字段10", "reserved10");
		return row1Name;
	}

	/**
	 * 忽略重复数据导入
	 * @param values
	 */
	private void getEmployAllowanceParamIgnore(List<Map<String, Object>> values) {
		List<Object[]> allowanceValues = new ArrayList<Object[]>();
		Map<String, Object> value = values.get(0);
		Map<String,	Object> param = new HashMap<String, Object>();
		param.put("year", value.get("year"));
		param.put("month", value.get("month"));
		List<Map<String, Object>> allowanceExist = allowanceDao.findEmployAllowance(param);
		for (Map<String, Object> allowance : values) {
			Map<String, Object> map = null;
			for (Map<String, Object> allowanceMap : allowanceExist) {
				if ((allowance.get("pNumber") + "").equals(allowanceMap.get("pNumber") + "")) {
					map = allowanceMap;
					allowanceExist.remove(allowanceMap);
					break;
				}
			}
			if(map == null){
				Object[] object = getObjectParam(allowance, null);
				allowanceValues.add(object);
			}
		}
		allowanceDao.saveEmployAllowances(allowanceValues);
	}

	/**
	 * 更新重复条目导入
	 * @param values
	 */
	private void getEmployAllowanceParamCover(List<Map<String, Object>> values) {
		List<Object[]> allowanceValues = new ArrayList<Object[]>();
		Map<String, Object> value = values.get(0);
		Map<String,	Object> param = new HashMap<String, Object>();
		param.put("year", value.get("year"));
		param.put("month", value.get("month"));
		List<Map<String, Object>> allowanceExist = allowanceDao.findEmployAllowance(param);
		for (Map<String, Object> allowance : values) {
			Map<String, Object> map = null;
			for (Map<String, Object> allowanceMap : allowanceExist) {
				if ((allowance.get("pNumber") + "").equals(allowanceMap.get("pNumber") + "")) {
					map = allowanceMap;
					allowanceExist.remove(allowanceMap);
					break;
				}
			}
			Object[] object = getObjectParam(allowance, map);
			allowanceValues.add(object);
		}
		allowanceDao.saveEmployAllowances(allowanceValues);
	}

	/**
	 * 获取插入参数
	 * @param allowance
	 * @param map
	 * @return
	 */
	private Object[] getObjectParam(Map<String, Object> allowance, Map<String, Object> map) {
		return new Object[] {
			UtilString.isEmpty(allowance.get("year") + "") ? (map == null ? "" : map.get("year")) : allowance.get("year"),
			UtilString.isEmpty(allowance.get("month") + "") ? (map == null ? "" : map.get("month")) : (allowance.get("month") + "").length() == 1 ?  "0" + allowance.get("month") : allowance.get("month"),
			UtilString.isEmpty(allowance.get("pNumber") + "") ? (map == null ? "" : map.get("pNumber")) : allowance.get("pNumber"),
//			UtilString.isEmpty(allowance.get("pName") + "") ? (map == null ? "" : map.get("pName")) : allowance.get("pName"),
			UtilString.isEmpty(allowance.get("overSalary") + "") ? (map == null ? "" : map.get("overSalary")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("overSalary")+""),
			UtilString.isEmpty(allowance.get("highTem") + "") ? (map == null ? "" : map.get("highTem")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("highTem")+""),
			UtilString.isEmpty(allowance.get("lowTem") + "") ? (map == null ? "" : map.get("lowTem")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("lowTem")+""),
			UtilString.isEmpty(allowance.get("nightShift") + "") ? (map == null ? "" : map.get("nightShift")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("nightShift")+""),
			UtilString.isEmpty(allowance.get("morningShift") + "") ? (map == null ? "" : map.get("morningShift")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("morningShift")+""),
			UtilString.isEmpty(allowance.get("stay") + "") ? (map == null ? "" : map.get("stay")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("stay")+""),
			UtilString.isEmpty(allowance.get("otherAllowance") + "") ? (map == null ? "" : map.get("otherAllowance")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("otherAllowance")+""),
			UtilString.isEmpty(allowance.get("performanceBonus") + "") ? (map == null ? "" : map.get("performanceBonus")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("performanceBonus")+""),
			UtilString.isEmpty(allowance.get("security") + "") ? (map == null ? "" : map.get("security")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("security")+""),
			UtilString.isEmpty(allowance.get("compensatory") + "") ? (map == null ? "" : map.get("compensatory")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("compensatory")+""),
			UtilString.isEmpty(allowance.get("otherBonus") + "") ? (map == null ? "" : map.get("otherBonus")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("otherBonus")+""),
			UtilString.isEmpty(allowance.get("addOther") + "") ? (map == null ? "" : map.get("addOther")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("addOther")+""),
			UtilString.isEmpty(allowance.get("mealDeduction") + "") ? (map == null ? "" : map.get("mealDeduction")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("mealDeduction")+""),
			UtilString.isEmpty(allowance.get("dormDeduction") + "") ? (map == null ? "" : map.get("dormDeduction")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("dormDeduction")+""),
			UtilString.isEmpty(allowance.get("beforeDeduction") + "") ? (map == null ? "" : map.get("beforeDeduction")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("beforeDeduction")+""),
			UtilString.isEmpty(allowance.get("insurance") + "") ? (map == null ? "" : map.get("insurance")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("insurance")+""),
			UtilString.isEmpty(allowance.get("providentFund") + "") ? (map == null ? "" : map.get("providentFund")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("providentFund")+""),
			UtilString.isEmpty(allowance.get("afterOtherDeduction") + "") ? (map == null ? "" : map.get("afterOtherDeduction")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("afterOtherDeduction")+""),
			UtilString.isEmpty(allowance.get("reserved1") + "") ? (map == null ? "" : map.get("reserved1")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved1")+""),
			UtilString.isEmpty(allowance.get("reserved2") + "") ? (map == null ? "" : map.get("reserved2")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved2")+""),
			UtilString.isEmpty(allowance.get("reserved3") + "") ? (map == null ? "" : map.get("reserved3")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved3")+""),
			UtilString.isEmpty(allowance.get("reserved4") + "") ? (map == null ? "" : map.get("reserved4")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved4")+""),
			UtilString.isEmpty(allowance.get("reserved5") + "") ? (map == null ? "" : map.get("reserved5")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved5")+""),
			UtilString.isEmpty(allowance.get("reserved6") + "") ? (map == null ? "" : map.get("reserved6")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved6")+""),
			UtilString.isEmpty(allowance.get("reserved7") + "") ? (map == null ? "" : map.get("reserved7")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved7")+""),
			UtilString.isEmpty(allowance.get("reserved8") + "") ? (map == null ? "" : map.get("reserved8")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved8")+""),
			UtilString.isEmpty(allowance.get("reserved9") + "") ? (map == null ? "" : map.get("reserved9")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved9")+""),
			UtilString.isEmpty(allowance.get("reserved10") + "") ? (map == null ? "" : map.get("reserved10")) : BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved10")+"")
		};
	}

	
	/**
	 * 导出Excel数据
	 * @throws Exception 
	 */
	@Override
	public void exportExcel(OutputStream ouputStream, String type) throws Exception {
		Map<String, String> row1Name = getExportRow1Name();
		String[] sort = new String[] { "year", "month", "pNumber", "pName", "highTem", "lowTem", "nightShift",
				"morningShift", "otherAllowance",  "otherBonus", "compensatory", "security",
				"addOther", "overSalary", "insurance", "beforeDeduction",
				"providentFund", "afterOtherDeduction", "reserved1", "reserved2", "reserved3", "reserved4", "reserved5",
				"reserved6", "reserved7", "reserved8", "reserved9", "reserved10" };
		List<Map<String, Object>> dataSource = null;
		if("0".equals(type)){//模板
			dataSource = new ArrayList<Map<String, Object>>();
		} else {//数据
			dataSource = allowanceDao.findEmployAllowance(new HashMap<String, Object>());
			BASE64Util.Base64DecodeMap(dataSource);
		}
		ExcelUtil.exportExcel("部门信息列表", row1Name, dataSource, ouputStream, sort);
	}

	private Map<String, String> getExportRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("year", "年");
		row1Name.put("month", "月");
		row1Name.put("pNumber", "员工工号");
		row1Name.put("pName", "员工姓名");
		row1Name.put("highTem", "高温津贴");
		row1Name.put("lowTem", "低温津贴");
		row1Name.put("nightShift", "夜班津贴");
		row1Name.put("morningShift", "早班津贴");
		row1Name.put("stay", "驻外/住宿津贴");
		row1Name.put("otherAllowance", "其它补贴");
		row1Name.put("performanceBonus", "绩效奖金");
		row1Name.put("otherBonus", "其它奖励");
		row1Name.put("compensatory", "礼金/补偿金");
		row1Name.put("security", "安全奖金");
		row1Name.put("addOther", "加其它");
		row1Name.put("mealDeduction", "餐补扣除");
		row1Name.put("dormDeduction", "宿舍扣款");
		row1Name.put("overSalary", "加班工资");
		row1Name.put("insurance", "宿舍扣款");
		row1Name.put("beforeDeduction", "其它扣款（税前）");
		row1Name.put("providentFund", "公积金");
		row1Name.put("afterOtherDeduction", "其它扣款（税后）");
		row1Name.put("reserved1", "预留字段1");
		row1Name.put("reserved2", "预留字段2");
		row1Name.put("reserved3", "预留字段3");
		row1Name.put("reserved4", "预留字段4");
		row1Name.put("reserved5", "预留字段5");
		row1Name.put("reserved6", "预留字段6");
		row1Name.put("reserved7", "预留字段7");
		row1Name.put("reserved8", "预留字段8");
		row1Name.put("reserved9", "预留字段9");
		row1Name.put("reserved10", "预留字段10");
		return row1Name;
	}

	@LogMethod
	@Override
	public Map<String, Object> getPageAllowanceList(Integer page, Integer rows, String pNumber, String pName,
			String organzationName, String deptName, String officeName) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String,	Object> param = new HashMap<String, Object>();
		Integer pageNum = page == null ? 1 : page;
		Integer pageSize = rows == null ? 10 : rows;
		param.put("start", (pageNum - 1) * pageSize);
		param.put("pageSize", pageSize);
		param.put("number", pNumber);
		param.put("pName", pName);
		param.put("organzationName", organzationName);
		param.put("deptName", deptName);
		param.put("officeName", officeName);
		try {
			Integer total = allowanceDao.getAllowanceCount(param);
			List<Map<String, Object>> employAllowance = allowanceDao.findEmployAllowance(param);
			BASE64Util.Base64DecodeMap(employAllowance);
			result.put("total", total);
			result.put("rows", employAllowance);
			result.put("code", "1");
			LogManager.putContectOfLogInfo(param.toString());
		} catch (Exception e) {
			LogManager.putContectOfLogInfo(e.getMessage());
			result.put("code", "0");
			result.put("msg", "分页查询数据失败");
		}
		return result;
	}
	
}
