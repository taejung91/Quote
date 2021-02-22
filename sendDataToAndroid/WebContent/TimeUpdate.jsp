<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB"%>

<%
	request.setCharacterEncoding("UTF-8");

	
	String id = request.getParameter("id");
	int time = Integer.parseInt(request.getParameter("time"));
	String type = request.getParameter("type");

	ConnectDB connectDB = ConnectDB.getInstance();
	

	
	if(type.equals("time")){
		String returns = connectDB.one_Quote_Time(id, time);
		out.println(returns);
	}

	
		
	
	
%>