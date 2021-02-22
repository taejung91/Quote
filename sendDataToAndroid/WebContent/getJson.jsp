<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="sendDataToAndroid.ConnectDB_Test"%>
<%@ page import="sendDataToAndroid.User"%>
<%@ page import="org.json.simple.JSONObject"%>
 <%@ page import="org.json.simple.JSONArray"%>


<%
	request.setCharacterEncoding("UTF-8");

	String name = request.getParameter("user_name");
/*	String age = request.getParameter("user_age");
	String job = request.getParameter("user_job");
*/

	
	if(name != ""){
	ConnectDB_Test connectDB = ConnectDB_Test.getInstance();
	
	User user = new User();
	
	
	
		user = connectDB.Select();
		
		JSONObject jsonMain = new JSONObject();
		JSONArray jArray = new JSONArray();
		JSONObject jObject = new JSONObject();

	   jObject.put("user_name", user.getName());
	   jObject.put("user_age", user.getAge());
	   jObject.put("user_job", user.getJob());
	   
	   jArray.add(0, jObject);
	   jsonMain.put("user_info", jArray);
	   
	
		out.println(jsonMain.toJSONString());
		out.flush();
	}
		
	
	
%>

