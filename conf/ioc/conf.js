var ioc = {
	// 读取配置文件
	dbConfig : {
			type : "org.nutz.ioc.impl.PropertiesProxy",
			fields : { paths : [ "db.properties"] } 
	}
};