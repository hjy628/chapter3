package org.hjy.chapter3.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelUtil {
	
	private static InputStream inputStream;
	
	private static Writer out;
	
	private static File tempFile;

	/**
	 * 生产Excel
	 * @param realPath
	 * @param heads
	 * @param record
	 */
	public static InputStream write(String fileName, String heads, List record) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("SLM_TABLE1");
		sheet.setDefaultColumnWidth(15);
		String[] head = heads.split(",");
		Row row = sheet.createRow(0);
		
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		
		CellStyle style = workbook.createCellStyle();
//		//设置单元格显示颜色
//		cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//		//样式的填充类型
//		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//居中对齐
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//设置边框
		style.setBorderTop((short) 1);
		style.setBorderBottom((short) 1);
		style.setBorderLeft((short) 1);
		style.setBorderRight((short) 1);
		style.setFont(font);
		
		for (int i = 0; i < head.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(head[i]);
		}
		
		for (int i = 0; i < record.size(); i++) {
			String[] content = (String[]) record.get(i);
			Row r = sheet.createRow(i + 1);
			for (int j = 0; j < head.length; j++) {
				Cell cell = r.createCell(j);
				cell.setCellStyle(style);
				cell.setCellValue(content[j]);
			}
		}
			
		try {
			// 得到临时文件
			Properties p = System.getProperties();
			String tempPath = p.getProperty("java.io.tmpdir");
			String separator = p.getProperty("file.separator");
			tempFile = new File(tempPath + separator + fileName);
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(tempFile), "UTF-8"));
			
			workbook.write(new FileOutputStream(tempFile));
						
			inputStream = new BufferedInputStream(new FileInputStream(tempFile));
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return inputStream;
	}
	

	
	public static String getCellValue(Cell cell) {
		String cellValue = "";
		DecimalFormat df = new DecimalFormat("#");
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				cellValue = cell.getRichStringCellValue().getString().trim();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();
					Date date = HSSFDateUtil.getJavaDate(d);
					SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					cellValue=sf.format(date);
				}else{  
					String val = cell.getNumericCellValue() + "";
					String regEx = ".0+$";
					Pattern pat = Pattern.compile(regEx);
					Matcher mat = pat.matcher(val);
					if (mat.find()) {
						cellValue = Integer.valueOf(val.substring(0,
								val.indexOf(".")))
								+ "";
					} else {
						cellValue = new BigDecimal(val).toPlainString() + "";
					}
				}
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				cellValue = cell.getCellFormula();
				break;
			default:
				cellValue = "";
			}
		}
		return cellValue;
	}

	/**
	 * 创建Excel文档
	 * 
	 * @param excelName
	 * @throws Exception
	 */
	public static void createExcel(String excelName) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream(excelName);
		wb.write(fileOut);
		fileOut.close();
	}

	/**
	 * 打开Excel文档
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Workbook openExcel(File file) {
		Workbook workbook = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}// 将excel文件转为输入流
//		POIFSFileSystem fs = null;
//		try {
//			fs = new POIFSFileSystem(in);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}// 构建POIFSFileSystem类对象，用输入流构建
		try {
			workbook = new HSSFWorkbook(in);
		} catch (Exception e) {
			try {
				workbook = new XSSFWorkbook(in);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}// 创建个workbook，根据POIFSFileSystem对象
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public static void updateExcel(HSSFWorkbook wb, String name)
			throws Exception {
		FileOutputStream out = new FileOutputStream(name);
		wb.write(out);
		out.close();
	}

	/**
	 * 插入行
	 * 
	 * @param sheet
	 * @param row
	 * @return
	 * @throws Exception
	 */
	public static HSSFSheet insertRow(HSSFSheet sheet, List list)
			throws Exception {
		int rowNum = sheet.getLastRowNum();
		HSSFRow row = sheet.createRow(rowNum + 1);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				row.createCell(i).setCellValue((String) list.get(i));
			}
		}
		return sheet;
	}

	/**
	 * 读取Excel
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static List<List<String>> readXLS(File file) throws Exception {
		Workbook workbook = null;// 读取源文件
		workbook = ExcelUtil.openExcel(file);
		 Cell cell = null;
		List<List<String>> outputList = new ArrayList<List<String>>();
		for (int i = 0; i < workbook.getSheetAt(0).getLastRowNum() + 1; i++) {
			Row row = workbook.getSheetAt(0).getRow(i);
			List<String> innerList = new ArrayList<String>();
			if (row != null) {
				for (int j = 0; j < 70; j++) {
					cell = row.getCell(j);
					if (cell != null) {
						if (0 == cell.getCellType()) {
							if (!HSSFDateUtil.isCellDateFormatted(cell)) {
								cell.setCellType(Cell.CELL_TYPE_STRING);
							}
						}
					}
					innerList.add(ExcelUtil.getCellValue(cell).trim());
				}
				outputList.add(innerList);
			}
		}
		return outputList;
	}
	
}