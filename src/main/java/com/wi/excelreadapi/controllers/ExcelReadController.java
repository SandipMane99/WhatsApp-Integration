package com.wi.excelreadapi.controllers;

import java.io.IOException;
import java.util.List;

//import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.wi.excelreadapi.services.ExcelReadService;

@Controller
@RequestMapping("/excelReadApi")
//@CrossOrigin("*")
public class ExcelReadController {

	
	  @Autowired
	  private ExcelReadService excelReadService;
	 
//	@GetMapping("/excelRead/{template_id}")
//	public List<Details> readExcel(@RequestParam("file") MultipartFile file, @PathVariable String template_id) throws IOException {
//		
//		List<Details> excelUser = this.excelReadService.getExcelUser(file, template_id);
//		 
//		String json = "";
//		List<String> jsonList = new ArrayList<>();
//		for (int i = 0; i < excelUser.size(); i++) {
//			Details curr = excelUser.get(i);
//			ObjectMapper mapper = new ObjectMapper();
//			String str = null;
//			try {
//				str = mapper.writeValueAsString(curr);
//			} catch (JsonProcessingException e) {
//				e.printStackTrace();
//			}
//			jsonList.add(str);
//		} 
//		   
//		json = jsonList.toString();
//		
//		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.postForObject("http://localhost:8081/notificationapi/sendMessageToExcelUser", json, String.class);
//		
//		return excelUser;
//		
//	}

	// Trial 1.0
//	@GetMapping("/excelRead/{template_id}")
//	public String readExcel(@RequestParam("file") MultipartFile file, @PathVariable String template_id)
//			throws IOException {
// 
//		System.out.println(template_id);
//		
//		System.out.println("Hello!!!!!!");
//		   
//		this.excelReadService.getExcelUser(file, template_id);
// 
//		return template_id;
//	}
 
	
	private boolean flag = false;
	
	private int total_excel_records = 0;
	
	
	// Trail 2.0
	@PostMapping(value  = "/excelRead",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
	public RedirectView readExcel(@RequestParam("file") MultipartFile file, @RequestParam String template_id)
			throws IOException {
 
		// Total count of Excel users
		total_excel_records = this.excelReadService.getExcelUser(file, template_id);
   
		flag = true;
		
		return new RedirectView("http://localhost:8080/excelReadApi/downloadExcel");
	}
	 
	 
	@GetMapping("/downloadExcel")
	
	public ModelAndView download() {
		
		ModelAndView modelAndView = null;
		 
		if(flag) {
			
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("http://localhost:8080/templatemessageapi/templates/getAllTemplates", List.class);	// 2
			 
			// Send total count of Excel users to getUpdatedRecords
			List<Integer> status_count = restTemplate.postForObject("http://localhost:8080/notificationapi/getUpdatedRecords", total_excel_records, List.class);	// 1
			
			modelAndView = new ModelAndView("excel");
			modelAndView.addObject("listOfTemplates", list);
			modelAndView.addObject("sentCount", "Total Sent Messages: " + status_count.get(0));
			modelAndView.addObject("unSentCount", "Total UnSent Messages: " + status_count.get(1));
			
			flag = false;
		}
		else {
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("http://localhost:8080/templatemessageapi/templates/getAllTemplates", List.class);	// 2
			 
			modelAndView = new ModelAndView("excel");
			modelAndView.addObject("listOfTemplates", list);
			
		}
		
		return modelAndView;
	}
	
	@GetMapping("/download")
	public ResponseEntity<byte[]> downloadFile(@RequestParam("template_id") String templateId) {
		// Load file as Resource
//		Resource resource = new FileSystemResource(
//				"C:\\Users\\Netwin\\Desktop\\notification_api\\Message API\\message-testing-api\\src\\main\\java\\com\\message\\api\\data\\User Details Excel.xls");
		
//		Resource resource = new FileSystemResource(
//				"E:\\WI\\WhatsappIntegration\\src\\main\\java\\com\\wi\\excelreadapi\\data\\User Details Excel.xls");
//		
//
//		// Try to determine file's content type
//		String contentType = null;
//		try {
//			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//		} catch (IOException ex) {
//		}
// 
//		// Fallback to the default content type if type could not be determined
//		if (contentType == null) {
//			contentType = "application/octet-stream";
//		}
//
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//				.body(resource);
		
		byte[] excelBytes = this.excelReadService.createExcel(templateId);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentLength(excelBytes.length);
	    headers.setContentDispositionFormData("attachment", "User Details Excel.xlsx");

	    new RedirectView("http://localhost:8080/excelReadApi/downloadExcel");
	    
	    return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
		
		
		
		
		
		
	}

}
