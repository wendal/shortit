<%@page import="cn.nutz.shortit.bean.DataEntry"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>文件下载</title>
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.1.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link href="assets/css/common.css" rel="stylesheet">
  </head>
  <body>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar" type="button">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="./">短点!</a>
        </div>
      </div>
    </div>
    <div class='container'>
      <h3>File Name : <%= ((DataEntry)request.getAttribute("obj")).filename() %></h3>
      <a href="api/read/bin?code=<%=Long.toHexString(((DataEntry)request.getAttribute("obj")).id) %>">下载</a>
    </div>
    <footer class="footer">
      <div class="container">
        <p>Coded and designed by <a href="https://github.com/nutzam?tab=members">Nutz Production Committee</a> © 2012</p>
        <p>Powered by <a href="https://github.com/nutzam/nutz">Nutz</a></p>
        <p>Thanks <a href="https://github.com/twitter/bootstrap">Bootstrap</a></p>
        <p><img src="https://chart.googleapis.com/chart?chs=72x72&cht=qr&choe=UTF-8&chl=http%3A%2F%2Fwww.nutz.cn%2F"/></p>
        <ul class="footer-links">
          <li><a href="http://nutzam.com">Nutz 官网地址</a></li>
          <li><a href="https://github.com/nutzam/nutz">Nutz Github</a></li>
          <li><a href="http://code.google.com/p/nutz">Nutz GoogleCode 首页</a></li>
          <li><a href="https://github.com/wendal/shortit">shortit</a></li>
        </ul>
      </div>
    </footer>
  </body>
</html>
