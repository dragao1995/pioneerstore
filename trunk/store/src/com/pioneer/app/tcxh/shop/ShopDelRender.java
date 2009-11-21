package com.pioneer.app.tcxh.shop;

import org.dom4j.Document;
import org.dom4j.Element;

import com.pioneer.app.xml.delete.IDelDocRender;

/**
 * @name :  
 * @URL : 
 * @URL_Parameter :  
 * @author Administrator
 * @version : v 1.0
 * @description : 删除商店信息的时候，要关联删除商店的图片信息等，商店下的商品信息。。。。
 */
public class ShopDelRender implements IDelDocRender {

	public void render(Document doc) throws Exception {
		 Element root=doc.getRootElement();
		 String tblName=root.valueOf("@TABLE");
		 String idStr=root.valueOf("@ID");
		 String keyName=root.valueOf("@KEYNAME");
		 //String sql=select 
	}

}
