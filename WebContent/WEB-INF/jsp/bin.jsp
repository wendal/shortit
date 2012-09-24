<%@page import="cn.nutz.shortit.bean.DataEntry"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件下载</title>
</head>
<body>
<h3>FileName : <%=((DataEntry)request.getAttribute("obj")).data.substring(0, ((DataEntry)request.getAttribute("obj")).data.lastIndexOf(',')) %></h3>
<p></p>
<a href="api/read/bin?code=<%=Long.toHexString(((DataEntry)request.getAttribute("obj")).id) %>">下载</a>
</body>
</html>