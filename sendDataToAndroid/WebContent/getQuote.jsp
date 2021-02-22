<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB"%>
<%@ page import="sendDataToAndroid.One_Quote"%>
<%@ page import="org.json.simple.JSONObject"%>
 <%@ page import="org.json.simple.JSONArray"%>


<%
	request.setCharacterEncoding("UTF-8");

	String id = request.getParameter("id");

	
	
	ConnectDB connectDB = ConnectDB.getInstance();
	
	One_Quote one = new One_Quote();
	
	
	
		one = connectDB.one_Quote_Select(id);
		
		JSONObject jsonMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		JSONObject jObject = new JSONObject();

	   jObject.put("quote", one.getQuote());
	   jObject.put("writer", one.getWriter());
	   jObject.put("image", one.getImage());
	   jObject.put("time", one.getTime());
	   
	   jArray.add(0, jObject);
	   jsonMain.put("one_Quote", jArray);
	   
	
		out.println(jsonMain.toJSONString());
		out.flush();
	
		
	
	
%>