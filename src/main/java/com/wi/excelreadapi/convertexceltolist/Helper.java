package com.wi.excelreadapi.convertexceltolist;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.wi.excelreadapi.entities.Details;

public class Helper {

//	public static List<Details> convetExcelToListOfDetails(InputStream inputStream) {

	public static List convetExcelToListOfDetails(MultipartFile file) {

		List list = new ArrayList();

		try {

			InputStream is = file.getInputStream();

			// Read .xlsx Excel data
			if (FilenameUtils.getExtension(file.getOriginalFilename()).equals("xlsx")) {

				XSSFWorkbook workbook = new XSSFWorkbook(is);
				XSSFSheet xssfSheet = workbook.getSheet("Sheet1");

				XSSFRow row = null;

				int total_columns = xssfSheet.getRow(0).getLastCellNum();
				int column = 0;

				int i = 1;
				while ((row = xssfSheet.getRow(i)) != null) {

					Map<String, String> mapList = new HashMap();

					for (column = 0; column < total_columns; column++) {
						XSSFCell cell = row.getCell(column);
						cell.setCellType(CellType.STRING);

						mapList.put(xssfSheet.getRow(0).getCell(column).getStringCellValue(),
								row.getCell(column).getStringCellValue());
					}

					list.add(mapList);

					i++;
				}
			}

			// Read .xls Excel data
			else if (FilenameUtils.getExtension(file.getOriginalFilename()).equals("xls")) {

				HSSFWorkbook workbook = new HSSFWorkbook(is);
				HSSFSheet xssfSheet = workbook.getSheetAt(0);

				HSSFRow row = null;

				int total_columns = xssfSheet.getRow(0).getLastCellNum();
				int column = 0;

				int i = 1;
				while ((row = xssfSheet.getRow(i)) != null) {

					Map<String, String> mapList = new HashMap();

					for (column = 0; column < total_columns; column++) {
						HSSFCell cell = row.getCell(column);
						cell.setCellType(CellType.STRING);

						mapList.put(xssfSheet.getRow(0).getCell(column).getStringCellValue(),
								row.getCell(column).getStringCellValue());
					}

					list.add(mapList);

					i++;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
