package com.pioneer.app.tcxh.shop;

import org.dom4j.Document;
import org.dom4j.Element;

import com.pioneer.app.util.Dom4jUtil;
import com.pioneer.app.xml.meta.IMetaDocRender;

public class ShopMetaRender implements IMetaDocRender {

	public void render(Document doc) {
		//文件类型的要把类型改成file类型。
		Element elt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='logo']");
		if(null!=elt){
			elt.addAttribute("TYPE", "image");
		}
		
		Element srtelt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='SHOP_REGIST_TIME']");
		if(null!=srtelt){
			srtelt.addAttribute("TYPE", "date");
		}
		
		Element nsrtelt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='NET_SHOP_REGIST_TIME']");
		if(null!=nsrtelt){
			nsrtelt.addAttribute("TYPE", "date");
		}
		//设置可以编辑的列。
		//System.out.println(elt.asXML());
	}
	
	public static void main(String[]args){
		try {
			Document doc=Dom4jUtil.getDocFromFile("c:/detail.xml");
			ShopMetaRender render=new ShopMetaRender();
			render.render(doc);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
