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
import com.babifood.dao.BaseSalaryDao;
import com.babifood.entity.BaseSalaryEntity;
import com.babifood.entity.LoginEntity;
import com.babifood.service.BaseSalaryService;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.ExcelUtil;
import com.babifood.utils.UtilDateTime;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;

/**
 * 基础薪资
 * @author wangguocheng
 *
 */
@Service
public class BaseSalaryServiceImpl implements BaseSalaryService {
	
	private static Logger logger = Logger.getLogger(BaseSalaryServiceImpl.class);
	
	@Autowired
	private BaseSalaryDao baseSalaryDao;

	/**
	 * 分页查询基础薪资列表
	 */
	@Override
	@LogMethod(module = ModuleConstant.BASESALARY)
	public Map<String, Object> getPageBaseSalary(Integer page, Integer rows, String pNumber, String pName) {
		Map<String, Object> result = new HashMap<String, Object>();
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		Map<String, Object> params = new HashMap<String, Object>();
		Integer pageNum = page == null ? 1 : page;
		Integer pageSize = rows == null ? 10 : rows;
		String year = UtilDateTime.getYearOfPreMonth();
		String month = UtilDateTime.getPreMonth();
		params.put("start", (pageNum - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("pNumber", pNumber);
		params.put("pName", pName);
		params.put("lastMonth", year + "-" + month);
		try {
			int count = baseSalaryDao.queryCountOfBaseSalarys(params);
			List<Map<String, Object>> baseSalaryList = baseSalaryDao.queryBaseSalaryList(params);
			result.put("code", "1");
			result.put("total", count);
			result.put("rows", baseSalaryList);
			LogManager.putContectOfLogInfo("查询基础薪资信息");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询基础配置信息失败");
			logger.error("分页查询基础薪资列表失败",e);
			LogManager.putContectOfLogInfo("查询基础薪资失败，错误信息：" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 保存基础薪资
	 * @param baseSalary
	 * @return
	 */
	@Override
	@LogMethod(module = ModuleConstant.BASESALARY)
	public Map<String, Object> saveBaseSalary(BaseSalaryEntity baseSalary) {
		Map<String, Object> result = new HashMap<String, Object>();
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		try {
			if(baseSalary.getId() != null){
				LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EDIT);
				if(UtilDateTime.isBeforeThanCurrentMonth(baseSalary.getUseTime(), "yyyy-MM")){
					baseSalaryDao.updateBaseSalary(baseSalary);
				} else {
					result.put("code", "0");
					result.put("msg", "该基本薪资为上月之前设置，不能修改");
					logger.error("该基本薪资为上月之前设置，不能修改");
					LogManager.putContectOfLogInfo("该基本薪资为上月之前设置，不能修改," + baseSalary.toString());
					return result;
				}
			} else {
				baseSalary.setCreateTime(UtilDateTime.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				baseSalaryDao.insertBaseSalary(baseSalary);
				LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
			}
			result.put("code", "1");
			LogManager.putContectOfLogInfo("保存基础薪资信息," + baseSalary.toString());
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存基础配置信息失败");
			logger.error("保存基础配置信息失败",e);
			LogManager.putContectOfLogInfo("保存基础薪资失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 删除基础薪资
	 * @param id
	 * @param pNumber
	 * @return
	 */
	@Override
	@LogMethod(module = ModuleConstant.BASESALARY)
	public Map<String, Object> removeBaseSalary(Integer id, String pNumber) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
			List<Map<String, Object>> baseSalarys = baseSalaryDao.findBaseSalaryByPNumber(pNumber);
			if(baseSalarys == null || baseSalarys.size() <= 1){
				result.put("code", "0");
				result.put("msg", "该基础新增信息为员工唯一基础薪资信息，不能删除");
				LogManager.putContectOfLogInfo("删除基础薪资失败，错误信息：该基础新增信息为员工唯一基础薪资信息，不能删除");
			} else {
				baseSalaryDao.deleteBaseSalaryById(id);
				result.put("code", "1");
				LogManager.putContectOfLogInfo("删除基础薪资");
			}
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "删除基础配置信息失败");
			logger.error("删除基础配置信息失败",e);
			LogManager.putContectOfLogInfo("删除基础薪资失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	@Override
	public Map<String, Object> getBaseSalaryRecord(Integer page, Integer rows, String pNumber) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> baseSalarys = baseSalaryDao.getBaseSalaryRecord(pNumber);
			if(baseSalarys.size() >= page * rows){
				baseSalarys.subList((page - 1) * rows , rows);
			} else {
				baseSalarys.subList((page - 1) * rows , baseSalarys.size());
			}
			BASE64Util.Base64DecodeMap(baseSalarys);
			result.put("total", baseSalarys.size());
			result.put("rows", baseSalarys);
			LogManager.putContectOfLogInfo("查询个人调薪记录信息");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "查询个人调薪记录失败");
			logger.error("查询个人调薪记录失败",e);
			LogManager.putContectOfLogInfo("查询个人调薪记录失败，错误信息：" + e.getMessage());
		}
		return result;
	}

	@Override
	@LogMethod(module = ModuleConstant.BASESALARY)
	public Map<String, Object> importExcel(MultipartFile file) {
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
					List<Object[]> baseSalaryParam = getBaseSalaryParam(values);
					baseSalaryDao.saveBaseSalaryList(baseSalaryParam);
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
			logger.error("导入员工基础薪资信息失败",e);
			LogManager.putContectOfLogInfo("导入员工基础薪资信息失败:"+e.getMessage());
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存导入数据异常，导入数据失败");
			logger.error("保存员工基础薪资信息失败",e);
			LogManager.putContectOfLogInfo("保存员工基础薪资信息失败:"+e.getMessage());
		}
		return result;
	}
	
	private List<Object[]> getBaseSalaryParam(List<Map<String, Object>> values) {
		List<Object[]> baseSalaryParam = new ArrayList<Object[]>();
		for (Map<String, Object> map : values) {
			Object[] obj = new Object[] { map.get("pNumber"),
					BASE64Util.getDecodeStringTowDecimal(map.get("baseSalary")),
					BASE64Util.getDecodeStringTowDecimal(map.get("fixedOverTimeSalary")), 
					BASE64Util.getDecodeStringTowDecimal(map.get("postSalary")), 
					BASE64Util.getDecodeStringTowDecimal(map.get("callSubsidies")),
					BASE64Util.getDecodeStringTowDecimal(map.get("companySalary")),
					BASE64Util.getDecodeStringTowDecimal(map.get("singelMeal")),
					BASE64Util.getDecodeStringTowDecimal(map.get("performanceSalary")),
					BASE64Util.getDecodeStringTowDecimal(map.get("stay")),
					UtilDateTime.getCurrentMonth(),"计时".equals(map.get("workType1"))?"0":"1",
					map.get("useTime"),"0"};
			baseSalaryParam.add(obj);
		}
		return baseSalaryParam;
	}

	public Map<String, Object> checkValues(List<Map<String, Object>> values) {
		Map<String, Object> user = new HashMap<String, Object>();
		List<Map<String, Object>> auths = baseSalaryDao.loadUserDataAuthority();
		if(auths != null && auths.size() > 0){
			List<String> pNumberList = baseSalaryDao.findPNumberList(auths);
			for(Map<String, Object> map : values){
				if(!pNumberList.contains(map.get("pNumber")+"")){
					user = map;
					break;
				}
			}
		}
		return user;
	}

	private Map<String, String> getRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("员工工号", "pNumber");
		row1Name.put("员工姓名", "pName");
		row1Name.put("基本工资", "baseSalary");
		row1Name.put("固定加班工资", "fixedOverTimeSalary");
		row1Name.put("岗位工资", "postSalary");
		row1Name.put("司龄工资", "companySalary");
		row1Name.put("话费补贴", "callSubsidies");
		row1Name.put("单个餐补", "singelMeal");
		row1Name.put("绩效工资", "performanceSalary");
		row1Name.put("工作类型", "workType1");
		row1Name.put("外住补贴", "stay");
		row1Name.put("启用时间", "useTime");
		return row1Name;
	}

	@Override
	@LogMethod(module = ModuleConstant.BASESALARY)
	public void exportExcel(OutputStream ouputStream, String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> row1Name = getRowName();
		String[] sort = new String[]{"pNumber", "pName", "baseSalary", "fixedOverTimeSalary", "postSalary", "companySalary", 
				"callSubsidies", "singelMeal", "workType1","stay","useTime"};
		List<Map<String, Object>> baseSalaryParam = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
			if("1".equals(type)){
				Map<String, Object> params = new HashMap<String, Object>();
				baseSalaryParam = baseSalaryDao.queryBaseSalaryList(params);
				LogManager.putContectOfLogInfo("导出基础薪资信息列表");
			} else {
				baseSalaryParam = new ArrayList<Map<String, Object>>();
				LogManager.putContectOfLogInfo("下载基础薪资信息模板");
			}
			ExcelUtil.exportExcel("基础薪资列表", row1Name, baseSalaryParam, ouputStream, sort);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "导出excel失败");
			logger.error("导出基础薪资信息列表失败",e);
			LogManager.putContectOfLogInfo("导出基础薪资信息列表失败，错误信息：" + e.getMessage());
		}
//		return result;
	}
	
	private Map<String, String> getRowName() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("pNumber", "员工工号");
		row1Name.put("pName", "员工姓名");
		row1Name.put("baseSalary", "基本工资");
		row1Name.put("stay", "外住补贴");
		row1Name.put("fixedOverTimeSalary", "固定加班工资");
		row1Name.put("postSalary", "岗位工资");
		row1Name.put("companySalary", "司龄工资");
		row1Name.put("callSubsidies", "话费补贴");
		row1Name.put("singelMeal", "单个餐补");
		row1Name.put("performanceSalary", "绩效工资");
		row1Name.put("workType1", "工作类型");
		row1Name.put("useTime", "启用时间");
		return row1Name;
	}

}
