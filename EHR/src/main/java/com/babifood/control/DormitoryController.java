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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.babifood.entity.DormitoryCostEntity;
import com.babifood.entity.DormitoryEntity;
import com.babifood.service.DormitoryService;
import com.babifood.utils.UtilDateTime;

@Controller
@RequestMapping("dormitory")
public class DormitoryController {
	
	@Autowired
	private DormitoryService dormitoryService;
	
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> saveDormitory(DormitoryEntity dormitory) {
		return dormitoryService.saveDormitory(dormitory);
	}
	
	@RequestMapping("remove")
	@ResponseBody
	public Map<String, Object> removeDormitory(String id) {
		return dormitoryService.removeDormitory(id);
	}

	@RequestMapping("all")
	@ResponseBody
	public Map<String, Object> getUnStayDormitory(Integer page,Integer rows,String floor, String roomNo, String sex, String stay) {
		return dormitoryService.getUnStayDormitory(page, rows, floor,  roomNo,  sex,  stay);
	}
	
	@RequestMapping("stay")
	@ResponseBody
	public Map<String, Object> getStayDormitory(Integer page,Integer rows,String floor, String roomNo, String sex, String pNumber, String pName) {
		return dormitoryService.getStayDormitory(page, rows, floor, roomNo, sex,pNumber,pName);
	}
	
	@RequestMapping("checking")
	@ResponseBody
	public Map<String, Object> cheakingDormitory(String dormitoryId, String pNumber, String stayTime) {
		return dormitoryService.cheakingDormitory(dormitoryId, pNumber, stayTime);
	}
	
	@RequestMapping("checkout")
	@ResponseBody
	public Map<String, Object> cheakoutDormitory(String dormitoryId, String pNumber, String outTime, String type) {
		return dormitoryService.cheakoutDormitory(dormitoryId, pNumber, outTime, type);
	}
	
	@RequestMapping("cost")
	@ResponseBody
	public Map<String, Object> queryDormitoryCostList(Integer page,Integer rows,String floor, String roomNo, String pNumber, String pName) {
		return dormitoryService.queryDormitoryCostList(page, rows, floor, roomNo, pNumber, pName);
	}
	
	@RequestMapping("saveCost")
	@ResponseBody
	public Map<String, Object> saveCost(DormitoryCostEntity dormitoryCost) {
		return dormitoryService.saveCost(dormitoryCost);
	}
	
	@RequestMapping("removeCost")
	@ResponseBody
	public Map<String, Object> removeCost(Integer id) {
		return dormitoryService.removeCost(id);
	}
	
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
	
	@RequestMapping(value = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importDormitoryCost(@RequestParam(value="excel")CommonsMultipartFile file, String type){
		return dormitoryService.importDormitoryCost(file, type);
	}
}
