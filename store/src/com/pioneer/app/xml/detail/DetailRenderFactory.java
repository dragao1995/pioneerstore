package com.pioneer.app.xml.detail;

import org.apache.commons.collections.FastHashMap;

import com.pioneer.app.listrender.StreetRender;
import com.pioneer.app.util.ClassHelper;

public class DetailRenderFactory {

	private FastHashMap renders=new FastHashMap();
	private DetailRenderFactory(){
		init();
	}
	
	private void init(){
		//此信息从配置文件中读取
		//renders.put("table_name", class)
		renders.put("街道", StreetRender.class);
	}
	public IDetailDocRender getRender(String tbName){
		IDetailDocRender render=null;
		Class className=(Class)this.renders.get(tbName);
		if(null==className)return null;
		render=(IDetailDocRender)ClassHelper.newInstance(className.getName());		
		return render;
	}
	
	public static DetailRenderFactory getInst(){
		return _inst;
	}
	
	private static DetailRenderFactory _inst=new DetailRenderFactory();
	public static void main(String[] args) {
		System.out.println(StreetRender.class);
	}
}
