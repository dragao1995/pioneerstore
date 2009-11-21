package com.pioneer.app.tcxh.area;

import org.dom4j.Document;
import org.dom4j.Element;

import com.pioneer.app.util.Dom4jUtil;
import com.pioneer.app.xml.meta.IMetaDocRender;

public class AreaMetaRender implements IMetaDocRender {

	public void render(Document doc) {
		//文件类型的要把类型改成file类型。
		Element elt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='pid']");
		if(null!=elt){
			elt.addAttribute("DP", "0");
		}
		
		
		//设置可以编辑的列。
		//System.out.println(elt.asXML());
	}
	
	public static void main(String[]args){
		try {
			Document doc=Dom4jUtil.getDocFromFile("c:/detail.xml");
			AreaMetaRender render=new AreaMetaRender();
			render.render(doc);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
