package com.babifood.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.dao.PersonInFoDao;
import com.babifood.dao.SynchronousOaAccountInfoDao;
import com.babifood.dao.impl.LoginDaoImpl;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.PersonBasrcEntity;
import com.babifood.service.PersonInFoService;
import com.babifood.utils.IdGen;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;
@Service
public class PersonInFoServiceImpl implements PersonInFoService {
	public static final Logger log = Logger.getLogger(PersonInFoServiceImpl.class);
	@Autowired
	PersonInFoDao personInFoDao;
	@Autowired
	SynchronousOaAccountInfoDao synchronousOaAccountInfoDao;
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> loadPersonInFo(String search_p_number,String search_p_name) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list=null;
		try {
			list = personInFoDao.loadPersonInFo(search_p_number, search_p_name);
			LogManager.putContectOfLogInfo("参数：search_p_number="+search_p_number+"search_p_name="+search_p_name);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadPersonInFo:"+e.getMessage());
		}
		return list;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> loadEducation(String e_p_id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list=null;
		try {
			list = personInFoDao.loadEducation(e_p_id);
			LogManager.putContectOfLogInfo("参数：e_p_id="+e_p_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadEducation:"+e.getMessage());
		}
		return list;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public Integer savePersonInfo(PersonBasrcEntity personInFo) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
		int status = 1;
		// TODO Auto-generated method stub
		String[] codes = new String[5];
		codes[0] = personInFo.getP_company_id()==null?"":personInFo.getP_company_id();
		codes[1] = personInFo.getP_organization_id()==null?"":personInFo.getP_organization_id();
		codes[2] = personInFo.getP_department_id()==null?"":personInFo.getP_department_id();
		codes[3] = personInFo.getP_section_office_id()==null?"":personInFo.getP_section_office_id();
		codes[4] = personInFo.getP_group_id()==null?"":personInFo.getP_group_id();
		String p_this_dept_code = codes[0];
		for(int i=0;i<codes.length;i++){
			if(p_this_dept_code.length()<codes[i].length()){
				p_this_dept_code=codes[i];
			}
		}
		if(personInFo.getP_id().equals("")||personInFo.getP_id()==null){
			personInFo.setP_id(IdGen.uuid());
		}
		personInFo.setP_create_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		personInFo.setP_this_dept_code(p_this_dept_code);
		try {
			personInFoDao.savePersonInfo(personInFo);
			LogManager.putContectOfLogInfo("参数:"+personInFo);
		} catch (Exception e) {
			// TODO: handle exception
			status = -1;
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("savePersonInfo:"+e.getMessage());
		}
		return status;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public Integer removePersonInFo(String p_id) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_REMOVE);
		int status = 1;
		try {
			// TODO Auto-generated method stub
			personInFoDao.removePersonInFo(p_id);
			LogManager.putContectOfLogInfo("参数:"+p_id);
		} catch (Exception e) {
			// TODO: handle exception
			status = -1;
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("removePersonInFo:"+e.getMessage());
		}
		return status;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> loadCultivateFront(String c_p_id) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			// TODO Auto-generated method stub
			list = personInFoDao.loadCultivateFront(c_p_id);
			LogManager.putContectOfLogInfo("参数:"+c_p_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadCultivateFront:"+e.getMessage());
		}
		return list;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> loadCultivateLater(String c_p_id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			list = personInFoDao.loadCultivateLater(c_p_id);
			LogManager.putContectOfLogInfo("参数:"+c_p_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadCultivateFront:"+e.getMessage());
		}
		return list;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> loadWorkFront(String w_p_id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			list = personInFoDao.loadWorkFront(w_p_id);
			LogManager.putContectOfLogInfo("参数:"+w_p_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadWorkFront:"+e.getMessage());
		}
		return list;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> loadWorkLater(String w_p_id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			list = personInFoDao.loadWorkLater(w_p_id);
			LogManager.putContectOfLogInfo("参数:"+w_p_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadWorkLater:"+e.getMessage());
		}
		return list;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> loadCertificate(String c_p_id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			list = personInFoDao.loadCertificate(c_p_id);
			LogManager.putContectOfLogInfo("参数:"+c_p_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadCertificate:"+e.getMessage());
		}
		return list;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> loadFamily(String f_p_id) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			list = personInFoDao.loadFamily(f_p_id);
			LogManager.putContectOfLogInfo("参数:"+f_p_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadFamily:"+e.getMessage());
		}
		return list;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public Object getPersonFoPid(String p_id) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		Object obj = null;
		try {
			// TODO Auto-generated method stub
			obj = personInFoDao.getPersonFoPid(p_id);
			LogManager.putContectOfLogInfo("参数:"+p_id);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("getPersonFoPid:"+e.getMessage());
		}
		return obj;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> loadComboboxCompanyData() {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			list = personInFoDao.loadComboboxCompanyData();
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadComboboxCompanyData:"+e.getMessage());
		}
		return list;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public Integer getPersonCount() {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		Integer count = 0;
		try {
			// TODO Auto-generated method stub
			count = personInFoDao.getPersonCount();
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("getPersonByPnumber:"+e.getMessage());
		}
		return count;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> findPagePersonInfo(int index, int threadCount) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			list = personInFoDao.findPagePersonInfo(index * threadCount ,threadCount);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadOaWorkNumInFo:"+e.getMessage());
		}
		return list;
		
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public Object getPersonByPnumber(String pNumber) {
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		Object obj = null;
		try {
			// TODO Auto-generated method stub
			obj = personInFoDao.getPersonByPnumber(pNumber);
			LogManager.putContectOfLogInfo("参数:"+pNumber);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("getPersonByPnumber:"+e.getMessage());
		}
		return obj;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public List<Map<String, Object>> loadOaWorkNumInFo(String workNum, String userName) {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_FIND);
		List<Map<String, Object>> list = null;
		try {
			list = synchronousOaAccountInfoDao.loadOaWorkNumInFo(workNum, userName);
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadOaWorkNumInFo:"+e.getMessage());
		}
		return list;
	}
	@LogMethod(module = ModuleConstant.PERSONINFO)
	@Override
	public Object getRandomYxWorkNum() {
		// TODO Auto-generated method stub
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_ADD);
	   	int random=new Random().nextInt(90000)+10000;//为变量赋随机值100-999;
	   	String newWorkNum = "YX"+random;
	   	List<Map<String, Object>> list = null;
		try {
			list = personInFoDao.loadEHRWorkNumInFo();
		} catch (Exception e) {
			// TODO: handle exception
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("getRandomYxWorkNum:"+e.getMessage());
		}
	   	int size = list==null?0:list.size();
	   	for(int i=0;i<size;i++){
	   		if(newWorkNum.equals(list.get(i).get("p_number").toString())){
	   			i=0;
	   			random=random+1;
	   			newWorkNum = "YX"+random;
	   		}
	   	}
		return newWorkNum;
	}
	
}
