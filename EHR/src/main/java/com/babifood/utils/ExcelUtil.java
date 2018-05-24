package com.babifood.utils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {

	/**
	 * 导出excel文档
	 * @param excelName
	 * @param rowName
	 * @param datasource
	 * @param out
	 * @throws Exception
	 */
	public static void exportExcel(String excelName,Map<String,String> rowName,List<Map<String, Object>> datasource,OutputStream out) throws Exception{
		if(datasource == null || datasource.size() <= 0 ){
			throw new Exception("数据异常");
		}
		//创建HSSFWorkbook对象  
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建HSSFSheet对象  
		HSSFSheet sheet = wb.createSheet("sheet0");
		//合并单元格
		int size = rowName.keySet().size();//可能生成列数
		CellRangeAddress cra =new CellRangeAddress(0, 0, 0, size-1); // 起始行, 终止行, 起始列, 终止列  
		sheet.addMergedRegion(cra); 
		
		//创建HSSFRow对象  
		HSSFRow row = sheet.createRow(0); 
		row.setHeight((short)500);//设置行高
		
		//创建HSSFCell对象  
		HSSFCell cell=row.createCell(0); 
		cell.setCellValue(excelName);
		
		//第一行样式
		HSSFCellStyle firstStyle = createFirstRowCellStyle(wb);
		cell.setCellStyle(firstStyle);
		
		//第二行样式
		HSSFCellStyle secondStyle = createSecondRowCellStyle(wb);
		
		//创建HSSFRow对象  
      	HSSFRow row1 = sheet.createRow(1);
      	row1.setHeight((short)400);//设置行高
      	
      	String[] columns = new String[size];//每列对应key（避免错位）
      	int index = 0;
      	for(String key:rowName.keySet()){//列名称信息
      		columns[index] = key;
      		String value = rowName.get(key);
      		sheet.setColumnWidth((short) index, (short) value.length()*800);//设置列宽
      		HSSFCell cell1=row1.createCell(index);
      		cell1.setCellStyle(secondStyle);
      		cell1.setCellValue(value);
      		index++;
      	}
      	
      	int rownum = 2;//行号
      	for(Map<String, Object> map:datasource){//数据
      		HSSFRow row2 = sheet.createRow(rownum);
      		int cellnum = 0 ;
      		for(int i = 0;i < map.keySet().size() ; i++){
      			HSSFCell cell1=row2.createCell(cellnum);
          		cell1.setCellValue(map.get(columns[cellnum])+"");
          		cellnum++;
      		}
      		rownum++;
      	}
		
		wb.write(out);  
		out.flush();
		out.close();
		wb.close();
	}
	
	
	public static void importExcel() {
		
	}
	
	/**
	 * 获取Excel第一行样式
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle createFirstRowCellStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.DOUBLE);; //下边框
        style.setBorderLeft(BorderStyle.DOUBLE);//左边框
        style.setBorderTop(BorderStyle.DOUBLE);//上边框
        style.setBorderRight(BorderStyle.DOUBLE);//右边框
        style.setAlignment(HorizontalAlignment.CENTER); // 居中
        HSSFFont firstRowFont = getFirstRowHSSFFont(workbook);
        style.setFont(firstRowFont);
        return style;
	}
	
	/**
	 * 获取Excel第二行样式
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle createSecondRowCellStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.DOUBLE);; //下边框
        style.setBorderLeft(BorderStyle.DOUBLE);//左边框
        style.setBorderTop(BorderStyle.DOUBLE);//上边框
        style.setBorderRight(BorderStyle.DOUBLE);//右边框
        style.setAlignment(HorizontalAlignment.CENTER); // 居中
        HSSFFont secondRowFont = getSecondRowHSSFFont(workbook);
        style.setFont(secondRowFont);
        return style;
	}
	
	/**
	 * 获取Excel第一行字体样式
	 * @param wb
	 * @return
	 */
	public static HSSFFont getFirstRowHSSFFont(HSSFWorkbook wb) {
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 16);//设置字体大小
		font.setBold(true);//粗体显示
		return font;
	}
	
	/**
	 * 获取Excel第二行字体样式
	 * @param wb
	 * @return
	 */
	public static HSSFFont getSecondRowHSSFFont(HSSFWorkbook wb) {
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 12);//设置字体大小
		font.setBold(true);//粗体显示
		return font;
	}
	
	
	public static void main(String[] args) throws Exception {
		String excelName = "部门信息列表";
		Map<String,String> rowName = new HashMap<>();
		rowName.put("deptName", "部门名称");
		rowName.put("deptCode", "部门代码");
		rowName.put("pName", "上级部门名称");
		List<Map<String, Object>> datasource = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptName", "中饮股份");
		map.put("deptCode", "1001");
		map.put("pName", "中饮集团");
		datasource.add(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("deptName", "中饮管理");
		map1.put("deptCode", "1002");
		map1.put("pName", "中饮集团");
		datasource.add(map1);
		exportExcel(excelName, rowName, datasource, null);
	}
	
	
}
