package com.wi.excelreadapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wi.excelreadapi.entities.Details;
import com.wi.excelreadapi.services.UserService;


@RestController
@RequestMapping("/databaseRead") 
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	@PostMapping("/add")
	public ResponseEntity<Details> creatUser(@RequestBody Details user){
		Details user1 = this.userservice.create(user);
		return new ResponseEntity<>(user1 ,HttpStatus.OK);
	}
	
	@GetMapping("/sendMessage/{userId}/{template_id}")
	public String getById (@PathVariable long userId, @PathVariable String template_id){
		String string = this.userservice.sendMessageById(userId, template_id);
		return null;
	}
	
	
	// Trail 
//	@GetMapping("/sendMessage")
//	public String sendMessage (){
////		System.out.println(this.userservice.sendMessage(userId));
//		String string = this.userservice.sendMessageAll();
//		return null;
//	}
	
	
	// Trail 1.0
	@GetMapping("/sendMessage/{template_id}")
	public String sendMessage (@PathVariable String template_id){
		String string = this.userservice.sendMessageAll(template_id);
		return null;
	}
	
	 
	@GetMapping("/sendMessage")
	public ModelAndView sendMessage() {
		
		ModelAndView modelAndView = new ModelAndView("sendMessage");
		
		return modelAndView;
	}
	
}
