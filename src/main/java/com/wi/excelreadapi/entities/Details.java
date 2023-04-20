package com.wi.excelreadapi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

@Entity
@Table(name = "details")
public class Details {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // the field is auto generated using this
	private int user_id;
	private String user_name;
	private String number;

	public Details() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Details(String user_name, String number, int message_id, int template_id) {
		super();
		this.user_name = user_name;
		this.number = number;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String d) {
		this.number = d;
	}

}
