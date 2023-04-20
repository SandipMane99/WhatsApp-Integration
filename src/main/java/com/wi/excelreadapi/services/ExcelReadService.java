package com.wi.excelreadapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelReadService {

	int getExcelUser(MultipartFile file, String template_id) throws IOException;

	byte[] createExcel(String templateId);

}
