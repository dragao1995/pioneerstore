package com.frame.util.security;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;

import com.pioneer.app.comm.ApplicationPathMg;
import com.pioneer.app.util.Dom4jUtil;

public class ApplicationValidate {

	private static String appName="store";
	private static boolean flg=false;
	private static String message=null;
	public ApplicationValidate(){
		init();
	}
	public static boolean isValidate(){
		return flg &&(null ==message);
	}
	public static String getMessage(){
		return message;
	}
	private void init(){
		try {
			String configFile=ApplicationPathMg.getInstance().getWebRootPath()+"/config/appconfig.xml";
			String configFile1=ApplicationPathMg.getInstance().getWebRootPath()+"/config/appconfig1.xml";
			CryptFile cryptInst=new CryptFile();
			cryptInst.setPrvKey(ApplicationPathMg.getInstance().getWebRootPath()+"/config/privateKey.scrpt");
			
			cryptInst.decryptFile(configFile, configFile1);
			Document doc=	Dom4jUtil.getDocFromFile(configFile1);
			Element root=doc.getRootElement();
			String name=root.valueOf("@name");
			Element hostElt=(Element)doc.selectSingleNode("/application/host");
			if(null!=hostElt){
				String mac=hostElt.valueOf("@mac");
				if(appName.equalsIgnoreCase(name)){
					String localmacStr=MACService.getMacAddress();
					if(localmacStr.equalsIgnoreCase(mac)){
						flg=true;
						return ;
					}else{
						flg=false;
						message="服务器主机变更，请重新注册系统！";
					}
				}else{
					message="系统注册文件错误！请使用正版注册文件";
				}
			}else{
				message="系统注册文件错误！请使用正版注册文件";
			}
			//System.out.println("message=="+message);
			File file1=new File(configFile1);
			if(file1.exists()){
				file1.delete();
			}
			
			flg=false;
		} catch (Exception e) {
			e.printStackTrace();
			flg=false;
		}
	
	}
	
	public static ApplicationValidate getInst(){
		return _inst;
	}
	
	private static ApplicationValidate _inst=new ApplicationValidate();
	
}
