package view.object
{
	import component.ImageBrowse;
	
	import mx.containers.Box;
	import mx.containers.HBox;
	import mx.controls.Alert;
	import mx.controls.Label;
	import mx.controls.Text;
	import mx.utils.XMLUtil;
	
	import view.fk.FKLabel;
	
	public class ObjectComparePanal
	{
	   	public	var panel:Box=null;
		public var labelMap:Array=new Array();
		public var labelLs:Array=new Array();
		
		public var doc:XML=null;
		public var keyname:String=null;
		private var comp:String=null;//显示数据所属得模块。
		private var editDoc:XML=null;
		private var labelWidth:int=80;
		private var dateFormat:String="YYYY-MM-DD";
		private var areaHeight:int=0;
		private var defaultValues:Array=null;
			//设置默认值 obj.NAME ,obj.VALUE
		public function setDefaultValues(defaultValues:Array):void{
			this.defaultValues=defaultValues;
			
		}
		public function setAreaHeight(areaHeight:int):void{
			this.areaHeight=areaHeight;
		}
		public function setDateFormat(format:String):void{
			this.dateFormat=format;
		}
		public function setLabelWidth(width:int):void{
			this.labelWidth=width;
		}
		public function clear():void{
			
			this.labelMap=new Array();
			this.labelLs=new Array();
		}
		
		public function setComp(comp:String):void{
			this.comp=comp;
		}
		public function setDoc(doc:XML):void{
			this.doc=doc;
			var keyElt=doc.METADATA.TABLE.KEYS.KEY;
			if(keyElt){
				this.keyname=keyElt.@NAME;
			}
		}
		public function ObjectComparePanal()
		{
			panel=new Box();
			panel.percentHeight=100;
			panel.percentWidth=100;
		}
		
		public function addField2Array(mate:XML,rows:XMLList):void{
			try{
				if(mate){
	//				col.@NAME,col.@REMARK
					
					var label:String=mate.@REMARK+":";
					var name:String=mate.@NAME;
					var isEditStr:String=mate.@ISEDIT;
					var ISFK:String=mate.@ISFK;
					var isEdit:Boolean=false;
					var type:String=mate.@TYPE;
					var sizeStr:String=mate.@SIZE;
					var size:int=new Number(sizeStr);
					var haveList:String=mate.@HAVELIST;
					
					
					
					if("true"==isEditStr){
						isEdit=true;
					}
					
					var dpStr:String=mate.@DP;
					var dp:Boolean=true;
					if("0"==dpStr){
						dp=false;
					}
					if(name==this.keyname){
						dp=false;
					}
					
					
				 	
	//////////////////////////////////////////////////
					if("CREATE_ID"!=name && dp){
						var lp:HBox=new HBox();//浏览显示，
					 	lp.percentWidth=100;
					 	lp.styleName="hbox";
					 	//lp.width=this.labelWidth;
					 	var l:Label=new Label();
					 	l.styleName="label";
					 	var valLabel=null;
						
					 	
					 	
					 	
					 	
					 	l.text=label;
					 	l.width=this.labelWidth;
					 	//添加标题
					 	lp.addChild(l);
					 		for each (var row:XML in rows){
					 			var val='';
								if("1"==ISFK && "ICON"!=name){
									val=XMLUtil.getAttributeByQName(row,new QName((mate.@NAME)+"_NAME"));
								}else{
									
									val=XMLUtil.getAttributeByQName(row,new QName(mate.@NAME));
								}
								if("1"==ISFK && "ICON"!=name){
								 	valLabel=new FKLabel();
								 	valLabel.setInfo(mate,row);
								 	valLabel.styleName="htmlLabel";
								 	
				//				 	valLabel.
							 	}else{
							 		valLabel=new Label();
							 		valLabel.text=val;
							 		valLabel.styleName="label";
							 	}
								
								if("image"==type){
								var img:ImageBrowse=new ImageBrowse();
								
								if(null!=this.comp){
									
									img.setComp(this.comp);
								}else{
									img.setComp(this.doc.@TABLECODE);
								}
									
									
								//img.setComp(doc.@TABLECODE);
								img.setName(val);
								
								lp.addChild(img);
								}else if("file"==type){
									
								}else  if("password"==type){
									valLabel.text="**********";
									valLabel.percentWidth=10;
									lp.addChild(valLabel);
								}else{
									if("ICON"==name ){
										var img:ImageBrowse=new ImageBrowse();
										img.setComp("ctl_biaoji");
										img.setName(val);
										lp.addChild(img);
									}else if("varchar"==type && size>50){
										//var ta:	TextArea=new TextArea();
										var ta:Text=new Text();
										ta.styleName="label";
										ta.text=val;
										ta.percentWidth=100;
										
										lp.addChild(ta);
									}else{
										valLabel.percentWidth=10;
										lp.addChild(valLabel);
									}
								
								}
					 		}
						 	
						
						
						lp.visible=dp;
						lp.name="box"+name;
						labelMap[name]=lp;
						labelLs.push(lp);
					}
					
				}//end if mate
			}catch(e:Error){
				Alert.show(e.getStackTrace());
			}
			
		}
		
		
		public function addDetailField2Panel():void{
			
			
			for each( var col:HBox in this.labelLs){
					this.panel.addChild(col);
			}
		}
		
		public function getDetailPanel():Box{
			panel.removeAllChildren();
			addDetailField2Panel();
			return this.panel;
		}
		
		
	}
}