package com.repositoryMock;

import java.util.ArrayList;
import java.util.List;

import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.model.SuccessResponse;

public class UserQueriesMock extends QueriesTemplate{

	public ResponseState fetchById(Object obj, List<String> messages) {
		return new SuccessResponse();
	}
	
	public List<String> fetchByCity(List<Object> students, String city) {
		return new ArrayList<String>();
	}
}
