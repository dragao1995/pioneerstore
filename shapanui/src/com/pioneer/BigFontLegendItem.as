package com.pioneer
{
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	
	import mx.charts.LegendItem;
	import mx.controls.Alert;
	import mx.controls.CheckBox;
	public class BigFontLegendItem extends LegendItem 
	{
		public function BigFontLegendItem()
		{
			super(); 
			this.styleName = "boot";
			//Alert.show(label);
		}
		
		
		override protected function createChildren():void {
         super.createChildren();
         
        var cb: CheckBox=new CheckBox(); 
        var child:DisplayObject=this.getChildAt(0);
        
        //cb.addEventListener(MouseEvent.CLICK,checkclick);
        child.addEventListener(MouseEvent.CLICK,checkclick);
        //child.addChild(cb);
     }
     
     private function checkclick(e:MouseEvent):void{
     	Alert.show("checkclick="+label);
     }

		

	}
}