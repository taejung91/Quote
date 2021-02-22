<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB_Test"%>

<%
	request.setCharacterEncoding("UTF-8");

	String name = request.getParameter("user_name");
	String age = request.getParameter("user_age");
	String job = request.getParameter("user_job");


	ConnectDB_Test connectDB = ConnectDB_Test.getInstance();
	
		String returns = connectDB.Insert(name, age, job);
		out.println(returns);
		
	
	
%>

