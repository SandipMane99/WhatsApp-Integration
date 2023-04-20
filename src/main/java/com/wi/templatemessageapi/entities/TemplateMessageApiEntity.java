package com.wi.templatemessageapi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "template_message_api_entity")
public class TemplateMessageApiEntity {
 
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String templateId;
	private String templateContent;
	private String mediaUrl;
	  
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
  
	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

}
