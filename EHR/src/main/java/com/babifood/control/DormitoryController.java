package com.babifood.control;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.babifood.entity.DormitoryCostEntity;
import com.babifood.entity.DormitoryEntity;
import com.babifood.entity.DormitoryStayEntiry;
import com.babifood.service.DormitoryService;
import com.babifood.utils.UtilDateTime;

/**
 * 宿舍管理
 * @author wangguocheng
 *
 */
@Controller
@RequestMapping("dormitory")
public class DormitoryController {
	
	@Autowired
	private DormitoryService dormitoryService;
	
	/**
	 * 保存宿舍信息
	 * @param dormitory
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> saveDormitory(DormitoryEntity dorm,DormitoryStayEntiry dormStay) {
		return dormitoryService.operateDormitory(dorm, dormStay);
	}
	
	/**
	 * 删除宿舍信息
	 * @param id
	 * @return
	 */
	@RequestMapping("remove")
	@ResponseBody
	public Map<String, Object> removeDormitory(String id) {
		return dormitoryService.removeDormitory(id);
	}

	/**
	 * 查询宿舍信息
	 * @param page
	 * @param rows
	 * @param floor
	 * @param roomNo
	 * @param sex
	 * @param stay
	 * @return
	 */
	@RequestMapping("all")
	@ResponseBody
	public Map<String, Object> getUnStayDormitory(Integer page, Integer rows, String floor, String roomNo, String sex,
			String stay, String dormType) {
		return dormitoryService.getUnStayDormitory(page, rows, floor, roomNo, sex, stay, dormType);
	}

	/**
	 * 入住信息列表
	 * @param page
	 * @param rows
	 * @param floor
	 * @param roomNo
	 * @param sex
	 * @param pNumber
	 * @param pName
	 * @return
	 */
	@RequestMapping("stay")
	@ResponseBody
	public Map<String, Object> getStayDormitory(Integer page, Integer rows, String floor, String roomNo, String sex,
			String pNumber, String pName, String dormType, String checkingStart, String checkingEnd,
			String checkoutStart, String checkoutEnd) {
		return dormitoryService.getStayDormitory(page, rows, floor, roomNo, sex, pNumber, pName, dormType,
				checkingStart, checkingEnd, checkoutStart, checkoutEnd);
	}
	
	/**
	 * 导出住宿记录
	 * @param response
	 * @param type
	 * @param year
	 * @param month
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("exportRecord")
	@ResponseBody
	public Map<String, Object> exportRecord(HttpServletResponse response, String checkingStart, String checkingEnd,
			String checkoutStart, String checkoutEnd) throws IOException{
		String filename = new String("员工住宿记录".getBytes("UTF-8"),"ISO8859-1") + UtilDateTime.getCurrentTime("yyyyMMddHHmmss");
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");    
        OutputStream ouputStream = response.getOutputStream();   
		return dormitoryService.exportRecord(ouputStream, checkingStart, checkingEnd, checkoutStart, checkoutEnd);
	}
	
//	/**
//	 * 入住
//	 * @param dormitoryId
//	 * @param pNumber
//	 * @param stayTime
//	 * @return
//	 */
//	@RequestMapping("checking")
//	@ResponseBody
//	public Map<String, Object> cheakingDormitory(String dormitoryId, String pNumber, String stayTime) {
//		return dormitoryService.cheakingDormitory(dormitoryId, pNumber, stayTime);
//	}
	
	/**
	 * 搬出
	 * @param dormitoryId
	 * @param pNumber
	 * @param outTime
	 * @param type
	 * @return
	 */
	@RequestMapping("checkout")
	@ResponseBody
	public Map<String, Object> cheakoutDormitory(DormitoryStayEntiry dormStay, String type) {
		return dormitoryService.cheakoutDormitory(dormStay, type);
	}
	
	/**
	 * 查询宿舍费用信息
	 * @param page
	 * @param rows
	 * @param floor
	 * @param roomNo
	 * @param pNumber
	 * @param pName
	 * @return
	 */
	@RequestMapping("cost")
	@ResponseBody
	public Map<String, Object> queryDormitoryCostList(Integer page,Integer rows,String floor, String roomNo, String pNumber, String pName) {
		return dormitoryService.queryDormitoryCostList(page, rows, floor, roomNo, pNumber, pName);
	}
	
	/**
	 * 保存宿舍费用信息 
	 * @param dormitoryCost
	 * @return
	 */
	@RequestMapping("saveCost")
	@ResponseBody
	public Map<String, Object> saveCost(DormitoryCostEntity dormitoryCost) {
		return dormitoryService.saveCost(dormitoryCost);
	}
	
	/**
	 * 删除宿舍扣款信息
	 * @param id
	 * @return
	 */
	@RequestMapping("removeCost")
	@ResponseBody
	public Map<String, Object> removeCost(Integer id) {
		return dormitoryService.removeCost(id);
	}
	
	/**
	 * 导出宿舍扣款信息
	 * @param response
	 * @param type
	 * @param year
	 * @param month
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("export")
	@ResponseBody
	public Map<String, Object> exportDormitoryCosts(HttpServletResponse response, String type, String year, String month) throws IOException{
		String filename = new String("员工住宿费用表".getBytes("UTF-8"),"ISO8859-1");
		if("1".equals(type)){
			filename += UtilDateTime.getCurrentTime("yyyyMMddHHmmss");
		}
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");    
        OutputStream ouputStream = response.getOutputStream();   
		return dormitoryService.exportDormitoryCosts(ouputStream, type, year, month);
	}
	
	/**
	 * 导入宿舍扣款信息
	 * @param file
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importDormitoryCost(@RequestParam(value="file",required = false) MultipartFile file, String type){
		return dormitoryService.importDormitoryCost(file, type);
	}
}
