package cn.nutz.shortit;

import org.nutz.filepool.NutFilePool;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

/**
 * 初始化表结构,创建文件池
 * @author wendal
 *
 */
public class SystemSetup implements Setup {

	public void init(NutConfig config) {
		//在WEB-INF/rsdata下面放个文件池,用来存放记录
		Helper.filePool = new NutFilePool(Mvcs.getServletContext().getRealPath("/WEB-INF") + "/rsdata");
	}
	
	public void destroy(NutConfig config) {
		// Nothing
	}
}
