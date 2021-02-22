<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB"%>
<%@ page import="sendDataToAndroid.C_Quote"%>
<%@ page import="org.json.simple.JSONObject"%>
 <%@ page import="org.json.simple.JSONArray"%>

<%
	request.setCharacterEncoding("UTF-8");

	
	
	String subject = request.getParameter("subject");
	String quote = request.getParameter("quote");
	String writer = request.getParameter("writer");
	
	
	
	ConnectDB connectDB = ConnectDB.getInstance();
	

	
		connectDB.addQuote(subject, quote, writer);
		
	
	
	
	
		

	
%>