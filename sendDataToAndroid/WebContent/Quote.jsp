<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB"%>
<%@ page import="sendDataToAndroid.One_Quote"%>
<%@ page import="org.json.simple.JSONObject"%>
 <%@ page import="org.json.simple.JSONArray"%>

<%
	request.setCharacterEncoding("UTF-8");

	String type = request.getParameter("type");
	
	ConnectDB connectDB = ConnectDB.getInstance();
	

	if(type.equals("random")){
		One_Quote one = new One_Quote();
		one = connectDB.randomQuote();
		
		JSONObject jsonMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		JSONObject jObject = new JSONObject();

	   jObject.put("quote", one.getQuote());
	   jObject.put("writer", one.getWriter());
	   jObject.put("subject", one.getSubject());
	   jObject.put("number", one.getNumber());
	   
	   jArray.add(0, jObject);
	   jsonMain.put("one_Quote", jArray);
	   
	
		out.println(jsonMain.toJSONString());
		out.flush();
		
	}
	else if(type.equals("all")){
		
		String result = connectDB.quoteList();
		out.println(result);
	
	}else{
		String result = connectDB.quoteSubjectList(type);
		out.println(result);
		
	}
		
	
	
	
	
		

	
%>