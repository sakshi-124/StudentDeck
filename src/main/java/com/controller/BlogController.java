package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.SessionManagement.SessionManager;
import com.StudentDeck.BlogOperations;
import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.database.BlogQueries;

@RestController
public class BlogController {
	BlogOperations blogOperations = new BlogOperations();

	
	@GetMapping(Constants.addBlogUrl)
	public ModelAndView getUserBlog( HttpServletRequest request)
	{
		SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
        
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.addBlogPage);
		return modelAndView;
	}

	@RequestMapping(Constants.addBlogUrl)
	public ModelAndView userBlogAdd(@RequestParam("title") String title,
									@RequestParam("blogDescription") String blogDescription,
									HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.addBlogPage);

		SessionManager sessionManager = new SessionManager();
		if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		sessionManager.getLoggedUserId(request.getSession());
		ResponseState response = blogOperations.insBlog( title.isEmpty()?"" :(title.substring(0,1).toUpperCase() + title.substring(1)),blogDescription,Integer.parseInt(sessionManager.getLoggedUserId(request.getSession())));

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("blogMsg", response.getMessages());
			return modelAndView;

		} else {
			return new ModelAndView(Constants.redirect+ Constants.userBlogUrl);

		}

	}

	@GetMapping(Constants.userCommunityUrl) @RequestMapping(Constants.userCommunityUrl)
	public ModelAndView getAllBlogs(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.userCommunityPage);
		SessionManager sessionManager = new SessionManager();
		if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		sessionManager.getLoggedUserId(request.getSession());
		ResponseState response = blogOperations.getUserBlogs(new BlogQueries(),"","");

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
		} else {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("blogs", response.getResponseObject());
		}
		return modelAndView;
	}

	@GetMapping(Constants.userBlogUrl) @RequestMapping(Constants.userBlogUrl)
	public ModelAndView getUserBlogs(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.userBlogPage);
		SessionManager sessionManager = new SessionManager();
		if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		sessionManager.getLoggedUserId(request.getSession());
		ResponseState response = blogOperations.getUserBlogs(new BlogQueries(),(sessionManager.getLoggedUserId(request.getSession())),"");

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
		} else {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("blogs", response.getResponseObject());
		}
		return modelAndView;
	}
	
	@RequestMapping(value = Constants.addCommentsUrl + "/{id}")
	public ModelAndView addComment(@PathVariable("id") String blogId ,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.addCommentPage);

		ResponseState response = blogOperations.getUserBlogs(new BlogQueries(),"",blogId);
		ResponseState response1 = blogOperations.getUserComments(blogId);

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
		} else {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("blogs", response.getResponseObject());
			modelAndView.addObject("comments", response1.getResponseObject());
		}
		return modelAndView;
	}
	@RequestMapping(value = Constants.getCommentsUrl +"/{id}")
	public ModelAndView commentsAdded(@RequestParam("blogComments") String blogComments,
									  @PathVariable("id") String blogId,
									  HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.addCommentPage);

		SessionManager sessionManager = new SessionManager();
		if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		ResponseState response = blogOperations.insBlogComment(blogComments,Integer.parseInt(blogId),Integer.parseInt(sessionManager.getLoggedUserId(request.getSession())));
		ResponseState response2 = blogOperations.getUserBlogs(new BlogQueries(),"",blogId);
		ResponseState response1 = blogOperations.getUserComments(blogId);

		if (response1.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
		} else {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("comments", response1.getResponseObject());
			modelAndView.addObject("blogs", response2.getResponseObject());
			modelAndView.addObject("comments", response1.getResponseObject());
		}
		return modelAndView;
	}
}
