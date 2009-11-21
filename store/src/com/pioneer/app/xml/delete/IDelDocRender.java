package com.pioneer.app.xml.delete;

import org.dom4j.Document;

public interface IDelDocRender {
	/**
	 * @desc:
	 * @param doc :用于显示详细信息的修饰加工处理
	 * @auther : pioneer
	 * @date : Apr 1, 2009
	 */
	public void render(Document doc)throws Exception;
}
