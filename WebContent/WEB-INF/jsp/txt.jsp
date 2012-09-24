<%@page import="cn.nutz.shortit.bean.DataEntry"%>
<%@page import="org.nutz.lang.Strings"%>
<%@page import="org.nutz.lang.Lang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文本信息</title>
</head>
<body>
<%=Strings.escapeHtml(((DataEntry)request.getAttribute("obj")).data) %>
</body>
</html>