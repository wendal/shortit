package cn.nutz.shortit;

import java.util.regex.Pattern;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

//扫描当前package及子package中的模块类(包含入口方法的类就是模块类)
@Modules(scanPackage=true)
//默认Ok视图,设置为json
@Ok("json")
//默认Fail视图,统统走50x,不解释
@Fail("jsp:/50x.jsp")
//因为同时使用了js和注解式Ioc,所以需要复合加载器
@IocBy(type = ComboIocProvider.class, args = {
		//"*org.nutz.ioc.loader.json.JsonLoader", "ioc/",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "cn.nutz.shortit"})
//加载多语言
@Localization("msg")
//启动快结束时,需要进行一些初始化话工作,所以用SystemSetup来完成
@SetupBy(SystemSetup.class)
//主模块就不要写入口方法和@At了,空类就很好
public class MainModule {
	
	public static void main(String[] args) {
		Pattern p = Pattern.compile("^.+\\.(jsp|png|gif|jpg|js|css|jspx|jpeg|swf|ico|html)$");
		System.out.println(p.matcher("/index.html").find());
	}
	
}
