<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
body{margin: 0;background-color:#FFFFC0;}
#main{padding:5px;text-align: center;}
.my_clip_button { width:50px; height:30px;line-height:30px; text-align:center; border:1px solid #FFA54F; background-color:#FFFFC0; cursor:default; font-size:11pt; }
.my_clip_button.hover { background-color:#eee; }
.my_clip_button.active { background-color:#aaa; }
#urlImage{float: left;width: 72px;height: 72px;padding: 0 5px;}
#d_clip_container{width: 60px;float: left;padding-top: 20px;}
#con-img{width: 145px;margin: 0 auto;}
</style>
<script type="text/javascript" src="${base }/js/ZeroClipboard.js"></script>
<script type="text/javascript">
ZeroClipboard.moviePath = '${base }/js/ZeroClipboard.swf';
</script>
</head>
<body>
  <div id="main">
    <div id="shortUrl">${url}</div>
    <div id="con-img">
      <div id="urlImage">
      </div>
      <div id="d_clip_container">
        <div id="d_clip_button" class="my_clip_button">
          <b>Copy</b>
        </div>
      </div>
    </div>
  </div>
  <script language="JavaScript">
      var clip = null;
      function $(id) {
        return document.getElementById(id);
      }
      function init() {
        clip = new ZeroClipboard.Client();
        clip.setHandCursor(true);
        clip.addEventListener('mouseOver', function(client) {
          clip.setText($('shortUrl').innerHTML);
        });
        clip.glue('d_clip_button', 'd_clip_container');
      }
      init();
      var img = document.createElement('img');
      img.src = 'https://chart.googleapis.com/chart?chs=72x72&cht=qr&choe=UTF-8&chl=${imageUrl }';
      $('urlImage').appendChild(img);
    </script>
</body>
</html>