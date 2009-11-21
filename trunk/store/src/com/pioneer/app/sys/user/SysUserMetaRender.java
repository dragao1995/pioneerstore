package com.pioneer.app.sys.user;

import org.dom4j.Document;
import org.dom4j.Element;

import com.pioneer.app.xml.meta.IMetaDocRender;

public class SysUserMetaRender implements IMetaDocRender {

	public void render(Document doc) {
		Element elt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='PASSWORD']");
		if(null!=elt){
			elt.addAttribute("TYPE", "password");
		}

	}

}
