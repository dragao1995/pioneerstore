package com.pioneer.app.xml.delete;

import org.apache.commons.collections.FastHashMap;

import com.pioneer.app.listrender.StreetRender;
import com.pioneer.app.util.ClassHelper;

/**
 * @name :  在删除数据的同时，如果有其他关联的操作，在此处注册。
 * @URL : 
 * @URL_Parameter :  
 * @author Administrator
 * @version : v 1.0
 * @description : 
 */
public class DelRenderFactory {

	private FastHashMap renders=new FastHashMap();
	private DelRenderFactory(){
		init();
	}
	
	private void init(){
		//此信息从配置文件中读取
		//renders.put("table_name", class)
		
	}
	public IDelDocRender getRender(String tbName){
		IDelDocRender render=null;
		Class className=(Class)this.renders.get(tbName);
		if(null!=className)
			render=(IDelDocRender)ClassHelper.newInstance(className.getName());		
		return render;
	}
	
	public static DelRenderFactory getInst(){
		return _inst;
	}
	
	private static DelRenderFactory _inst=new DelRenderFactory();
	public static void main(String[] args) {
		System.out.println(StreetRender.class);
	}
}
