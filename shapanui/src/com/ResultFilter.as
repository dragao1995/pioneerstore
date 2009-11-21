package com
{
	import mx.controls.Alert;
	
	public class ResultFilter
	{
		public function ResultFilter()
		{
		}
		
		public static function filter(doc:XML):Boolean{
			var flg:Boolean=false;
			var code:String=doc.@code;
			var message:String=doc.@message;
			if("error"==code){
				Alert.show(message);
				
			}else{
				flg=true;
			}
			return flg;
		}

	}
}