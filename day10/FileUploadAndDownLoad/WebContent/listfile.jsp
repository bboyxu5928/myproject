<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Map<String,String>map = (Map<String,String>)request.getAttribute("fileNameMap");
//读取key，根据key找寻value
Set<String> keySet = map.keySet();
for(String key:keySet){
	String value = map.get(key);
	out.print(value+"<a href="+request.getContextPath()+"/DownLoadServlet?filename="+key+">下载</a><br>");
}
%>
</body>
</html>