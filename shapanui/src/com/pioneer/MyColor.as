package com.pioneer
{
	public class MyColor
	{
		var arr:Array=new Array();
		var ii:int=0;
		public function MyColor()
		{
			arr.push("0xCC6600");
			arr.push("0x66FF33");
			arr.push("0x3300FF");
			arr.push("0x669900");
			arr.push("0x0000FF");
			arr.push("0x99CC33");
			arr.push("0x00FFFF");
			arr.push("0xFF00FF");
			arr.push("0x999900");
			arr.push("0xFF9900");
			arr.push("0x9966FF");
			arr.push("0x000033");
			arr.push("0x666633");
			arr.push("0xCC99FF");
			arr.push("0x66FF00");
		}
		
		public function getColor(index:int):String{
			if(-1==index){
				return arr[(ii++)%arr.length]
			}else{
				return arr[index];
			}
		}
		

	}
}