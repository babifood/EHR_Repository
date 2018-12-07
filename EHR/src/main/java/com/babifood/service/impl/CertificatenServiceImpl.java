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
import com.babifood.dao.CertificatenDao;
import com.babifood.entity.Certificaten;
import com.babifood.entity.LoginEntity;
import com.babifood.service.CertificatenService;
import com.babifood.utils.ExcelUtil;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;
@Service
public class CertificatenServiceImpl implements CertificatenService {
	public static final Logger log = Logger.getLogger(CertificatenServiceImpl.class);
	@Autowired
	CertificatenDao certificatenDao;
	@Override
	@LogMethod(module = ModuleConstant.CERTIFICATEN)
	public List<Map<String, Object>> loadCertificaten(String c_p_number,String c_p_name,String zj_name,String zj_code,String orga) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			// TODO Auto-generated method stub
			list = certificatenDao.loadCertificaten(c_p_number, c_p_name,zj_name,zj_code,orga);
			LogManager.putContectOfLogInfo("参数:"+c_p_number+c_p_name);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadPersonInFo:"+e.getMessage());
		}
		return list;
	}
	@Override
	@LogMethod(module = ModuleConstant.CERTIFICATEN)
	public Integer removeCertificaten(String c_id) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
		int rows =-1;
		try {
			// TODO Auto-generated method stub
			rows = certificatenDao.removeCertificaten(c_id);
			LogManager.putContectOfLogInfo("参数:"+c_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadPersonInFo:"+e.getMessage());
		}
		return rows;
	}
	@Override
	@LogMethod(module = ModuleConstant.CERTIFICATEN)
	public Integer saveCertificaten(Certificaten certificaten) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
		int rows = 1;
		try {
			// TODO Auto-generated method stub
			certificatenDao.saveCertificaten(certificaten);
			LogManager.putContectOfLogInfo("参数:"+certificaten);
		} catch (Exception e) {
			// TODO: handle exception
			rows = -1;
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadPersonInFo:"+e.getMessage());
		}
		return rows;
	}
	/**
	 * 导入
	 */
	@Override
	@LogMethod(module = ModuleConstant.CERTIFICATEN)
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
					List<Object[]> certificatenParam = getCertificatenParam(values);
					certificatenDao.saveimportExcelCertificaten(certificatenParam);
					result.put("code", "1");
					result.put("msg", "导入数据成功");
				} else {
					result.put("code", "0");
					result.put("msg", "导入数据失败，"+user.get("c_p_name") + ",工号"+user.get("c_p_number")+"不是权限范围内员工");
				}
			} else {
				result.put("code", "1");
				result.put("msg", "导入数据成功");
			}
			LogManager.putContectOfLogInfo("导入员工证件信息");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("code", "0");
			result.put("msg", "excel数据异常，导入数据失败");
			log.error("员工证件信息数据异常",e);
			LogManager.putContectOfLogInfo("导出员工证件信息列表失败，错误信息：员工证件信息数据异常，" + e.getMessage());
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "保存导入数据异常，导入数据失败");
			log.error("保存员工证件信息失败",e);
			LogManager.putContectOfLogInfo("导出员工证件信息列表失败，错误信息：保存员工证件信息失败，" + e.getMessage());
		}
		return result;
	}
	public Map<String, Object> checkValues(List<Map<String, Object>> values) {
		Map<String, Object> user = new HashMap<String, Object>();
		List<Map<String, Object>> auths = certificatenDao.loadUserDataAuthority();
		if(auths != null && auths.size() > 0){
			List<String> pNumberList = certificatenDao.findPNumberList(auths);
			for(Map<String, Object> map : values){
				if(!pNumberList.contains(map.get("c_p_number")+"")){
					user = map;
					break;
				}
			}
		}
		return user;
	}
	
	private List<Object[]> getCertificatenParam(List<Map<String, Object>> values) {
		// TODO Auto-generated method stub
		List<Object[]> params = new ArrayList<>();
		for(int i=0;i<values.size();i++){
			params.add(new Object[]{
					values.get(i).get("c_p_number"),
					values.get(i).get("c_p_name"),
					values.get(i).get("c_certificate_name"),
					values.get(i).get("c_organization"),
					values.get(i).get("c_certificate_number"),
					values.get(i).get("c_begin_date"),
					values.get(i).get("c_end_date"),
					values.get(i).get("c_desc")
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
		row1Name.put("人员编号", "c_p_number");
		row1Name.put("人员名称", "c_p_name");
		row1Name.put("证件名称", "c_certificate_name");
		row1Name.put("签发机构", "c_organization");
		row1Name.put("证件编号", "c_certificate_number");
		row1Name.put("颁发日期", "c_begin_date");
		row1Name.put("失效日期", "c_end_date");
		row1Name.put("备注", "c_desc");
		return row1Name;
	}
	/**
	 * 导出
	 */
	@Override
	@LogMethod(module = ModuleConstant.CERTIFICATEN)
	public Map<String, Object> exportDormitoryCosts(OutputStream ouputStream, String type) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> row1Name = getRow1Name();
		String[] sort = new String[]{"c_p_number", "c_p_name", "c_certificate_name", "c_organization", "c_certificate_number", "c_begin_date", "c_end_date", "c_desc"};
		List<Map<String, Object>> performanceList = null;
		try {
			LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
			LogManager.putUserIdOfLogInfo(login.getUser_id());
			LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_EXPORT);
			if("1".equals(type)){
				performanceList = certificatenDao.loadCertificaten("","","","","");
				LogManager.putContectOfLogInfo("导出绩效信息列表");
			} else {
				performanceList = new ArrayList<Map<String, Object>>();
				LogManager.putContectOfLogInfo("下载证件管理信息模板");
			}
			ExcelUtil.exportExcel("证件管理列表", row1Name, performanceList, ouputStream, sort);
			result.put("code", "1");
		} catch (Exception e) {
			result.put("code", "0");
			result.put("msg", "导出excel失败");
			log.error("导出证件管理信息列表失败",e);
			LogManager.putContectOfLogInfo("导出证件管理信息列表失败，错误信息：" + e.getMessage());
		}
		return result;
	}
	/**
	 * excel首行
	 * @return
	 */
	private Map<String, String> getRow1Name() {
		Map<String, String> row1Name = new HashMap<String, String>();
		row1Name.put("c_p_number", "人员编号");
		row1Name.put("c_p_name", "人员名称");
		row1Name.put("c_certificate_name", "证件名称");
		row1Name.put("c_organization", "签发机构");
		row1Name.put("c_certificate_number", "证件编号");
		row1Name.put("c_begin_date", "颁发日期");
		row1Name.put("c_end_date", "失效日期");
		row1Name.put("c_desc", "备注");
		return row1Name;
	}
}
