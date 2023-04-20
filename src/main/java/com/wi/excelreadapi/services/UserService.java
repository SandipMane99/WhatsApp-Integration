package com.wi.excelreadapi.services;

import com.wi.excelreadapi.entities.Details;

public interface UserService {
	
	String sendMessageById(long userId, String template_id);
	
	// Trail
//	String sendMessageAll();
	
	// Trail 1.0
	String sendMessageAll(String template_id);
	
	
	Details create(Details user);

}
