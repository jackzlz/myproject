/**
 * 
 */
package com.example.myproject.util;

import java.io.File;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * <p>
 * 类的描述
 * </p>
 *
 * @author zhenglz 2016年11月25日
 *
 */
public class ExcelUtil {

	public static Object[][] parseExcelFile(File excelFile) throws Exception {

		Workbook workbook = WorkbookFactory.create(excelFile);

		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		Object[][] datas = new Object[rowCount][];
		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			int colCount = row.getLastCellNum();
			Object[] rowData = new Object[colCount];
			for (int j = 0; j <= colCount; j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				Object value = getCellValue(cell);
				rowData[j] = value;
			}
			datas[i] = rowData;
		}
		return datas;
	}

	private static Object getCellValue(Cell cell) {
		Object val = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			val = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			val = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			val = cell.getNumericCellValue();
			break;
		}
		return val;
	}
}
