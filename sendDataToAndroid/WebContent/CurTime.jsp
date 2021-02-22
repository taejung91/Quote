<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB"%>
<%@ page import="java.sql.Timestamp" %>

<%
	request.setCharacterEncoding("UTF-8");

	
	String id = request.getParameter("id");
	
	ConnectDB connectDB = ConnectDB.getInstance();
	

		String time = connectDB.curTime(id);
		out.println(String.valueOf(time));
	

	
		
	
	
%>