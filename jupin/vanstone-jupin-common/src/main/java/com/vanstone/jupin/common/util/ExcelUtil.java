/**
 * 
 */
package com.vanstone.jupin.common.util;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author shipeng
 *
 */
public class ExcelUtil {

	/**
	 * 获取字符串表示形式的值
	 * @param cell
	 * @return
	 */
	public static String getCellValueAsString(HSSFCell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			return null;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell != null ? Boolean.toString(cell.getBooleanCellValue()) : null;
		case HSSFCell.CELL_TYPE_ERROR:
			return null;
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell != null ? cell.getCellFormula() : null;
		case HSSFCell.CELL_TYPE_NUMERIC:
			return cell != null ? Double.toString(cell.getNumericCellValue()): null;
		case HSSFCell.CELL_TYPE_STRING:
			return cell != null ? cell.getStringCellValue() : null;
		default:
			throw new IllegalArgumentException();
		}
	}
}
