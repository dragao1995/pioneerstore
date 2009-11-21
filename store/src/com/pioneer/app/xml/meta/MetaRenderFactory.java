package com.pioneer.app.xml.meta;

import org.apache.commons.collections.FastHashMap;

import com.pioneer.app.good.SpendingMateRender;
import com.pioneer.app.listrender.StreetRender;
import com.pioneer.app.sys.user.SysUserMetaRender;
import com.pioneer.app.tcxh.area.AreaMetaRender;
import com.pioneer.app.tcxh.shop.ShopMetaRender;
import com.pioneer.app.util.ClassHelper;

public class MetaRenderFactory {

	private FastHashMap renders=new FastHashMap();
	private MetaRenderFactory(){
		init();
	}
	
	private void init(){
		//此信息从配置文件中读取
		
		//renders.put("开支", SpendingMateRender.class);
		//renders.put("用户", SysUserMetaRender.class);
	}
	public IMetaDocRender getRender(String tbName){
		IMetaDocRender render=null;
		Class className=(Class)this.renders.get(tbName);
		if(null!=className)
			render=(IMetaDocRender)ClassHelper.newInstance(className.getName());		
		return render;
	}
	
	public static MetaRenderFactory getInst(){
		return _inst;
	}
	
	private static MetaRenderFactory _inst=new MetaRenderFactory();
	public static void main(String[] args) {
		System.out.println(StreetRender.class);
	}
}
