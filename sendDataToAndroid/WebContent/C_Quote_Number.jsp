<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB"%>

<%
	request.setCharacterEncoding("UTF-8");

	String number = request.getParameter("number");
	String id = request.getParameter("id");
	


	ConnectDB connectDB = ConnectDB.getInstance();
	
	
		String returns = connectDB.c_Quote_Select(id, number);
		out.println(returns);
	
	
	
		
	
	
%>