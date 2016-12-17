<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm</title>
</head>
<body>

<form action="./" method="POST">
            <input type="text" name="year" />年
            <input type="text" name="month" />月      
            <input type="text" name="day" />日<br>
            <input type="submit" value="確認"/>
</form>
<%
String result = (String)request.getAttribute("result");
if (result != null) {
%>

<div><%= result %></div> 


<%
}
%>
<a href="index.jsp">TOPへ</a>
</body>
</html>