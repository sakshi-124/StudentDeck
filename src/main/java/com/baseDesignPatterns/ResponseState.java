package com.baseDesignPatterns;

import java.util.List;

//Design Pattern - state
public interface ResponseState {	
	public int getStatusCode();

	public void setMessages(List<String> messages);
	
	public List<String> getMessages();

	void setResponseObject(Object obj);

	Object getResponseObject();

}
