package com.wi.templatemessageapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wi.excelreadapi.entities.Details;
import com.wi.templatemessageapi.services.TemplateMessageApiService;

@RestController
@RequestMapping("/templatemessageapi/templates")
public class TemplateMessageApiController {

	@Autowired
	TemplateMessageApiService templateMessageApiService;
	
	@PostMapping("/getTextTemplate/{template_id}")
	public String getTextTemplate(@RequestBody Details details, @PathVariable String template_id) {
		return templateMessageApiService.getTextTemplate(details, template_id);
	}
	
	@PostMapping("/getTextTemplateToAllUsers/{template_id}")
	public String getTextTemplateToAllUsers(@RequestBody String json, @PathVariable String template_id) {
		return templateMessageApiService.getTextTemplateToAllUsers(json, template_id);
	}
	
	@PostMapping("/getTextTemplateToRequestPayload/{template_id}")
	public String getTextTemplateToRequestPayload(@RequestBody String json, @PathVariable String template_id) {
		return templateMessageApiService.getTextTemplateToRequestPayload(json, template_id);
	}
	
	@GetMapping("/getAllTemplates")
	public List<String> getAllTemplates(){
		
		List<String> list = templateMessageApiService.getAllTemplates();
		
		return list;
	}
	
	
	@PostMapping("/getTemplateContent/{template_id}")
	public String getTemplateContent(@PathVariable String template_id) {
		
		return templateMessageApiService.getTemplateContent(template_id);
	}
	
	@PostMapping("/getTemplateType/{template_id}")
	public String getTemplateType(@PathVariable String template_id) {
		
		return templateMessageApiService.getTemplateType(template_id);
	}
	
//	@PostMapping("/getTextTemplateToRequestPayload/{template_id}")
//	public String getTextTemplateToRequestPayload(@PathVariable String template_id) {
//		
//		return templateMessageApiService.getTextTemplateToRequestPayload(template_id);
//	}
	
	
}
