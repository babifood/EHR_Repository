package com.babifood.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {

	/**
	 * 导出excel文档
	 * 
	 * @param excelName
	 * @param rowName
	 * @param datasource
	 * @param out
	 * @throws Exception
	 */
	public static void exportExcel(String excelName, Map<String, String> rowName, List<Map<String, Object>> datasource,
			OutputStream out, String[] sort) throws Exception {
		// 创建HSSFWorkbook对象
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建HSSFSheet对象
		HSSFSheet sheet = wb.createSheet("sheet0");
		// 合并单元格
		int size = rowName.keySet().size();// 可能生成列数
		CellRangeAddress cra = new CellRangeAddress(0, 0, 0, size - 1); // 起始行, 终止行, 起始列, 终止列
		sheet.addMergedRegion(cra);

		// 创建HSSFRow对象
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 500);// 设置行高

		// 创建HSSFCell对象
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(excelName);

		// 第一行样式
		HSSFCellStyle firstStyle = createFirstRowCellStyle(wb);
		cell.setCellStyle(firstStyle);

		// 第二行样式
		HSSFCellStyle secondStyle = createSecondRowCellStyle(wb);

		// 创建HSSFRow对象
		HSSFRow row1 = sheet.createRow(1);
		row1.setHeight((short) 400);// 设置行高

		if(sort == null || sort.length == 0){
			sort = new String[rowName.size()];
			rowName.keySet().toArray(sort);
		}
		
		for (int i = 0; i < sort.length; i++) {// 列名称信息
			String value = rowName.get(sort[i]);
			sheet.setColumnWidth((short) i, (short) 3500);// 设置列宽
			HSSFCell cell1 = row1.createCell(i);
			cell1.setCellStyle(secondStyle);
			cell1.setCellValue(value);
		}

		int rownum = 2;// 行号
		if(datasource != null && datasource.size() > 0){
			for (Map<String, Object> map : datasource) {// 数据
				HSSFRow row2 = sheet.createRow(rownum);
				int cellnum = 0;
				for (int i = 0; i < sort.length; i++) {
					HSSFCell cell1 = row2.createCell(cellnum);
					cell1.setCellValue(map.get(sort[cellnum]) + "");
					cellnum++;
				}
				rownum++;
			}
		}
		wb.write(out);
		out.flush();
		out.close();
		wb.close();
	}

	/**
	 * 导入excel获取实体对象
	 * @param file
	 * @param row1Name
	 * @param className
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static List<Object> importExcelForEntity(MultipartFile file, Map<String, String> row1Name, String className) 
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Class<?> entity = Class.forName(className);
		List<Object> entityList = new ArrayList<Object>();
		List<Map<String, Object>> entitys = importExcel(file, row1Name);
		if(entitys != null && entitys.size() > 0){
			for(Map<String, Object> map : entitys){
				Object obj = entity.newInstance();
				Set<String> set = map.keySet();
				for(String key : set){
					Field field = entity.getDeclaredField(key);
					field.setAccessible(true);
					field.set(obj, map.get(key));
				}
				entityList.add(obj);
			}
		}
		return entityList;
	}
	
	@SuppressWarnings({ "resource", "deprecation" })
	public static List<Map<String, Object>> importExcel(MultipartFile file, Map<String, String> row1Name)
			throws IOException {
		// 装载流
		POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
		HSSFWorkbook hw = new HSSFWorkbook(fs);
		// 获取第一个sheet页
		HSSFSheet sheet = hw.getSheetAt(0);
		// 容器
		HSSFRow row = sheet.getRow(1);
		int lastCell = row.getLastCellNum();
		String[] fields = new String[lastCell];// 对应字段名称
		for (int i = 0; i < lastCell; i++) {
			Cell cell = row.getCell(i);
			String value = cell.getStringCellValue();
			fields[i] = row1Name.get(value);
		}
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();

		for (int j = 2; j <= sheet.getLastRowNum(); j++) {
			row = sheet.getRow(j);
			Map<String, Object> param = new HashMap<String, Object>();
			for (int i = 0; i < lastCell; i++) {
				Cell cell = row.getCell(i);
				Object value = (cell + "").trim();
				if (!UtilString.isEmpty(fields[i]) && cell != null) {
					switch (cell.getCellType()) {
					// 如果当前Cell的Type为NUMERIC
					case HSSFCell.CELL_TYPE_NUMERIC: {
						short format = cell.getCellStyle().getDataFormat();
						if(format == 14 || format == 31 || format == 57 || format == 58){ 	//excel中的时间格式
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			                double dou = cell.getNumericCellValue();  
			                Date date = DateUtil.getJavaDate(dou);  
			                value = sdf.format(date);  
						}
						// 判断当前的cell是否为Date
						else if (HSSFDateUtil.isCellDateFormatted(cell)) {  //先注释日期类型的转换，在实际测试中发现HSSFDateUtil.isCellDateFormatted(cell)只识别2014/02/02这种格式。
							// 如果是Date类型则，取得该Cell的Date值           // 对2014-02-02格式识别不出是日期格式
							Date date = cell.getDateCellValue();
							DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
							value= formater.format(date);
						} else { // 如果是纯数字
							// 取得当前Cell的数值
							value = NumberToTextConverter.toText(cell.getNumericCellValue()); 
						}
						break;
					}
					// 如果当前Cell的Type为STRIN
					case HSSFCell.CELL_TYPE_STRING:
						// 取得当前的Cell字符串
						value = cell.getStringCellValue().replaceAll("'", "''");
						break;
					case  HSSFCell.CELL_TYPE_BLANK:
						value = null;
						break;
					// 默认的Cell值
					default:{
						value = "";
					}

				}
				}
				param.put(fields[i], UtilString.isEmpty(value+"")? "":value);
			}
			ret.add(param);
		}
		return ret;
	}

	/**
	 * 获取Excel第一行样式
	 * 
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle createFirstRowCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		// style.setBorderBottom(BorderStyle.DOUBLE);; //下边框
		// style.setBorderLeft(BorderStyle.DOUBLE);//左边框
		// style.setBorderTop(BorderStyle.DOUBLE);//上边框
		// style.setBorderRight(BorderStyle.DOUBLE);//右边框
		style.setAlignment(HorizontalAlignment.CENTER); // 居中
		HSSFFont firstRowFont = getFirstRowHSSFFont(workbook);
		style.setFont(firstRowFont);
		return style;
	}

	/**
	 * 获取Excel第二行样式
	 * 
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle createSecondRowCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		// style.setBorderBottom(BorderStyle.DOUBLE);; //下边框
		// style.setBorderLeft(BorderStyle.DOUBLE);//左边框
		// style.setBorderTop(BorderStyle.DOUBLE);//上边框
		// style.setBorderRight(BorderStyle.DOUBLE);//右边框
		style.setAlignment(HorizontalAlignment.CENTER); // 居中
		HSSFFont secondRowFont = getSecondRowHSSFFont(workbook);
		style.setFont(secondRowFont);
		return style;
	}

	/**
	 * 获取Excel第一行字体样式
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFFont getFirstRowHSSFFont(HSSFWorkbook wb) {
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 16);// 设置字体大小
		font.setBold(true);// 粗体显示
		return font;
	}

	/**
	 * 获取Excel第二行字体样式
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFFont getSecondRowHSSFFont(HSSFWorkbook wb) {
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		font.setBold(true);// 粗体显示
		return font;
	}

	public static void main(String[] args) throws Exception {
//		String excelName = "部门信息列表";
		Map<String, String> rowName = new HashMap<>();
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
		// exportExcel(excelName, rowName, datasource, null);
	}

}
