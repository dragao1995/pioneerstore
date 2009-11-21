package com.pioneer.app.xml.add;

import org.apache.commons.collections.FastHashMap;

import com.pioneer.app.listrender.StreetRender;
import com.pioneer.app.store.render.add.InGoodAddRender;
import com.pioneer.app.store.render.add.PhysicalObjAddRender;
import com.pioneer.app.util.ClassHelper;

public class AddRenderFactory {

	private FastHashMap renders=new FastHashMap();
	private AddRenderFactory(){
		init();
	}
	
	private void init(){
		//renders.put("table_name", class)
		//renders.put("地理对象存储", PhysicalObjAddRender.class);
		renders.put("入货单", InGoodAddRender.class);
	}
	public IAddDocRender getRender(String tbName){
		IAddDocRender render=null;
		Class className=(Class)this.renders.get(tbName);
		
		try {
			render=(IAddDocRender)ClassHelper.newInstance(className.getName());
		} catch (RuntimeException e) {
			
		}		
		
		return render;
	}
	
	public static AddRenderFactory getInst(){
		return _inst;
	}
	
	private static AddRenderFactory _inst=new AddRenderFactory();
	public static void main(String[] args) {
		System.out.println(StreetRender.class);
	}
}
