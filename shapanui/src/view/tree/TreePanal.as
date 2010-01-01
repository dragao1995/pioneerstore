package view.tree
{
	import component.FileUpload;
	import component.ImageBrowse;
	
	import event.FileEvent;
	
	import flash.events.FocusEvent;
	
	import mx.containers.Box;
	import mx.containers.HBox;
	import mx.controls.Alert;
	import mx.controls.DateField;
	import mx.controls.Label;
	import mx.controls.TextInput;
	import mx.core.UITextField;
	import mx.utils.XMLUtil;
	
	import view.fk.FKTextInput;
	
	public class TreePanal
	{
	   	public	var panel:Box=null;
		public var textInputMap:Array=new Array();
		public var textInputLs:Array=new Array();
		public var addInputMap:Array=new Array();
		public var addInputLs:Array=new Array();
		public var labelMap:Array=new Array();
		public var labelLs:Array=new Array();
		
		public var doc:XML=null;
		public var keyname:String=null;
		private var comp:String="com";//显示数据所属得模块。
		private var editDoc:XML=null;
		
		public function clear():void{
			this.textInputMap=new Array();
			this.textInputLs=new Array();
			this.addInputMap=new Array();
			this.addInputLs=new Array();
			this.labelMap=new Array();
			this.labelLs=new Array();
		}
		public function createEditDoc():void{
			//if(null==editDoc){
				editDoc=doc.copy();
				var rows:XML=editDoc.TABLEDATA.ROWDATA[0];
				var row:XML=editDoc.TABLEDATA.ROWDATA.ROW[0];
				//var keyElts=editDoc.METADATA.TABLE.KEYS.KEY;
				//var keyElt:XML==keyElts[0];
				var keyName:String=editDoc.METADATA.TABLE.KEYS.KEY.@NAME;
				
				var keyval=	row.@[keyName];
				
				delete editDoc.TABLEDATA.ROWDATA.ROW[0];
				
				rows.appendChild("<ROW/>");
				var newRow=rows.ROW[0];
				newRow.@[keyName]=keyval;
				this.editDoc.@flg=0;
			//}
			//Alert.show(editDoc.toXMLString());
		}
		public function getEditDoc():XML{
			return this.editDoc;
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
		public function TreePanal()
		{
			panel=new Box();
			panel.percentHeight=100;
			panel.percentWidth=100;
		}
		//收集修改了的变量
		
		private function fieldChange(e:flash.events.FocusEvent):void{
			try{
				var obj:UITextField=UITextField(e.target);
				var meta:XML=XML(obj.document);
				var type=meta.@TYPE;
				var name=meta.@NAME;
				var val="";
				val=obj.text;
				
				var row:XML=this.editDoc.TABLEDATA.ROWDATA.ROW[0];
				var oldrow:XML=this.doc.TABLEDATA.ROWDATA.ROW[0];
				this.editDoc.@flg=1;
				if(val!=oldrow.@[name]){
					row.@[name]=val;
				}
			}catch(err:Error){
				Alert.show(err.message);
			}
			
			
		}
		private function fkDataChange(e:event.FKEvent):void{
			try{
				
				var valobj:Object=e.getCN();
				var obj:FKTextInput=FKTextInput(e.target);
				var meta:XML=XML(obj.document);
				var type:String=meta.@TYPE;
				var name:String=meta.@NAME;
				var ISFK:String=meta.@ISFK;
				var val:String="";
				var valName:String=null;
				
				val=valobj.id;
				valName=valobj.name;
				var row:XML=this.editDoc.TABLEDATA.ROWDATA.ROW[0];
				var oldrow:XML=this.doc.TABLEDATA.ROWDATA.ROW[0];
				this.editDoc.@flg=1;
				if(val!=oldrow.@[name] && "ICON"!=name){
					//Alert.show(name+"_NAME="+valName);
					row.@[name+"_NAME"]=valName;
					row.@[name]=val;
				}else if(valName!=oldrow.@[name]){
					row.@[name]=valName;
				}
				//Alert.show(row.toXMLString());
			}catch(err:Error){
				Alert.show(err.message);
			}
			
			
		}
		private function dateChange(e:flash.events.FocusEvent):void{
			try{
				var obj:DateField=DateField(e.target);
				var meta:XML=XML(obj.document);
				var type=meta.@TYPE;
				var name=meta.@NAME;
				var val="";
				val=obj.text;
				var row:XML=this.editDoc.TABLEDATA.ROWDATA.ROW[0];
				var oldrow:XML=this.doc.TABLEDATA.ROWDATA.ROW[0];
				if(val!=oldrow.@[name]){
					row.@[name]=val;
				}
				this.editDoc.@flg=1;
			}catch(err:Error){
				Alert.show(err.message);
			}
			
			
		}
		private function fileChange(e:FileEvent):void{
			try{
				var obj:FileUpload=FileUpload(e.target);
				
				var name=obj.name;
				var val="";
				val=e.getFileName();
				//Alert.show(name+"="+val)
				var row:XML=this.editDoc.TABLEDATA.ROWDATA.ROW[0];
				var oldrow:XML=this.doc.TABLEDATA.ROWDATA.ROW[0];
				if(val!=oldrow.@[name]){
					row.@[name]=val;
				}
				this.editDoc.@flg=1;
			}catch(err:Error){
				Alert.show(err.message);
			}
			
			
		}
		public function addField2Array(mate:XML,row:XML):void{
			if(mate){
//				col.@NAME,col.@REMARK
				var label:String=mate.@REMARK+":";
				var name:String=mate.@NAME;
				if(null==row)return;
				var val:String=XMLUtil.getAttributeByQName(row,new QName(mate.@NAME));	
				var isEditStr:String=mate.@ISEDIT;
				var isEdit:Boolean=false;
				var type:String=mate.@TYPE;
				var ISFK:String=mate.@ISFK;
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
				
				if("1"==ISFK && "ICON"!=name){
					val=XMLUtil.getAttributeByQName(row,new QName((mate.@NAME)+"_NAME"));
				}else{
					
					val=XMLUtil.getAttributeByQName(row,new QName(mate.@NAME));
				}
				
			 	
			 	var p:HBox=new HBox();//编辑显示
			 	p.percentWidth=100;
			 	var el:Label=new Label();
			 	el.styleName="label";
			 	el.text=label;
			 	el.width=60;
			 	var editf:TextInput=new TextInput();
			 	editf.styleName="textInput";
			 	
			 	editf.percentWidth=60;
				editf.name=name;
				editf.text=val;
				editf.document=mate
				//editf.editable=isEdit;
				editf.id=name+"_id";
				editf.addEventListener(flash.events.FocusEvent.FOCUS_OUT,fieldChange);
			 	p.addChild(el);
			 	if("1"==ISFK && "ICON"!=name){
								var fkinput:FKTextInput=new FKTextInput();
								fkinput.addEventListener(event.FKEvent.DATA_CHANGE,fkDataChange);
								fkinput.setInfo(mate,row);
								
								fkinput.document=mate;
								fkinput.styleName="textInput";
								fkinput.name=name;
								fkinput.id=name+"_id";
								p.addChild(fkinput);
							}else
			 	if("date"==type){
					var datef:DateField=new DateField();
					datef.text=val;
					datef.percentWidth=60;
					datef.name=name;
					datef.document=mate;
					datef.addEventListener(flash.events.FocusEvent.FOCUS_OUT,dateChange);
					datef.id=name+"_id";
					datef.formatString="YYYY-MM-DD";
					datef.dayNames=['日','一','二','三','四','五','六'];
					datef.monthNames=['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];

					p.addChild(datef);
					
					
				}else if("file"==type || "image"==type){
					var fileSel2:FileUpload=new FileUpload();
					
					fileSel2.addEventListener(FileEvent.UPLOAD_SUCESSFUL,fileChange);
					
					fileSel2.percentWidth=60;
					fileSel2.setText(val);
					fileSel2.name=name;
					fileSel2.id=name+"_id";
					fileSel2.setComName("shoplogo");
					p.addChild(fileSel2);
				}else{
					p.addChild(editf);
				}
				
				p.visible=dp;
				p.name="box"+name;
//////////////////////////////////////////////////
				var lp:HBox=new HBox();//浏览显示，
			 	lp.percentWidth=100;
			 	var l:Label=new Label();
			 	l.styleName="label";
				var valLabel:Label=new Label();
				valLabel.styleName="label";
			 	valLabel.percentWidth=60;
			 	valLabel.text=val;
			 	l.text=label;
			 	l.width=60;
			 	lp.addChild(l);
				if("image"==type){
					var img:ImageBrowse=new ImageBrowse();
					img.setComp(this.comp);
					img.setName(val);
					lp.addChild(img);
				}else if("file"==type){
					
				}else{
					lp.addChild(valLabel);
				}
				
				
				lp.visible=dp;
				lp.name="box"+name;
/////////////////////////////////////////////////////////////////
				var ap:HBox=new HBox();//添加显示
			 	ap.percentWidth=100;
			 	var addl:Label=new Label();
			 	addl.styleName="label";
			 	addl.text=label;
			 	addl.width=60;
				var addf:TextInput=new TextInput();
				addf.styleName="textInput";
				addf.name=name;
				//addf.text=val;
				//addf.editable=isEdit;
				addf.id=name+"_id";
				addf.percentWidth=60;
				ap.addChild(addl);
				if("date"==type){
					var datef1:DateField=new DateField();
					datef1.text=val;
					datef1.percentWidth=60;
					datef1.name=name;
					datef1.id=name+"_id";
					datef1.formatString="YYYY-MM-DD";
					datef1.dayNames=['日','一','二','三','四','五','六']; 
					datef1.monthNames=['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
					ap.addChild(datef1);
					
					
				}else if("file"==type || "image"==type){
					var nfileSel:FileUpload=new FileUpload();
					nfileSel.percentWidth=60;
					//fileSel.setText(val);
					nfileSel.name=name;
					nfileSel.id=name+"_id";
					nfileSel.setComName("shoplogo");
					ap.addChild(nfileSel);
				}else{
					ap.addChild(addf);
				}
				
				ap.visible=dp;
				ap.name="box"+name;
				
				if(!dp){
					p.percentHeight=0;
				}
				//panel.addChild(p);	
				textInputMap[name]=p;
				textInputLs.push(p);
				addInputLs.push(ap);
				addInputMap[name]=ap;
				labelMap[name]=lp;
				labelLs.push(lp);
			}
		}
		public function addField(mate:XML,val:String):void{
			if(mate){
//				col.@NAME,col.@REMARK
				var label:String=mate.@REMARK;
				var name:String=mate.@NAME;
				var isEditStr:String=mate.@ISEDIT;
				var isEdit:Boolean=false;
				if("true"==isEditStr){
					isEdit=true;
				}
				var dpStr:String=mate.@DP;
				var dp:Boolean=false;
				if("1"==dpStr){
					dp=true;
				}
			
				var editf:TextInput=new TextInput();
				var p:HBox=new HBox();
			 	var l:Label=new Label();
			 	p.percentWidth=100;
			 	l.text=label;
			 	l.percentWidth=30;
			 	editf.percentWidth=65;
				editf.name=name;
				editf.text=val;
				editf.editable=isEdit;
				p.addChild(l);
				p.addChild(editf);
				p.visible=dp;
				if(!dp){
					p.percentHeight=0;
				}
				panel.addChild(p);	
			
				
			}
		}
		public function addDetailField2Panel():void{
			
			
			for each( var col:HBox in this.labelLs){
					this.panel.addChild(col);
			}
		}
		public function addEditField2Panel():void{
			
			for each( var col:HBox in this.textInputLs){
					this.panel.addChild(col);
			}
		}
		public function addAddField2Panel():void{
			
			for each( var col:HBox in this.addInputLs){
					this.panel.addChild(col);
			}
		}
		public function getDetailPanel():Box{
			panel.removeAllChildren();
			//addButton();
			addDetailField2Panel();
			return this.panel;
		}
		public function getEditPanel():Box{
			panel.removeAllChildren();
			//addButton();
			addEditField2Panel();
			return this.panel;
		}
		public function getAddPanel():Box{
			panel.removeAllChildren();
			//addButton();
			addAddField2Panel();
			return this.panel;
		}
	}
}