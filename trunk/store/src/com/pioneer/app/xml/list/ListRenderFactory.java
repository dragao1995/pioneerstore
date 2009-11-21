package com.pioneer.app.xml.list;

import org.apache.commons.collections.FastHashMap;

import com.pioneer.app.listrender.StreetRender;
import com.pioneer.app.util.ClassHelper;

public class ListRenderFactory {

	private FastHashMap renders=new FastHashMap();
	private ListRenderFactory(){
		init();
	}
	
	private void init(){
		//renders.put("table_name", class)
		renders.put("街道", StreetRender.class);
	}
	public IListDocRender getRender(String tbName){
		IListDocRender render=null;
		Class className=(Class)this.renders.get(tbName);
		
		try {
			render=(IListDocRender)ClassHelper.newInstance(className.getName());
		} catch (RuntimeException e) {
			
		}		
		
		return render;
	}
	
	public static ListRenderFactory getInst(){
		return _inst;
	}
	
	private static ListRenderFactory _inst=new ListRenderFactory();
	public static void main(String[] args) {
		System.out.println(StreetRender.class);
	}
}
