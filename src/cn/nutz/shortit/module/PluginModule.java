package cn.nutz.shortit.module;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

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
	@Ok("jsp:/iframe.jsp")
	public void createUrlForBookmarklet(HttpServletRequest req, @Param("url") String url) throws UnsupportedEncodingException {
		long code = Helper.createUrl(url);
		if (code == -1) {
			return ;
		}
		String shortCode = Helper.id2String(code);
		String shortUrl = "http://nutz.cn/" + shortCode;
		req.setAttribute("url", shortUrl);
		req.setAttribute("imageUrl", URLEncoder.encode(shortUrl, "utf-8"));
	}
	
}
