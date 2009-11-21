package com
{
	import flash.display.DisplayObject;
	import flash.ui.ContextMenuItem;
	
	import mx.managers.PopUpManager;
	
	import view.sys.LoginWindow;
	
	public class AppShareObj
	{
		private static var name:String=null;
		private static var userdoc:XML;
		
		private static var menus:Array=null;
		//public static var appcode:String="ctl";
		public static var appcode:String="store";
		//public static var appcode:String="shop";
		public static var companyName:String="移动";
		
		public function AppShareObj()
		{
			menus=new Array();
		}
		
		public static function setUserDoc(t_userdoc:XML):void{
			userdoc=t_userdoc;
		}
		
		public static function getUserDoc():XML{
			return userdoc;
		}
		public static function setName(t_name:String):void{
			name=t_name;
		}
		
		public static function getName():String{
			return name;
		}
		
		
		public static function loginTest(par:DisplayObject):void{
			var userdoc:XML=AppShareObj.getUserDoc();
	            if(null==userdoc){
	            	//打开登陆界面
	            	var loginwin:LoginWindow =LoginWindow(PopUpManager.createPopUp(par, LoginWindow, true));
        			loginwin.title="登录";
        			loginwin.setParentObj(par);
        			
        			loginwin.x=(par.width)/2-loginwin.width/2;
	                loginwin.y=par.height/2-loginwin.height/2;
	            }
		}
		public static function isPriview(priview:String):Boolean{
			if(null==userdoc)return false;
			var ps:XMLList=userdoc.PRIVIEW;
			for each( var p:XML in ps){
				if(p.text()==priview)return true;
			}
			return false;
		}
		
		public static function setMenu(menu:ContextMenuItem):void{
			menus.push(menu);
			
		}
		
		
	}
}