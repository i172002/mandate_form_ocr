package com.example.demo.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

//import java.time.LocalDateTime;

public class Response {

	 private String timestamp;
	 private int status;
	 private String statusMsg;
	 @JsonProperty("Mandate Data")
	 private Object response;
	 
	 
	 
	 
	public Response(int status, String statusMsg, Object response) {
			super();
			this.status = status;
			this.statusMsg = statusMsg;
			this.response = response;
		}

	 
	 
	public Response(String timestamp, int status, String statusMsg, Object response) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.statusMsg = statusMsg;
		this.response = response;
	}
	


	



	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	



	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	} 
	@Override
	public String toString() {
		return "Response [timestamp=" + timestamp + ", status=" + status + ", statusMsg=" + statusMsg + ", response="
				+ response + "]";
	}
	
}
