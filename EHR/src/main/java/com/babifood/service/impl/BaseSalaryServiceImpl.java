package com.babifood.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.dao.BaseSalaryDao;
import com.babifood.entity.BaseSalaryEntity;
import com.babifood.entity.LoginEntity;
import com.babifood.service.BaseSalaryService;
import com.babifood.utils.BASE64Util;
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
			BASE64Util.Base64DecodeMap(baseSalaryList);
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

}
