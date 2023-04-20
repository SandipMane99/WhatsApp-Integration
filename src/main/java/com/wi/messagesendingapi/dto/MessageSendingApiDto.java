package com.wi.messagesendingapi.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class MessageSendingApiDto {

	private Long id;
	private String request_json;
	private String response_json;
	private LocalDateTime date;
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, id, request_json, response_json, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageSendingApiDto other = (MessageSendingApiDto) obj;
		return Objects.equals(date, other.date) && Objects.equals(id, other.id)
				&& Objects.equals(request_json, other.request_json)
				&& Objects.equals(response_json, other.response_json) && Objects.equals(status, other.status);
	}

	public String getRequest_json() {
		return request_json;
	}

	public void setRequest_json(String request_json) {
		this.request_json = request_json;
	}

	public String getResponse_json() {
		return response_json;
	}

	public void setResponse_json(String response_json) {
		this.response_json = response_json;
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

	@Override
	public String toString() {
		return "MessageSendingApiDto [id=" + id + ", request_json=" + request_json + ", response_json=" + response_json
				+ ", date=" + date + ", status=" + status + "]";
	}
}
