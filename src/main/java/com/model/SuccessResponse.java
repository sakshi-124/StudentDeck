package com.model;

import java.util.ArrayList;
import java.util.List;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;

public class SuccessResponse implements ResponseState {
	private List<String> messages = new ArrayList<String>();
	private Object object = null;


	@Override
	public int getStatusCode() {
		return Constants.status200;
	}

	@Override
	public void setMessages(List<String> message) {
		messages.addAll(message);
	}

	@Override
	public List<String> getMessages() {
		return messages;
	}
	
	@Override
	public void setResponseObject(Object obj) {
		object = obj;
	}
	
	@Override
	public Object getResponseObject() {
		return object;
	}

}
