package com.wi.messagesendingapi.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wi.excelreadapi.entities.Details;
import com.wi.messagesendingapi.entities.MessageSendingApiEntity;
import com.wi.messagesendingapi.services.MessageSendingApiService;

@RestController
@RequestMapping("/notificationapi")
public class MessageSendingApiController {

	@Autowired
	MessageSendingApiService messageSendingApiService;

//	@GetMapping("/show")
//	public List<MessageSendingApiEntity> getAllRecords() {
//
//		List<MessageSendingApiEntity> messageSendingApiEntity = this.messageSendingApiService.getAllRecords();
//
////		System.out.println(messageSendingApiEntity2.toString());
//
//		return messageSendingApiEntity;
//	}
	
	
//	@GetMapping("/getrecord/{id}")
//	public Optional<MessageSendingApiEntity> disp1(@PathVariable Long id) {
//
//		Optional<MessageSendingApiEntity> messageSendingApiEntity2 = this.messageSendingApiService.getRecordById(id);
//
//		return messageSendingApiEntity2;
//	}
//
//	
//	@GetMapping("/getallrecords")
//	public List<MessageSendingApiDto> getAllRecords(@ModelAttribute MessageSendingApiEntity messageSendingApiEntity) {
//		
//		return this.messageSendingApiService.getAllRecords();
//	}
	
	@PostMapping("/addrecord")
	public String addRecord(@RequestBody MessageSendingApiEntity messageSendingApiEntity) {

		return this.messageSendingApiService.saveRecord(messageSendingApiEntity);
	}
 
	
	// call to SendMessageById Service(DB User)
	
	@PostMapping("/sendMessage/{template_id}")
	public  String sendMessage(@RequestBody Details details, @PathVariable String template_id) {
		String msg = this.messageSendingApiService.sendMessageById(details, template_id);
		return null;
	}
	  
	
	// Call to SendMessageAll Service(DB Users)    Trail 1.0
	
	@PostMapping("/sendMessageAll/{template_id}")
	public  String sendMessage(@RequestBody String json, @PathVariable String  template_id) {
		String msg = this.messageSendingApiService.sendMessageAll(json, template_id);
		return null;
	}
	   
	
	// Call to SendMessageToExcelUser Service(Excel users)
	
	@PostMapping("/sendMessageToExcelUser/{template_id}")
	public  String sendMessageToExcelUser(@RequestBody String json, @PathVariable String template_id) throws IOException {
		
		String msg = this.messageSendingApiService.sendMessageToExcelUser(json, template_id);
		return null;
	}
	
	
	@PostMapping("/getUpdatedRecords")
	public List<Integer> getUpdatedRecords(@RequestBody int total_excel_records){

//		System.out.println("Records = " + total_excel_records);
		
		List<Integer> list = this.messageSendingApiService.getUpdatedRecords(total_excel_records);
		
		return list;
	}
	
	
	@PostMapping("/sendMessage")
	public String sendMessageToRequestpayload(@RequestBody String JSON) {
		
		return this.messageSendingApiService.sendMessageToRequestpayload(JSON.toString());
	}
	 
}
