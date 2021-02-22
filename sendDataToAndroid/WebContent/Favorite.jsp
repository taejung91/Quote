<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB"%>
<%@ page import="sendDataToAndroid.C_Quote"%>
<%@ page import="org.json.simple.JSONObject"%>
 <%@ page import="org.json.simple.JSONArray"%>

<%
	request.setCharacterEncoding("UTF-8");

	
	String id = request.getParameter("id");
	
	ConnectDB connectDB = ConnectDB.getInstance();
	

//	ArrayList<C_Quote> c_Quote = new ArrayList<C_Quote>();
	
	
		String result = connectDB.select(id);
		
		out.println(result);
	
	
	
		

	
%>