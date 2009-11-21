package com.pioneer.app.good;

import org.dom4j.Document;
import org.dom4j.Element;

import com.pioneer.app.xml.meta.IMetaDocRender;

public class SpendingMateRender implements IMetaDocRender {

	public void render(Document doc) {
		Element elt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='DATETIME']");
		if(null!=elt){
			elt.addAttribute("TYPE", "date");
		}

	}

}
