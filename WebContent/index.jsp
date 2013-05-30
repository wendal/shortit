<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String shortUrlPath = "http://nutz.cn/plugin/bookmarklet/create";
	request.setAttribute("basePath", shortUrlPath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="assets/img/logo/nutz-shortit-logo-16x16.png" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收藏短点|方便无处不在</title>
<style type="text/css">
#shortA {background-color: #FFF68F;padding: 3px;border: 1px gray solid;text-decoration: none;}
</style>
<script type="text/javascript">
	function init(){
	  document.getElementById('shortA').href = "javascript:function shortUrl() {var d = document, b = d.body, l = d.location;try {if (!l||!b) {return;}var divId = 'nutz_shortit';" + 
	  		"var div = d.getElementById(divId);if(!div){div = d.createElement('div');div.setAttribute('id', divId);"+
	  		"div.setAttribute('style', 'z-index:99999999;background-color:#FFFFC0;position:fixed;left:10px;top:10px;'+ 'width:188px;height:130px;border:3px solid #FFA54F;');}"+
	  		"var i = d.getElementById('nutzIframe');if (!i) {div.innerHTML='<div style=\"text-align: left;padding:2px; font-size: 13px; border-bottom: 1px solid #ccc; color: #333;\">"+
	  		"<a style=\"color:red;text-decoration: none;float:right;margin-right:5px;\" href=\"javascript:var i = top.document.getElementById(\\\'nutz_shortit\\\');i.parentNode.removeChild(i);void (0)\">[X]</a>"+
	  		"<a style=\"padding:0 10px; text-decoration: none;\" target=\"_blank\" href=\"http://nutz.cn\">nutz.cn</a></div>';"+
	  		"i = d.createElement('iframe');i.setAttribute('name', 'nutzIframe');i.setAttribute('id', 'nutzIframe');"+
	  		"i.setAttribute('style', 'display:block;'+ 'height:100px;border:none;width:188px;');div.appendChild(i);d.body.appendChild(div);}"+
	  		"i.contentWindow.document.write('<html><body style=&quot;color:#A00000;background-color:#FFFFC0;text-align:center;margin:0px;font-family:Georgia,Times,serif;font-size:16px;&quot;>'+ 'Shortening...</body>"+
	  		"</html>');i.setAttribute('src', '${basePath}?url=' + encodeURIComponent(l));} catch (e) {}}shortUrl();void (0)";
  }
</script>
</head>
<body onload="init()">
<h1>短点Bookmarklet</h1>
将
"<a id="shortA" href="#">短网址</a>"
这个链接拖到书签栏，点击一下，就会生成当前网页的短网址了。^_^
</body>
</html>
