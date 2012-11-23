package cn.nutz.shortit.module;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import cn.nutz.shortit.Helper;

@IocBean
public class PluginModule {

	@At("/plugin/bookmarklet")
	@Ok("jsp:/index.jsp")
	public void showBookmarklet() {
	}
	
	@At("/plugin/bookmarklet/create")
	@Ok("raw")
	public Object createUrlForBookmarklet(@Param("url") String url) throws UnsupportedEncodingException {
		long code = Helper.createUrl(url);
		if (code == -1) {
			return "";
		}
		return String.format(iframeStr, code, "http://nutz.cn/" + code,
                URLEncoder.encode("http://nutz.cn/" + code, "utf-8"));
	}

	static final String	iframeStr	= "(function(){"
			+ "var i = document.getElementById('nutzIframe');"
			+ "if (!i) {"
			+ "i = document.createElement('iframe');"
			+ "i.setAttribute('name', 'nutzIframe');"
			+ "i.setAttribute('id', 'nutzIframe');"
			+ "i.setAttribute('style', 'z-index: 99999999; position: fixed;left:10px;top:10px;width:168px;'"
			+ "+ 'width:168px;height: 140px; border: 3px solid #FFA54F;');"
			+ "document.body.appendChild(i);"
			+ "}else{i.contentWindow.document.getElementsByTagName('body')[0].innerHTML = '';}"
			+ "i.contentWindow.document"
			+ ".write('<html><body style=\"color: #A00000; background-color: #FFFFC0; text-align: center; margin: 0px; font-family: Georgia, Times, serif; font-size: 26px;\">'"
			+ "+ '<div style=\"text-align: left; padding: 2px; margin: 0 auto 15px auto; font-size: 13px; border-bottom: 1px solid #ccc; color: #333;\">"
			+ "<a style=\"color:red;float:right;margin-right:5px;\" href=\"javascript:var i = top.document.getElementById(\\'nutzIframe\\');i.parentNode.removeChild(i);\">[X]</a>"
			+ "<a target=\"_blank\" href=\"http://nutz.cn\">nutz.cn</a></div>'"
			+ "+ '<p style=\"font-size:13px\">http://nutz.cn/%s</p>"
			+ "<div>"
			+ "<img src=\"https://chart.googleapis.com/chart?chs=72x72&amp;cht=qr&amp;choe=UTF-8&amp;chl=%s\"/>"
			+ "</div>'" + "+ '</body></html>');" + "})();";
}
