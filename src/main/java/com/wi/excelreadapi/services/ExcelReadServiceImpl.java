package com.wi.excelreadapi.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wi.excelreadapi.convertexceltolist.Helper;
import com.wi.excelreadapi.entities.Details;

@Service
public class ExcelReadServiceImpl implements ExcelReadService {

	// Return all Excel users in list format
	@Override
	public int getExcelUser(MultipartFile file, String template_id) {

//		List<Details> listOfDetails = null;
		List<Object> listOfDetails = null;
		
		int total_records = 0;
		
		try {
//			listOfDetails = Helper.convetExcelToListOfDetails(file.getInputStream());
			
			listOfDetails = Helper.convetExcelToListOfDetails(file);

			String json = "";
			List<String> jsonList = new ArrayList<>();
			for (int i = 0; i < listOfDetails.size(); i++) {
				ObjectMapper mapper = new ObjectMapper();
				String str = null;
				try {
					str = mapper.writeValueAsString(listOfDetails.get(i));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				jsonList.add(str);
			}
      
			json = jsonList.toString();

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForObject("http://localhost:8080/notificationapi/sendMessageToExcelUser/{template_id}",
					json, String.class, template_id);	// 1
			
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return listOfDetails.size();
	}

	// Create Excel file
	@Override
	public byte[] createExcel(String templateId) {

		RestTemplate restTemplate = new RestTemplate();
		String templateContent = restTemplate.postForObject(
				"http://localhost:8080/templatemessageapi/templates/getTemplateContent/{template_id}", null,
				String.class, templateId);
		
		String templateType = restTemplate.postForObject(
				"http://localhost:8080/templatemessageapi/templates/getTemplateType/{template_id}", null,
				String.class, templateId);
		
		List<String> columns = new ArrayList<>();
		
		if(templateType.contains("text")) {
			
//			columns.add("user_id");
//			columns.add("user_name");
//			columns.add("number");
			
			columns.add("Sr.No.");
			columns.add("User Name");
			columns.add("Number");
			
		}
		else if(templateType.contains("media")) {
			
			columns.add("Sr.No.");
			columns.add("User Name");
			columns.add("Number");
			columns.add("Media Url");
			
		}
		
		 String pattern = "<([^>]+)>";
		    Pattern r = Pattern.compile(pattern);
		    Matcher m = r.matcher(templateContent);
		    while (m.find()) {
		      columns.add(m.group(1));
		    }
		    
		 // Create a new workbook
		    Workbook workbook = new XSSFWorkbook();

		    // Create a new sheet
		    Sheet sheet = workbook.createSheet("Sheet1");

		    // Create header row
		    Row headerRow = sheet.createRow(0);
//		    Cell headerCell = headerRow.createCell(0);
//		    headerCell.setCellValue("Column 1");

		    int temp = 0;
		    for(int i=0; i<columns.size(); i++) {
		    	
		    	Cell headerCell = headerRow.createCell(i);
		    	headerCell.setCellValue(columns.get(temp));
		    	
		    	temp++;
		    }
		    
		    
		    // Write the workbook to a byte array
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    
		    byte[] excelBytes = null;
		    
		    try {
				workbook.write(out);
			 
		    excelBytes = out.toByteArray();

		    // Clean up
		    out.close();
		    workbook.close();
		    
		    }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    // Return the byte array
		    return excelBytes;
		
	}
 
}
