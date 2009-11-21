package view.pioneer
{
	import mx.containers.HBox;
	
	import view.object.ObjectPanal;

	public class shopDetail extends ObjectPanal
	{
		public function shopDetail()
		{
			super();
		}
		 override public  function addDetailField2Panel():void{
			//use textInputLs or textInputMap
			for each( var col:HBox in labelLs){
					
					var bb:Object=col.getChildByName("USER_NUMBER");
					if(null!=bb){
						//Alert.show("ssss");
						//var pp:Label=new Label();
						//pp.text="111111";
						//col.addChild(pp);
						//this.panel.addChild(pp);
					}
					
					
					this.panel.addChild(col);
			}
		}
		
	}
}