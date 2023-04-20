package com.wi.excelreadapi.dto;

import java.util.Objects;


public class details {
	
	private String name;
	
	private int userid;
	private int messageId;
	private long number;
	private int transactionId;

	public details() {
		super();
	}
	@Override
	public String toString() {
		return "name=" + name + ", userid=" + userid + ", messageId=" + messageId + ", number=" + number
				+ ", transactionId=" + transactionId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(messageId, name, number, transactionId, userid);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		details other = (details) obj;
		return messageId == other.messageId && Objects.equals(name, other.name) && number == other.number
				&& transactionId == other.transactionId && userid == other.userid;
	}
	public details(String name, int userid, int messageId, long number, int transactionId) {
		super();
		this.name = name;
		this.userid = userid;
		this.messageId = messageId;
		this.number = number;
		this.transactionId = transactionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

}
