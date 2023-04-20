package com.wi.templatemessageapi.services;

import java.util.List;

import com.wi.excelreadapi.entities.Details;

public interface TemplateMessageApiService {

	String getTextTemplate(Details details, String template_id);

	
	String getTextTemplateToAllUsers(String json, String template_id);


	List<String> getAllTemplates();


	String getTemplateContent(String template_id);


	String getTextTemplateToRequestPayload(String json, String template_id);


	String getTemplateType(String template_id);

}
