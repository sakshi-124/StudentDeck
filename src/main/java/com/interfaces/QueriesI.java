package com.interfaces;

import java.util.List;

import com.model.Blog;
import com.model.RentalList;

public interface QueriesI {

	List<String> writeDb(Object obj, List<String> messages2);
	
	List<String> update(Object obj, List<String> messages);

	void delete(Object obj, List<String> messages);

	List<String> fetchById(List<RentalList> rentals, int landlordId);

	List<String> fetchBlogsById(List<Blog> blogs,String userId,String blogId);
}
