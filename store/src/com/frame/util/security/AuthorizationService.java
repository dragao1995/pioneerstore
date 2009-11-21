package com.frame.util.security;

import java.io.FileWriter;
import java.net.InetAddress;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.pioneer.app.util.DateTimeUtil;
import com.pioneer.app.util.Dom4jUtil;

public class AuthorizationService {

	public  void createApplyInfo(String name,String address)throws Exception {
		java.io.InputStream fileStream =this.getClass().getResourceAsStream("ctlInfo.xml");
		if(null!=fileStream){
			SAXReader reader=new SAXReader();
			Document doc=reader.read(fileStream);
			Element root= doc.getRootElement();
			Element userElt=(Element)root.selectSingleNode("user");
			if(null!=userElt){
				userElt.setAttributeValue("name", name);
				Element addrElt=(Element)userElt.selectSingleNode("address");
				if(null!=addrElt)
					addrElt.setAttributeValue("value", address);
				Element hostElt=(Element)userElt.selectSingleNode("host");
				if(null!=hostElt){
					String ipStr= null;
						
					try {
						ipStr=InetAddress.getLocalHost().getHostAddress();
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
					String macStr=MACService.getMacAddress();
					hostElt.setAttributeValue("ip", ipStr);
					hostElt.setAttributeValue("mac", macStr);
				}
					
				
			}
			System.out.println("请把c:/app.xml发送给系统管理人员，为系统生成注册文件！");
			Dom4jUtil.writeDocToFile(doc, "GBK", "c:/app.xml");
			System.out.println("doc="+doc.asXML());
		}
	}
	public  void createApplyDoc(String name,String address)throws Exception {
		
			String date=DateTimeUtil.getDate();
			String macStr=MACService.getMacAddress();
			StringBuffer bf=new StringBuffer(1024*3);
			bf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>").append("\r\n")
			.append("<application name=\"store\"  date=\"").append(date).append("\">").append("\r\n")
			.append("<user value=\"").append(name).append("\"/>").append("\r\n")
			.append("<host mac=\"").append(macStr).append("\"/>").append("\r\n")
			.append("</application>").append("\r\n");

			FileWriter writer=new FileWriter("j:/application.xml");
			writer.write(bf.toString());
			writer.flush();
			writer.close();
	}
	public static void main(String[]args){
		try {
			AuthorizationService athu=new AuthorizationService();
			athu.createApplyDoc("enduser", "云南");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
