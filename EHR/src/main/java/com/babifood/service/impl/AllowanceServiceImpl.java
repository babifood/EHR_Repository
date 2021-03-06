package com.babifood.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.dao.AllowanceDao;
import com.babifood.entity.LoginEntity;
import com.babifood.service.AllowanceService;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.ExcelUtil;
import com.babifood.utils.UtilString;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;

/**
 * 津贴、扣款数据操作类
 * @author wangguocheng
 *
 */
@Service
public class AllowanceServiceImpl implements AllowanceService {

	private static Logger logger = Logger.getLogger(AllowanceServiceImpl.class);
	
	@Autowired
	private AllowanceDao allowanceDao;

	/**
	 * 查询员工津贴、奖金、扣款信息
	 */
	@Override
	@LogMethod(module = ModuleConstant.ALLOWANCE)
	public Map<String, Object> findEmployAllowance(String year, String month, String pNumber) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		Map<String,	Object> param = new HashMap<String, Object>();
		param.put("year", year);
		param.put("pNumber", pNumber);
		param.put("month", month);
		try {
			List<Map<String, Object>> employAllowance = allowanceDao.findEmployAllowance(param);
			LogManager.putContectOfLogInfo("查询员工津贴、奖金、扣款信息");
			BASE64Util.Base64DecodeMap(employAllowance);
			if (employAllowance != null && employAllowance.size() > 0) {
				return employAllowance.get(0);
			}
		} catch (Exception e) {
			logger.error("查询员工津贴/扣款的导入信息失败",e);
			LogManager.putContectOfLogInfo("查询员工津贴、奖金、扣款信息失败，错误信息：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 导入Excel数据
	 */
	@Override
	@LogMethod(module = ModuleConstant.ALLOWANCE)
	public Map<String, Object> importExcel(MultipartFile file, String type) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_IMPORT);
		Map<String, Object> result = new HashMap<>();
		Map<String, String> row1Name = getRow1Name();
		List<Map<String, Object>> values = null;
		try {
			values = ExcelUtil.importExcel(file, row1Name);
			if(values != null && values.size() > 0){
				Map<String, Object> user = checkValues(values);
				if(user == null || user.size() <= 0){
//					if ("1".equals(type)) {//覆盖导入
//						getEmployAllowanceParamCover(values);
//					} else {//忽略导入
						getEmployAllowanceParamIgnore(values);
//					}
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
		} catch (IOException e) {
			result.put("code", "0");
			result.put("msg", "excel数据异常，导入数据失败");
			logger.error("导入员工津贴/扣款信息失败",e);
			LogManager.putContectOfLogInfo("导入员工津贴/扣款信息失败:"+e.getMessage());
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存导入数据异常，导入数据失败");
			logger.error("保存员工津贴/扣款信息失败",e);
			LogManager.putContectOfLogInfo("保存员工津贴/扣款信息失败:"+e.getMessage());
		}
		return result;
	}

	public Map<String, Object> checkValues(List<Map<String, Object>> values) {
		Map<String, Object> user = new HashMap<String, Object>();
		List<Map<String, Object>> auths = allowanceDao.loadUserDataAuthority();
		if(auths != null && auths.size() > 0){
			List<String> pNumberList = allowanceDao.findPNumberList(auths);
			for(Map<String, Object> map : values){
				if(!pNumberList.contains(map.get("pNumber")+"")){
					user = map;
					break;
				}
			}
		}
		return user;
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
		row1Name.put("高温津贴(标准)", "highTem");
		row1Name.put("低温津贴(标准)", "lowTem");
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
	@LogMethod(module = ModuleConstant.ALLOWANCE)
	private void getEmployAllowanceParamIgnore(List<Map<String, Object>> values) {
		List<Object[]> allowanceValues = new ArrayList<Object[]>();
		Map<String, Object> value = values.get(0);
		Map<String,	Object> param = new HashMap<String, Object>();
		param.put("year", value.get("year"));
		param.put("month", value.get("month"));
//		List<Map<String, Object>> allowanceExist = allowanceDao.findEmployAllowance(param);
		for (Map<String, Object> allowance : values) {
//			Map<String, Object> map = null;
//			for (Map<String, Object> allowanceMap : allowanceExist) {
//				if ((allowance.get("pNumber") + "").equals(allowanceMap.get("pNumber") + "")) {
//					map = allowanceMap;
//					allowanceExist.remove(allowanceMap);
//					break;
//				}
//			}
//			if(map == null){
				Object[] object = getObjectParam(allowance, null);
				allowanceValues.add(object);
//			}
		}
		allowanceDao.saveEmployAllowances(allowanceValues);
		LogManager.putContectOfLogInfo("追加导入员工津贴/扣款信息,导入条数：" + allowanceValues.size());
	}

	/**
	 * 更新重复条目导入
	 * @param values
	 */
	@LogMethod(module = ModuleConstant.ALLOWANCE)
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
		LogManager.putContectOfLogInfo("覆盖导入员工津贴/扣款信息，导入条数：" + allowanceValues.size());
	}

	/**
	 * 获取插入参数
	 * @param allowance
	 * @param map
	 * @return
	 */
	private Object[] getObjectParam(Map<String, Object> allowance, Map<String, Object> map) {
		return new Object[] {
			allowance.get("year") ,
			(allowance.get("month") + "").length() == 1 ?  "0" + allowance.get("month") : allowance.get("month"),
			allowance.get("pNumber"),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("overSalary")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("highTem")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("lowTem")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("nightShift")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("morningShift")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("stay")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("otherAllowance")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("performanceBonus")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("security")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("compensatory")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("otherBonus")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("addOther")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("mealDeduction")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("dormDeduction")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("beforeDeduction")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("insurance")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("providentFund")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("afterOtherDeduction")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved1")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved2")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved3")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved4")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved5")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved6")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved7")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved8")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved9")+""),
			BASE64Util.getDecodeStringTowDecimal(allowance.get("reserved10")+"")
		};
	}

	
	/**
	 * 导出Excel数据
	 * @throws Exception 
	 */
	@Override
	@LogMethod(module = ModuleConstant.ALLOWANCE)
	public void exportExcel(OutputStream ouputStream, String type) throws Exception {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
		Map<String, String> row1Name = getExportRow1Name();
		String[] sort = new String[] { "year", "month", "pNumber", "pName", "highTem", "lowTem", "nightShift",
				"morningShift", "otherAllowance",  "otherBonus", "compensatory", "security",
				"addOther", "overSalary", "insurance", "beforeDeduction",
				"providentFund", "afterOtherDeduction", "reserved1", "reserved2", "reserved3", "reserved4", "reserved5",
				"reserved6", "reserved7", "reserved8", "reserved9", "reserved10" };
		try {
			List<Map<String, Object>> dataSource = null;
			if("0".equals(type)){//模板
				dataSource = new ArrayList<Map<String, Object>>();
			} else {//数据
				dataSource = allowanceDao.findEmployAllowance(new HashMap<String, Object>());
				BASE64Util.Base64DecodeMap(dataSource);
			}
			ExcelUtil.exportExcel("津贴/奖金/扣款列表", row1Name, dataSource, ouputStream, sort);
			LogManager.putContectOfLogInfo("导出津贴/奖金/扣款列表数据");
		} catch (Exception e) {
			logger.error("导出津贴/奖金/扣款列表数据失败",e);
			LogManager.putContectOfLogInfo("导出津贴/奖金/扣款列表数据失败:" + e.getMessage());
		}
	}

	/**
	 * 导出数据第一行名称
	 * @return
	 */
	private Map<String, String> getExportRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("year", "年");
		row1Name.put("month", "月");
		row1Name.put("pNumber", "员工工号");
		row1Name.put("pName", "员工姓名");
		row1Name.put("highTem", "高温津贴(标准)");
		row1Name.put("lowTem", "低温津贴(标准)");
		row1Name.put("nightShift", "夜班津贴");
		row1Name.put("morningShift", "早班津贴");
		row1Name.put("otherAllowance", "其它补贴");
		row1Name.put("performanceBonus", "绩效奖金");
		row1Name.put("otherBonus", "其它奖励");
		row1Name.put("compensatory", "礼金/补偿金");
		row1Name.put("security", "安全奖金");
		row1Name.put("addOther", "加其它");
		row1Name.put("mealDeduction", "餐补扣除");
		row1Name.put("dormDeduction", "宿舍扣款");
		row1Name.put("overSalary", "加班工资");
		row1Name.put("insurance", "代缴社保");
		row1Name.put("beforeDeduction", "其它扣款（税前）");
		row1Name.put("providentFund", "公积金");
		row1Name.put("afterOtherDeduction", "其它扣款（税后）");
//		row1Name.put("reserved1", "预留字段1");
//		row1Name.put("reserved2", "预留字段2");
//		row1Name.put("reserved3", "预留字段3");
//		row1Name.put("reserved4", "预留字段4");
//		row1Name.put("reserved5", "预留字段5");
//		row1Name.put("reserved6", "预留字段6");
//		row1Name.put("reserved7", "预留字段7");
//		row1Name.put("reserved8", "预留字段8");
//		row1Name.put("reserved9", "预留字段9");
//		row1Name.put("reserved10", "预留字段10");
		return row1Name;
	}

	/**
	 * 分页查询津贴、奖金、扣款的数据
	 */
	@Override
	@LogMethod(module = ModuleConstant.ALLOWANCE)
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
			LogManager.putContectOfLogInfo("查询参数:" + param.toString());
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "分页查询数据失败");
			logger.error("分页查询津贴/奖金/扣款信息失败",e);
			LogManager.putContectOfLogInfo("查询失败:"+e.getMessage());
		}
		return result;
	}
	
}
