<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String shortUrlPath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/plugin/bookmarklet/create";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="assets/img/logo/nutz-shortit-logo-16x16.png" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收藏短点|方便无处不在</title>
<style type="text/css">
#shortA {
  background-color: #FFF68F;
  padding: 3px;
  border: 1px gray solid;
  text-decoration: none;
}
</style>
</head>
<body>
<h1>短点Bookmarklet</h1>
将
"<a id="shortA" href="javascript:function shortUrl(){var d=document,z=d.createElement('scr'+'ipt'),b=d.body,l=d.location;try{if(!b)throw(0);if(!l){return;}var i=d.getElementById('nutzIframe');if(!i){i=d.createElement('iframe');i.setAttribute('name','nutzIframe');i.setAttribute('id','nutzIframe');i.setAttribute('style','z-index:99999999;position:fixed;left:10px;top:10px;width:168px;'+'width:168px;height:140px;border:3px solid #FFA54F;');d.body.appendChild(i);}else{d.getElementByTagName('body')[0].innerHTML = '';}i.contentWindow.document.write('<html><body style=&quot;color:#A00000;background-color:#FFFFC0;text-align:center;margin:0px;font-family:Georgia,Times,serif;font-size:16px;&quot;>'+'<div style=&quot;text-align: left;padding:2px;margin: 0 auto 15px auto; font-size: 13px; border-bottom: 1px solid #ccc; color: #333;&quot;><a style=&quot;color:red;float:right;margin-right:5px;&quot; href=&quot;javascript:var i = top.document.getElementById(\'nutzIframe\');i.parentNode.removeChild(i);&quot;>[X]</a><a target=&quot;_blank&quot; href=&quot;http://nutz.cn&quot;>nutz.cn</a></div>Shortening...</body></html>');z.setAttribute('src','<%=shortUrlPath %>?url='+encodeURIComponent(l));b.appendChild(z);}catch(e){}}shortUrl();void(0)">短网址</a>"
这个链接拖到书签栏，点击一下，就会生成当前网页的短网址了。^_^
</body>
</html>