$(function () {
  $('<div class="navbar-inner"><div class="container"><a class="brand" href="./">短点!</a></div></div>').appendTo($('#navbar'));

  var container = $('<div class="container"></div>').append($('<p>Coded and designed by <a href="https://github.com/nutzam?tab=members" target="_blank">Nutz Production Committee</a> © 2012</p>'))
  .append($('<p>Powered by <a href="https://github.com/nutzam/nutz" target="_blank">Nutz</a></p>'))
  .append($('<p>Thanks <a href="https://github.com/twitter/bootstrap" target="_blank">Bootstrap</a></p>'))
  .append($('<p id="site-qrcode" style="display: none"><img src="https://chart.googleapis.com/chart?chs=72x72&cht=qr&choe=UTF-8&chl=http%3A%2F%2Fwww.nutz.cn%2F" /></p>'))
  .append($('<p><a href="javascript:void(0);" id="site-qrcode-str">显示本网站QR Code</a></p>'));

  var ul =  $('<ul class="footer-links"></ul>').append($('<li><a href="http://nutzam.com" target="_blank">Nutz 官网地址</a></li>'))
  .append($('<li><a href="https://github.com/nutzam/nutz" target="_blank">Nutz Github</a></li>'))
  .append($('<li><a href="http://code.google.com/p/nutz" target="_blank">Nutz GoogleCode 首页</a></li>'))
  .append($('<li><a href="https://github.com/wendal/shortit" target="_blank">本站源码地址</a></li>'))
  .append($('<li><a href="http://nutz.cn/LD" target="_blank">Chrome插件</a></li>'))
  .append($('<li><a href="http://nutz.cn/index.jsp" target="_blank">收藏夹插件</a></li>'));
  container.append(ul).append($('<a href="http://www.miibeian.gov.cn" target="_blank" title="查看备案信息">苏ICP备 10226088号-17</a>')).appendTo($('.footer'));

  qrcodeToggle("site", "显示本站QR Code", "隐藏本站QR Code");

  disqus();

  googleAnalytics();
});

function qrcodeToggle(id, showStr, hideStr) {
  $("#" + id + "-qrcode-str").toggle(
    function () {
      $("#" + id + "-qrcode").show();
      $("#" + id + "-qrcode-str").html(hideStr);
    },
    function () {
      $("#" + id + "-qrcode").hide();
      $("#" + id + "-qrcode-str").html(showStr);
    }
  );
}

function disqus() {
  /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
  var disqus_shortname = 'shortit2';

  /* * * DON'T EDIT BELOW THIS LINE * * */
  (function() {
      var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
      dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';
      (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
  })();
}

function googleAnalytics() {
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36753226-1']);
  _gaq.push(['_setDomainName', 'nutz.cn']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
}
