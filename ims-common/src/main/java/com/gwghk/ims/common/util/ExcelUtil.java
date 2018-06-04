package com.gwghk.ims.common.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel工具类
 */
public final class ExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	public static final int MAX_ROW = 1000;

	/**
	 * 功能：包装excel导出response，使其支持excel输出（xlsx)
	 * 
	 * @param codedFileName
	 *            文件名
	 * @param request
	 *            request请求对象
	 * @param response
	 *            response请求对象
	 */
	public static void wrapExcelExportResponse(String codedFileName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		wrapExcelExportResponse(codedFileName, null, request, response);
	}

	public static void wrapExcelExportResponse(String codedFileName, String ext, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (ext == null) {
			ext = ".xlsx";
		}
		response.setContentType("application/vnd.ms-excel");
		String browse = BrowserUtil.checkBrowse(request);
		if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) { // 根据浏览器进行转码，使其支持中文文件名
			response.setHeader("content-disposition", "attachment;filename="
					+ java.net.URLEncoder.encode(codedFileName, "UTF-8") + DateUtil.toYyyymmddHhmmss() + ext);
		} else {
			String newtitle = new String(codedFileName.getBytes("UTF-8"), "ISO8859-1");
			response.setHeader("content-disposition",
					"attachment;filename=" + newtitle + DateUtil.toYyyymmddHhmmss() + ext);
		}
	}

	/**
	 * 功能:转换列类型
	 */
	public static String convertCellType(Cell cell) {
		String result = "";
		if (cell == null) {
			return result;
		}
		int cellType = cell.getCellType();
		switch (cellType) {
		case Cell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date d = cell.getDateCellValue();
				// DateFormat formater = new SimpleDateFormat("yyyy-MM-dd
				// HH:mm:ss");
				result = DateUtil.formatDateToString(d, "yyyy-MM-dd HH:mm:ss");

				break;
			}

			result = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			result = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula();
			break;
		}
		return result;
	}

	/**
	 * 功能：读取 Excel文件内容
	 */
	public static List<List<Object>> readExcelByInputStream(InputStream inputStream) throws Exception {
		return readExcelByInputStream(inputStream, null);
	}

	public static List<List<Object>> readExcelByInputStream(InputStream inputStream, String fileName) throws Exception {
		List<List<Object>> result = new ArrayList<List<Object>>();
		Workbook xswb = null;
		String fileExt = ".xlsx";
		if (StringUtils.isNotBlank(fileName)) {
			fileExt = fileName.substring(fileName.lastIndexOf("."));
		}
		if (".xls".equals(fileExt)) {
			xswb = new HSSFWorkbook(inputStream);
		} else {
			xswb = new XSSFWorkbook(inputStream);
		}

		Sheet sheet = xswb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		logger.debug("readExcelByInputStream->total rows :{}", rowNum);
		
		// 读取标题-获取首行列数-给中间的空单元格添加空值
		int firstColumnNumber = 0;
		if(rowNum >= 0){
			Row row = sheet.getRow(0);
			firstColumnNumber = row.getLastCellNum();
		}
		logger.debug("firstColumnNumber={}", firstColumnNumber);
		
		for (int i = 1; i < rowNum + 1; i++) {
			Row row = sheet.getRow(i);// row中只会封装有值的单元格
			List<Object> oneRowResult = new ArrayList<Object>();
			int cur = 0;
			for (Cell cell : row) {
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING: // 字符串
					oneRowResult.add(cell.getStringCellValue().trim());
					break;
				case XSSFCell.CELL_TYPE_NUMERIC: // 数字
					double strCell = cell.getNumericCellValue();
					oneRowResult.add(strCell);
					break;
				case XSSFCell.CELL_TYPE_BLANK: // 空值
					oneRowResult.add("");
					break;
				default:
					break;
				}
				cur++;
			}
			// 给中间的空单元格添加空值
			if (firstColumnNumber > cur) {
				for (int j = cur; j < firstColumnNumber; j++) {
					oneRowResult.add("");
				}
			}
			result.add(oneRowResult);
		}
		return result;
	}

}
