package com.wi.messagesendingapi.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//@Data
//@AllArgsConstructor
@Entity
@Table(name = "message_sending_api_entity")
public class MessageSendingApiEntity {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int customer_id;
	private String user_name;
	private String number;
	private String sender;
	private String channel;
	private String type;
	private String templateId;
	private String message;
	private String mediaUrl;
	private String contentType;
	private String parameters;
	private String langCode;
	private String responseType;
	@Column(name = "date", insertable = false, updatable = false)
	private LocalDateTime date;
	private String status;
	private String responseId;
	
	public MessageSendingApiEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageSendingApiEntity(String user_name, String number, String sender, String channel, String type,
			String parameters, String templateId, String langCode, String responseType,
		String status, String responseId) {
	super();
	this.user_name = user_name;
	this.number = number;
	this.sender = sender;
	this.channel = channel;
	this.type = type;
	this.parameters = parameters;
	this.templateId = templateId;
	this.langCode = langCode;
	this.responseType = responseType;
	this.status = status;
	this.responseId = responseId;
}
	
	
	
	public MessageSendingApiEntity(String user_name, String number, String sender, String channel, String type,
			String parameters, String templateId, String message, String langCode, String responseType,
		String status, String responseId) {
	super();
	this.user_name = user_name;
	this.number = number;
	this.sender = sender;
	this.channel = channel;
	this.type = type;
	this.parameters = parameters;
	this.templateId = templateId;
	this.message = message;
	this.langCode = langCode;
	this.responseType = responseType;
	this.status = status;
	this.responseId = responseId;
}
	
	public MessageSendingApiEntity(String user_name, String number, String sender, String channel, String type,
			String parameters, String templateId, String message, String mediaUrl, String contentType, String langCode, String responseType,
		String status, String responseId) {
	super();
	this.user_name = user_name;
	this.number = number;
	this.sender = sender;
	this.channel = channel;
	this.type = type;
	this.parameters = parameters;
	this.templateId = templateId;
	this.message = message;
	this.mediaUrl = mediaUrl;
	this.contentType = contentType;
	this.langCode = langCode;
	this.responseType = responseType;
	this.status = status;
	this.responseId = responseId;
}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
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

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		contentType = contentType;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

}
