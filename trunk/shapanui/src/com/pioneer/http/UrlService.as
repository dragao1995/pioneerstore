package com.pioneer.http
{
	import com.SharedObjectUtil;
	
	public class UrlService
	{
		public function UrlService()
		{
			
		}
	
	//private static var basePath:String=SharedObjectUtil.getServiceURL();
	private static var basePath:String="http://127.0.0.1:7777/store/";
	public static function getServiceUrl():String{
		return basePath;
	}
	public static function setBasePath(url:String):void{
		basePath=url;
	}
	public static function getListUrl():String{
		if(null==basePath)basePath="http://127.0.0.1/store/";
		return basePath+"CDSServlet"
//		return "mode/data/listdoc.xml";
	}
	
	public static function getSystemDefUrl():String{
		return basePath+"SystemDefServlet";
	}
	
	public static function getDetailUrl():String{
		return basePath+"CDSServlet"
		//return "mode/data/detaildoc.xml";
	}
	public static function getPCCDSUrl():String{
		return basePath+"OrderProcServlet"
		//return "mode/data/detaildoc.xml";
	}
	public static function getCDSActionUrl():String{
		if(null==basePath)basePath="http://127.0.0.1/store/";
		return basePath+"CDSServlet";
	}
	
	public static function getUploadUrl():String{
		return basePath+"FileUploadServlet";
		
	}
	public static function getFileBasePath():String{
		return basePath+"upload/";
	}
	
	public static function getTreeUrl():String{
		return basePath+"TreeServlet";
	}
	public static function getGoodCDSURL():String{
		return basePath+"GoodCDSServlet";
	}
	
	public static function getShapanCDSURL():String{
		return basePath+"shaPanServlet";
	}
	

	}
}