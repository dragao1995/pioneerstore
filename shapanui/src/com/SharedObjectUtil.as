package com
{
	import flash.net.SharedObject;
	
	public class SharedObjectUtil
	{
		
		public function SharedObjectUtil()
		{

		}
			public static function getServiceURL():String{
				var myLSO:SharedObject= SharedObject.getLocal('store');
				var url:String=myLSO.data.url;
				
				return url;
			}
	
			public static function setServiceURL(url:String):void{
				var myLSO:SharedObject= SharedObject.getLocal('store');
				myLSO.data.url=url;
				myLSO.flush();	
			}
			
			public static function getShopName():String{
				var myLSO:SharedObject= SharedObject.getLocal('shop');
				var shopname:String=myLSO.data.shopname;
				
				return shopname;
			}
	
			public static function setShopName(shopname:String):void{
				var myLSO:SharedObject= SharedObject.getLocal('shop');
				myLSO.data.shopname=shopname;
				myLSO.flush();	
			}
			public static function setUserName(name:String):void{
				var myLSO:SharedObject= SharedObject.getLocal('user');
				myLSO.data.username=name;
				myLSO.flush();	
			}
			public static function getUserName():String{
				var myLSO:SharedObject= SharedObject.getLocal('user');
				return myLSO.data.username;
			}
			
			
	}
}