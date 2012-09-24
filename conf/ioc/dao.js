var ioc = {
	dataSource : {	
		type : "com.alibaba.druid.pool.DruidDataSource",
		fields : {
			driverClassName : { java : "$dbConfig.get('db-driver')" },
			url : { java : "$dbConfig.get('db-url')" },
			username : { java : "$dbConfig.get('db-username')" },
			password : { java : "$dbConfig.get('db-password')" },
		},
		events : {
			create : "init",
			depose : "close"
		}
	},
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer : "dataSource"}]
	}
};