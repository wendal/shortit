package cn.nutz.shortit.service;

import java.io.File;
import java.io.InputStream;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.service.IdEntityService;

import cn.nutz.shortit.Helper;
import cn.nutz.shortit.bean.DataEntry;

/**
 * 封装数据持久化的逻辑
 * @author wendal
 *
 */
@IocBean(fields="dao") //声明当前类为一个Ioc的bean,并注入dao字段(超类IdEntityService的字段)
public class DataEntryService extends IdEntityService<DataEntry> { //超类带泛型的,必须声明本类的泛型

	/**
	 * 通过in创建一个文件记录
	 */
	public DataEntry create(InputStream in, String fileName) {
		//从文件夹创建一个File对象
		File f = Helper.filePool.createFile(".bin");
		Files.write(f, in); // TODO 判断in的大小,防止过大的文件
		DataEntry entry = new DataEntry();
		entry.type = 2;
		//这里的data的格式是 "文件夹,文件id", 这样的设计够简单,但会存储相同的文件
		entry.data = fileName+","+Helper.filePool.getFileId(f);
		return dao().insert(entry);//必不重复
	}
	
	/**
	 * 根据文本创建一个记录, 可以是网址,也可以使普通文本
	 */
	public DataEntry create(String data) {
		data = data.trim();
		DataEntry entry = new DataEntry();
		entry.data = data;
		//带http/https/ftp的作为网址,其余的统统当成文本
		if (data.contains("\n")) {
			entry.type = 1;
		} else if (data.startsWith("http://") || data.startsWith("https://") || data.startsWith("ftp://")) {
			entry.type = 0;
		} else {
			entry.type = 1;
		}
		//先查一查,如果已经存在,那么就不需要插入了
		DataEntry en = fetch(Cnd.where("data", "=", data).and("type", "=", entry.type));
		if (en != null)
			return en;
		try {
			//这里会有插入失败的情况,因为其他线程也可能在插入相同的记录
			return dao().insert(entry);
		} catch (Exception e) {}
		//插入失败,那就肯定有重复的key,我们查一下就好了
		return fetch(Cnd.where("data", "=", data).and("type", "=", entry.type));
	}
	
}
