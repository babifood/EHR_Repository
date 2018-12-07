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
import com.babifood.dao.WorkshopClockedDao;
import com.babifood.entity.LoginEntity;
import com.babifood.service.WorkshopClockedService;
import com.babifood.utils.ExcelUtil;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;
@Service
public class WorkshopClockedServiceImpl implements WorkshopClockedService {
	public static final Logger log = Logger.getLogger(WorkshopClockedServiceImpl.class);
	@Autowired
	WorkshopClockedDao workshopClockedDao;
	@Override
	@LogMethod(module = ModuleConstant.CLOCKED)
	public List<Map<String, Object>> loadWorkshopClocked(String workNumber, String userName,String comp,String organ,String dept) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			// TODO Auto-generated method stub
			list = workshopClockedDao.loadWorkshopClocked(workNumber,userName,comp,organ,dept);
			LogManager.putContectOfLogInfo("参数:"+workNumber+userName);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadWorkshopClocked:"+e.getMessage());
		}
		return list;
	}
	/**
	 * 导入
	 */
	@Override
	@LogMethod(module = ModuleConstant.CLOCKED)
	public Map<String, Object> importExcel(MultipartFile file, String type) {
		// TODO Auto-generated method stub
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
					List<Object[]> workshopClockedParam = getWorkshopClockedParam(values);
					workshopClockedDao.saveimportExcelWorkshopClocked(workshopClockedParam);
					result.put("code", "1");
					result.put("msg", "导入数据成功");
				} else {
					result.put("code", "0");
					result.put("msg", "导入数据失败，"+user.get("workname") + ",工号"+user.get("worknum")+"不是权限范围内员工");
				}
			} else {
				result.put("code", "1");
				result.put("msg", "导入数据成功");
			}
			LogManager.putContectOfLogInfo("导入车间考勤信息");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", "excel数据异常，导入数据失败");
			log.error("车间考勤信息数据异常",e);
			LogManager.putContectOfLogInfo("导出车间考勤信息列表失败，错误信息：车间考勤信息数据异常，" + e.getMessage());
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存导入数据异常，导入数据失败");
			log.error("保存车间考勤信息失败",e);
			LogManager.putContectOfLogInfo("导出车间考勤信息列表失败，错误信息：保存车间考勤信息失败，" + e.getMessage());
		}
		return result;
	}
	
	public Map<String, Object> checkValues(List<Map<String, Object>> values) {
		Map<String, Object> user = new HashMap<String, Object>();
		List<Map<String, Object>> auths = workshopClockedDao.loadUserDataAuthority();
		if(auths != null && auths.size() > 0){
			List<String> pNumberList = workshopClockedDao.findPNumberList(auths);
			for(Map<String, Object> map : values){
				if(!pNumberList.contains(map.get("worknum")+"")){
					user = map;
					break;
				}
			}
		}
		return user;
	}
	
	private List<Object[]> getWorkshopClockedParam(List<Map<String, Object>> values) {
		// TODO Auto-generated method stub
		List<Object[]> params = new ArrayList<>();
		for(int i=0;i<values.size();i++){
			params.add(new Object[]{
					values.get(i).get("workshop_year"),
					values.get(i).get("workshop_month"),
					values.get(i).get("worknum"),
					values.get(i).get("workname"),
					values.get(i).get("standardWorkDateLength"),
					values.get(i).get("practicalWorkDateLength")
				});
		}
		return params;
	}
	/**
	 * 导入excel首行与字段对应关系
	 * @return
	 */
	private Map<String, String> getImportRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("年度", "workshop_year");
		row1Name.put("月份", "workshop_month");
		row1Name.put("工号", "worknum");
		row1Name.put("姓名", "workname");
		row1Name.put("标准工作时长", "standardWorkDateLength");
		row1Name.put("实际工作时长", "practicalWorkDateLength");
		return row1Name;
	}
	/**
	 * 导出
	 */
	@Override
	@LogMethod(module = ModuleConstant.CLOCKED)
	public Map<String, Object> exportDormitoryCosts(OutputStream ouputStream, String type,String WorkNumber,String UserName,String comp,String organ,String dept) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> row1Name = getRow1Name();
		String[] sort = new String[]{"workshop_year", "workshop_month", "worknum", "workname", "p_company_name", "p_organization", "p_department", "p_section_office", "p_group", "p_post", "standardWorkDateLength","practicalWorkDateLength"};
		List<Map<String, Object>> performanceList = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
			if("1".equals(type)){
				performanceList = workshopClockedDao.loadWorkshopClocked(WorkNumber,UserName,comp,organ,dept);
				LogManager.putContectOfLogInfo("导出车间考勤信息列表");
			} else {
				performanceList = new ArrayList<Map<String, Object>>();
				LogManager.putContectOfLogInfo("下载车间考勤信息模板");
			}
			ExcelUtil.exportExcel("车间考勤列表", row1Name, performanceList, ouputStream, sort);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "导出excel失败");
			log.error("导出车间考勤信息列表失败",e);
			LogManager.putContectOfLogInfo("导出车间考勤信息列表失败，错误信息：" + e.getMessage());
		}
		return result;
	}
	/**
	 * excel首行
	 * @return
	 */
	private Map<String, String> getRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("workshop_year", "年度");
		row1Name.put("workshop_month", "月份");
		row1Name.put("worknum", "工号");
		row1Name.put("workname", "姓名");
		row1Name.put("p_company_name", "公司名称");
		row1Name.put("p_organization", "单位机构");
		row1Name.put("p_department", "部门名称");
		row1Name.put("p_section_office", "科室名称");
		row1Name.put("p_group", "班组名称");
		row1Name.put("p_post", "岗位名称");
		row1Name.put("standardWorkDateLength", "标准工作时长");
		row1Name.put("practicalWorkDateLength", "实际工作时长");
		return row1Name;
	}

}
