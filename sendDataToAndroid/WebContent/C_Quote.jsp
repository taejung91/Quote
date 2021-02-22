<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB"%>

<%
	request.setCharacterEncoding("UTF-8");

	String number = request.getParameter("number");
	String id = request.getParameter("id");
	String subject = request.getParameter("subject");
	String quote = request.getParameter("quote");
	String writer = request.getParameter("writer");
	String image = request.getParameter("image");
	String type = request.getParameter("type");

	ConnectDB connectDB = ConnectDB.getInstance();
	

	
	if(type.equals("insert")){
		connectDB.c_Quote_Insert(number, id, subject, quote, writer, image);
		
	}
	
	
		
	
	
%>
