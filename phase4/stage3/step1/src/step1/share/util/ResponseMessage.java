package step1.share.util;

import com.google.gson.Gson;

public class ResponseMessage {
	//
	private String serviceName; 
	private String value;
	private boolean success; 
	private String reason; 
	
	public ResponseMessage(String serviceName, String value) {
		//
		this.serviceName = serviceName; 
		this.value = value; 
		this.success = true; 
	}
		
	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append("Service name:").append(serviceName); 
		builder.append(", value:").append(value);
		
		return builder.toString(); 
	}
	
	public String toJson() {
		// 
		return (new Gson()).toJson(this); 
	}
	
	public static ResponseMessage fromJson(String json) {
		// 
		return (new Gson()).fromJson(json, ResponseMessage.class); 
	}
	
	public static ResponseMessage getSample() {
		// 
		return new ResponseMessage("setBaseTime", "10"); 
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}