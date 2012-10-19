package cn.nutz.shortit.module;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Encoding;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.mvc.View;
import org.nutz.mvc.adaptor.VoidAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.HttpStatusView;
import org.nutz.mvc.view.JspView;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.mvc.view.ViewWrapper;
import org.nutz.repo.Base64;

import cn.nutz.shortit.Helper;
import cn.nutz.shortit.bean.DataEntry;
import cn.nutz.shortit.service.DataEntryService;

/**
 * 核心模块
 * @author wendal
 *
 */
@IocBean //声明为一个bean,默认名字为首字母小写,即coreModule
public class CoreModule {
	
	/*首页,直接跳转就好了*/
	@At("/index")
	@Ok("->:/index.html")
	public void index(){};
	
	/**
	 * 核心方法,根据短地址码,进行特定的跳转
	 * @param code 短地址码
	 * @return
	 */
	@At("/?")
	public View sdata(@Param("code")String code) {
		if (Strings.isBlank(code))//没code?唯一的可能性就是全部空格
			return new ServerRedirectView("/");
		DataEntry entry = query(code);
		if (entry == null)
			return HTTP_404;//找不到就一概404
		return renderDataEntry(entry);
	}
	
	/**
	 * 创建文本型记录
	 * @param data 文本,可以是网址或者普通文本
	 */
	@At("/api/create")
	@Ok("raw")
	public String create(@Param("data")String data) {
		if (Strings.isBlank(data))
			return "{'ok':false,'msg':'err.data_emtry'}";
		data = data.trim();
		if (data.length() > 500)
			return "{'ok':false,'msg':'err.data_too_long'}";
		return String.format("{'ok':true,'code':'%s'}", Long.toHexString(dataEntryService.create(data).id));
	}
	
	/**
	 * 创建文件型记录
	 * @param req 请求
	 */
	@At("/api/create/bin")
	@AdaptBy(type=VoidAdaptor.class)
	@Ok("raw")
	public String create(HttpServletRequest req) throws IOException {
		int fileSize = req.getContentLength();
		if (fileSize == 0)
			return "{'ok':false,'msg':'err.file_emtry'}";
		if (fileSize > 1024 * 1024)
			return "{'ok':false,'msg':'err.file_too_big'}";
		String fileName = req.getHeader("X-File-Name");
		if (Strings.isBlank(fileName))
			fileName = "file.bin";
		else {
			fileName = new String(Base64.decodeFast(fileName), "UTF8");
		}
		return String.format("{'ok':true,'code':'%s'}", Long.toHexString(dataEntryService.create(req.getInputStream(), fileName).id));
	}
	
	/**
	 * 下载文件
	 * @param code 短地址码
	 */
	@At("/api/read/bin")
	@Ok("void")
	public Object readBin(@Param("code")String code, HttpServletResponse resp) throws IOException{
		DataEntry entry = query(code);
		if (entry != null && entry.type == 2) {
			String filename = URLEncoder.encode(entry.filename(), Encoding.UTF8);
			File file = Helper.filePool.getFile(Long.parseLong(entry.fid()), ".bin");
			if (file != null && file.exists()) {
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
				resp.setHeader("Content-Length", "" + file.length());
				Streams.writeAndClose(resp.getOutputStream(), Streams.fileIn(file));
				return null;
			}
		}
		return HTTP_404; //一概404
	}
	
	/**
	 * 反查短地址码所对应的记录
	 * @param code 短地址码
	 */
	@At("/api/query")
	@Ok("json")
	public DataEntry query(@Param("code")String code) {
		try {
			return dataEntryService.fetch(Long.parseLong(code, 16));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * 获取特定的视图
	 */
	View renderDataEntry(DataEntry entry) {
		switch (entry.type) {
		case 0:
			return new ServerRedirectView(entry.data);
		case 1:
			return new ViewWrapper(new JspView("jsp.txt"), entry);
		default:
			return new ViewWrapper(new JspView("jsp.bin"), entry);
		}
	}
	
	@Inject
	DataEntryService dataEntryService;
	
	static final View HTTP_404 = new HttpStatusView(404);
}
