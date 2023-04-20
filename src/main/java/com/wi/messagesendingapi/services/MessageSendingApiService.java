package com.wi.messagesendingapi.services;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import com.wi.excelreadapi.entities.Details;
import com.wi.messagesendingapi.entities.MessageSendingApiEntity;

public interface MessageSendingApiService {

	String saveRecord( MessageSendingApiEntity messageSendingApiEntity);

//	List<MessageSendingApiEntity> getAllRecords();

//	Optional<MessageSendingApiEntity> getRecordById(Long id);

//	List<MessageSendingApiDto> getAllRecords();
//	String getAllRecords();

//	String sendMessage(Details details);
	

	String sendMessageById(Details details, String template_id);
	
	// Trail
//	String sendMessageAll(String json);
	
	
	//Trial 1.0
	String sendMessageAll(String json, String template_id);

	
	String sendMessageToExcelUser(String json, String template_id) throws IOException;

	List<Integer> getUpdatedRecords(int total_excel_records);

	String sendMessageToRequestpayload(String json);

}
