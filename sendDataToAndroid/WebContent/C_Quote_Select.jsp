<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB"%>

<%
	request.setCharacterEncoding("UTF-8");

	String number = request.getParameter("number");
	String id = request.getParameter("id");
	String type = request.getParameter("type");
	


	ConnectDB connectDB = ConnectDB.getInstance();
	

	if(type.equals("select")){
	
		String returns = connectDB.c_Quote_Select(id, number);
		out.println(returns);
	}
	else if(type.equals("delete")){
		connectDB.c_Quote_Delete(id, number);
	}
	else if(type.equals("f_Delete")){
		connectDB.f_Quote_Delete(id, number);
		
	}
	
	
	
		
	
	
%>
