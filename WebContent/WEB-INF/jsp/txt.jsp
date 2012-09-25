<%@page import="cn.nutz.shortit.bean.DataEntry"%>
<%@page import="org.nutz.lang.Strings"%>
<%@page import="org.nutz.lang.Lang"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>文本信息</title>
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.1.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
        background: none repeat scroll 0 0 #EEE;
        color: #666;
      }

      .footer {
        padding: 20px 0;
        margin-top: 120px;
        border-top: 1px solid #e5e5e5;
        background-color: #f5f5f5;
      }

      .footer p {
        margin-bottom: 0;
        color: #777;
      }

      .footer-links {
        margin: 10px 0;
      }

      .footer-links li {
        display: inline;
        margin-right: 10px;
      }

      body > .navbar .brand {
        padding-right: 0;
        padding-left: 0;
        margin-left: 20px;
        float: left;
        font-weight: bold;
        color: #FFF;
        text-shadow: 0 1px 0 rgba(255,255,255,.1), 0 0 30px rgba(255,255,255,.125);
        -webkit-transition: all .2s linear;
        -moz-transition: all .2s linear;
        transition: all .2s linear;
      }
    </style>
  </head>
  <body>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="./">短点!</a>
        </div>
      </div>
    </div>
    <div class='container'>
      <pre><%= Strings.escapeHtml(((DataEntry)request.getAttribute("obj")).data) %></pre>
    </div>
    <footer class="footer">
      <div class="container">
        <p>Coded and designed by <a href="https://github.com/nutzam?tab=members">Nutz Production Committee</a> © 2012</p>
        <p>Powered by <a href="https://github.com/nutzam/nutz">Nutz</a></p>
        <p>Thanks <a href="https://github.com/twitter/bootstrap">Bootstrap</a>
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
