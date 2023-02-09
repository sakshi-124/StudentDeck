package com.SessionManagement;

import javax.servlet.http.HttpSession;

public class SessionManager
{
    //HttpSession session;
    ///HttpServletRequest request;
    public void setSession( HttpSession session ,String userId,String userType,String userCity)
    {
        session.setAttribute("isLoggedIn" , true);
        session.setAttribute("loginId",userId);
        session.setAttribute("userType",userType);
        session.setAttribute("userCity",userCity);
    }
    public  String getLoggedUserId(HttpSession session)
    {
        return (String)session.getAttribute("loginId");
    }

    public String getUserType(HttpSession session){
        return (String) session.getAttribute("userType");
    }

    public  String getUserCity(HttpSession session)
    {
        return (String)session.getAttribute("userCity");
    }

    public void destroySession(HttpSession session)
    {
        session.setAttribute("isLoggedIn" , false);
        session.removeAttribute("loginId");
        session.invalidate();
    }
    
    public boolean checkIfValidSession(HttpSession session) {
    	if(session == null || getLoggedUserId(session) == null){
    		return false;
    	}
    	return true;
    }
}
