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
	int time = Integer.parseInt(request.getParameter("time"));
	String type = request.getParameter("type");

	ConnectDB connectDB = ConnectDB.getInstance();
	

	
	if(type.equals("all")){
		String returns = connectDB.one_Quote_Insert_Update(number, id, subject, quote, writer, image, time);
		out.println(returns);
	}
	else if(type.equals("time")){
		connectDB.one_Quote_Time(id, time);
	}
	
		
	
	
%>

