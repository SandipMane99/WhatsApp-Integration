package com.wi.excelreadapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wi.excelreadapi.entities.Details;
import com.wi.excelreadapi.repository.UserRepository;

@Service
public class UserServiceImpl<JSONObject> implements UserService {

	private UserRepository userRepo;

	@Override
	public Details create(Details user) {
		user.setNumber("+91"+user.getNumber());
		return this.userRepo.save(user);
	}

	// Send Message to particular user
	@Override
	public String sendMessageById(long userId, String template_id) {

		Details user1 = this.userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForObject("http://localhost:8080/notificationapi/sendMessage/{template_id}", user1,
				Details.class, template_id);	// 1

		return null;

	}

//	// Send Message to all Users
//	@Override
//	public String sendMessageAll() {
//
//		List<Details> allUser = this.userRepo.findAll();
//		long len = allUser.size();
//		String msg = "Happy Holi !!";
//		String json = "";
//		List<String> jsonList = new ArrayList<>();
//		for (int i = 0; i < len; i++) {
//			Details curr = allUser.get(i);
//			ObjectMapper mapper = new ObjectMapper();
//			String str = null;
//			try {
//				str = mapper.writeValueAsString(curr);
//			} catch (JsonProcessingException e) {
//				e.printStackTrace();
//			}
//			jsonList.add(str);
//
//		}
//
//		json = jsonList.toString();
//
//		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.postForObject("http://localhost:8081/notificationapi/sendMessageAll", json, String.class);
//
//		return null;
//	}

	
	// Call to sendMessageAll function of notificationAPI  Trail 1.0
	@Override
	public String sendMessageAll(String template_id) {

		List<Details> allUser = this.userRepo.findAll();
		long len = allUser.size();
		List<String> jsonList = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			Details curr = allUser.get(i);
//			curr.setMsg(msg);
			ObjectMapper mapper = new ObjectMapper();
			String str = null;
			try {
				str = mapper.writeValueAsString(curr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			jsonList.add(str);

		}

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForObject("http://localhost:8080/notificationapi/sendMessageAll/{template_id}",
				jsonList.toString(), String.class, template_id);	// 1

		return null;
	}

}
