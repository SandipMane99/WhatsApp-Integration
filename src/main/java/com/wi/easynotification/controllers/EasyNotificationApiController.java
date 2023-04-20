package com.wi.easynotification.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/easynotification")
public class EasyNotificationApiController {

	@PostMapping("/requestPayload")
	public String sendMessage(@RequestBody String JSON) {
		
		RestTemplate restTemplate = new RestTemplate();
 
		return restTemplate.postForObject("http://localhost:8080/notificationapi/sendMessage", JSON, String.class);   // 1
	}
	
}
