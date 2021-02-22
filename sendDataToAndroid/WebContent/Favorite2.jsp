<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB"%>
<%@ page import="sendDataToAndroid.C_Quote"%>
<%@ page import="org.json.simple.JSONObject"%>
 <%@ page import="org.json.simple.JSONArray"%>

<%
	request.setCharacterEncoding("UTF-8");

	
	String id = request.getParameter("id");
	String subject = request.getParameter("subject");
	System.out.println(id + subject);
	
	
	ConnectDB connectDB = ConnectDB.getInstance();
	

	
		String result = connectDB.select_Subject(id, subject);
		
		out.println(result);
	
	
	
		

	
%>