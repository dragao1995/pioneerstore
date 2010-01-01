package com
{
	import flash.net.SharedObject;
	
	import mx.controls.Alert;
	
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
			public static function getOrderGood():Array{
				var myLSO:SharedObject= SharedObject.getLocal('shop');
				var goods:Array=myLSO.data.goods;
				
				return goods;
			}
			public static function clearOrderGood():void{
				var myLSO:SharedObject= SharedObject.getLocal('shop');
				myLSO.data.goods=null;
				myLSO.flush();	
			}
			public static function delGood(gd:XML):void{
				var myLSO:SharedObject= SharedObject.getLocal('shop');
				var goods:Array=myLSO.data.goods;
				var newLs:Array=new Array();
				for each(var tgood:XML in goods){
					if(tgood.@CODE!=gd.@CODE){
						newLs.push(tgood);
					}
				}
				myLSO.data.goods=newLs;
				myLSO.flush();	
			}
			private static function isAdd(good:Object):Boolean{
				var myLSO:SharedObject= SharedObject.getLocal('shop');
				var goods:Array=myLSO.data.goods;
				
				for each(var tgood:Object in goods){
					if(tgood["CODE"]==good["CODE"])return true;
				}
				return false;
			}
			private static function getGoodByCode(code:String):XML{
				var myLSO:SharedObject= SharedObject.getLocal('shop');
				var goods:Array=myLSO.data.goods;
				var obj:XML=null;
				
				for each(var tgood:XML in goods){
					var tcode:String=tgood.@CODE;
					if(code==tcode){
						obj=tgood;
					}
				}
				return obj;
			}
			
			public static function updateOrderGoods(ls:Array):void{
				var myLSO:SharedObject= SharedObject.getLocal('shop');
				myLSO.data.goods=ls;
				myLSO.flush();	
			}
			public static function setOrderGood(gd:XML):void{
				try{
					var myLSO:SharedObject= SharedObject.getLocal('shop');
					var goods:Array=myLSO.data.goods;
					
					if(null==goods){
						goods=new Array();
						goods.push(gd);
						myLSO.data.goods=goods;
					}else{
						var tGood=getGoodByCode(gd.@CODE);
						if(null==tGood){//新加入的商品
							goods.push(gd);
							myLSO.data.goods=goods;
						}else{//修改商品数量
							tGood.@GETNUMBER=Number(tGood.@GETNUMBER)+1;
						}
						
					}
					myLSO.flush();	
				}catch(e:Error){
					
					Alert.show("Error::"+e.message+e.getStackTrace());
					
				}
			}
			
	}
}