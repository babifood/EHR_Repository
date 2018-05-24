package com.babifood.control;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.DeptEntity;
import com.babifood.service.DeptPageService;
import com.babifood.utils.UtilDateTime;

@Controller
@RequestMapping("dept") 
public class DeptPageController {

	@Autowired
	private DeptPageService deptService;
	
	/**
	 * 查询当前机构及其所有子组织架构
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("loadTree")
	public List<Map<String, Object>> loadOrganzations(String deptCode) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result.add(deptService.findOrganization(deptCode));
		return result;
	}
	
	/**
	 * 查询当前机构及其所有子组织架构
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findAll")
	public List<Map<String, Object>> findAllDepts() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = deptService.findAll();
		return result;
	}
	
	/**
	 * 根据部门编号查找部门信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryCount")
	public Map<String, Object> queryCountByDeptCode(String deptCode) {
		return deptService.queryCountByDeptCode(deptCode);
	}
	
	/**
	 * 根据部门编号查找部门信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findDept")
	public Map<String, Object> findDeptById(String deptCode) {
		return deptService.findDeptByDeptCode(deptCode);
	}
	
	/**
	 * 添加部门信息
	 * @param deptEntity
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("addDept")
	public Map<String, Object> addDept(DeptEntity deptEntity) throws Exception {
		deptEntity.setDeptName(new String(deptEntity.getDeptName().getBytes("ISO-8859-1"), "UTF-8"));
		deptEntity.setRemark(new String(deptEntity.getRemark().getBytes("ISO-8859-1"), "UTF-8"));
		return deptService.addDept(deptEntity);
	}
	
	/**
	 * 修改部门信息
	 * @param deptEntity
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping("updateDept")
	public Map<String, Object> updateDept(DeptEntity deptEntity) throws UnsupportedEncodingException {
		deptEntity.setDeptName(new String(deptEntity.getDeptName().getBytes("ISO-8859-1"), "UTF-8"));
		deptEntity.setRemark(new String(deptEntity.getRemark().getBytes("ISO-8859-1"), "UTF-8"));
		return deptService.updateDept(deptEntity);
	}
	
	/**
	 * 删除部门信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteDept")
	public Map<String, Object> deleteDept(String deptCode) {
		return deptService.deleteDept(deptCode);
	}
	
	@ResponseBody
	@RequestMapping("export")
	public void exportExcel(HttpServletResponse response,String type) throws Exception {
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + 
        		new String("部门信息".getBytes("UTF-8"),"ISO8859-1") + UtilDateTime.getCurrentTime() + ".xls");    
        OutputStream ouputStream = response.getOutputStream();    
        deptService.exportExcel(ouputStream,type);
	}

}
