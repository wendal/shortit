package cn.nutz.shortit;

import org.nutz.dao.Dao;
import org.nutz.filepool.NutFilePool;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import cn.nutz.shortit.bean.DataEntry;

/**
 * 初始化表结构,创建文件池
 * @author wendal
 *
 */
public class SystemSetup implements Setup {

	public void init(NutConfig config) {
		//创建表,其实就一张表,所以直接create了
		config.getIoc().get(Dao.class).create(DataEntry.class, false);
		//在WEB-INF/rsdata下面放个文件池,用来存放文件型记录
		Helper.filePool = new NutFilePool(Mvcs.getServletContext().getRealPath("/WEB-INF") + "/rsdata");
	}
	
	public void destroy(NutConfig config) {
		// Nothing
	}
}
