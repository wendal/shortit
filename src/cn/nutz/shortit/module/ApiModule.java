package cn.nutz.shortit.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Encoding;
import org.nutz.lang.Files;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.mvc.View;
import org.nutz.mvc.adaptor.VoidAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.ForwardView;
import org.nutz.mvc.view.HttpStatusView;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.repo.Base64;

import cn.nutz.shortit.Helper;

@IocBean
@Fail("http:500")
@Ok("raw")
public class ApiModule {

	@At("/?")
	public Object code(String code) {
		long id = Helper.string2Id(code);
		if (id < 0) {
			return 404;
		}
		return render(id);
	}

	@At("/api/create/url")
	public Object createUrl(@Param("data") String url) {
		if (url == null) 
			return _fail("err.data_emtry");
		if (url.length() > 1024*4)
			return _fail("err.url_too_big");
		File f = newFile();
		Files.write(f, url);
		Files.write(f.getPath() +".meta", "url:");
		return _ok(Helper.filePool.getFileId(f));
	}

	@At("/api/create/txt")
	@AdaptBy(type = VoidAdaptor.class)
	public Object createTxt(HttpServletRequest req) throws IOException {
		int fileSize = req.getContentLength();
		if (fileSize < 1)
			return _fail("err.data_emtry");
		if (fileSize > 1024 * 1024 * 10)
			return _fail("err.file_too_big");
		File f = newFile();
		Files.write(f, req.getInputStream());
		Files.write(f.getPath() +".meta", "txt:");
		return _ok(Helper.filePool.getFileId(f));
	}

	@At("/api/create/file")
	@AdaptBy(type = VoidAdaptor.class)
	public Object createFile(HttpServletRequest req) throws IOException {
		int fileSize = req.getContentLength();
		if (fileSize < 1)
			return _fail("err.data_emtry");
		if (fileSize > 1024 * 1024 * 10)
			return _fail("err.file_too_big");
		String fileName = req.getHeader("X-File-Name");
		if (Strings.isBlank(fileName))
			fileName = "file.bin";
		else {
			fileName = new String(Base64.decodeFast(fileName), "UTF8");
		}
		File f = newFile();
		Files.write(f, req.getInputStream());
		Files.write(f.getPath() +".meta", "bin:"+fileName);
		return _ok(Helper.filePool.getFileId(f));
	}

	@At("/api/read/?")
	public Object read(String code, HttpServletResponse resp)
			throws FileNotFoundException {
		long id = Helper.string2Id(code);
		if (id < 0)
			return HTTP_404;
		File f = Helper.filePool.getFile(id, ".bin");
		if (f == null)
			return HTTP_404;
		resp.setHeader("Content-Length", "" + f.length());
		resp.setContentType("text/plain; charset=x-user-defined");
		return new FileInputStream(f);
	}

	@At("/api/down/?")
	@Ok("void")
	public Object down(String code, HttpServletResponse resp)
			throws IOException {
		long id = Helper.string2Id(code);
		if (id < 0)
			return HTTP_404;
		File f = Helper.filePool.getFile(id, "");
		String meta = meta(f);
		if (meta == null || !meta.startsWith("bin:") || meta.length() < 5) {
			return HTTP_404;
		}

		String filename = meta.substring(4);
		filename = URLEncoder.encode(filename, Encoding.UTF8);

		resp.setHeader("Content-Length", "" + f.length());
		resp.setHeader("Content-Disposition", "attachment; filename=\""
				+ filename + "\"");
		Streams.writeAndClose(resp.getOutputStream(), Streams.fileIn(f));
		return null;
	}

	public View render(long id) {
		File f = Helper.filePool.getFile(id, "");
		String metaStr = meta(f);
		if (metaStr == null) {
			return HTTP_404;
		}
		if (metaStr.startsWith("url:")) {
			return new ServerRedirectView(Files.read(f));
		} else if (metaStr.startsWith("txt:")) {
			return new ForwardView("/txt.html");
		} else {
			return new ForwardView("/down.html");
		}
	}
	
	public String meta(File f) {
		if (f == null)
			return null;
		File meta = new File(f.getParentFile(), f.getName() + ".meta");
		if (!meta.exists() || meta.length() == 0)
			return null;
		return Files.read(meta);
	}

	private static final Object lock = new Object();

	public File newFile() {
		synchronized (lock) {
			return Helper.filePool.createFile("");
		}
	}
	
	static String _ok(long id) {
		return String.format("{'ok':true,'code':'%s'}", Helper.id2String(id));
	}
	
	static String _fail(String str) {
		return String.format("{'ok':false,'msg':'%s'}", str);
	}

	static final View HTTP_404 = new HttpStatusView(404);
}
