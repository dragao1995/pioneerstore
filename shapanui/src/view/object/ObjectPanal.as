package view.object
{
	import com.AppShareObj;
	
	import component.FileUpload;
	import component.ImageBrowse;
	
	import event.FKEvent;
	import event.FileEvent;
	
	import flash.events.FocusEvent;
	
	import mx.containers.Box;
	import mx.containers.HBox;
	import mx.controls.Alert;
	import mx.controls.ComboBox;
	import mx.controls.DateField;
	import mx.controls.Label;
	import mx.controls.Text;
	import mx.controls.TextArea;
	import mx.controls.TextInput;
	import mx.core.Application;
	import mx.core.UITextField;
	import mx.events.ListEvent;
	import mx.utils.XMLUtil;
	
	import view.fk.FKLabel;
	import view.fk.FKTextInput;
	import view.selWin.IconLabel;
	
	public class ObjectPanal
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
		private var comp:String=null;//显示数据所属得模块。
		private var editDoc:XML=null;
		private var labelWidth:int=80;
		private var dateFormat:String="YYYY-MM-DD";
		private var areaHeight:int=0;
		private var defaultValues:Array=null;
		private var readonlyCols:Array=null;
			//设置默认值 obj.NAME ,obj.VALUE
		public function setDefaultValues(defaultValues:Array):void{
			this.defaultValues=defaultValues;
			
		}
		public function setReadonlyCols(readonlyCols:Array):void{
			this.readonlyCols=readonlyCols;
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
			this.textInputMap=new Array();
			this.textInputLs=new Array();
			this.addInputMap=new Array();
			this.addInputLs=new Array();
			this.labelMap=new Array();
			this.labelLs=new Array();
		}
		public function createEditDoc():void{
			if(null==editDoc){
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
			}
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
		public function ObjectPanal()
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
				var type:String=meta.@TYPE;
				//Alert.show("type="+type);
				var name:String=meta.@NAME;
				var ISFK:String=meta.@ISFK;
				var val:String="";
				var valName:String=null;
				
					val=obj.text;
				if("int"==type){
					var intVal:Number=Number(val);
					if (isNaN(intVal) || !val )
		            {
		                Alert.show("请输入整数！");
						obj.setFocus();
						return ;
		            } 
				}else
				if("float"==type ){
					try{
						var intVal:Number=Number(val);
						if (isNaN(intVal) || !val )
			            {
			                Alert.show("请输入数字！");
							obj.setFocus();
							return ;
			            } 
					}catch(eee:Error){
						Alert.show(eee.message);
					}
					
				}
				if("varchar"==type ){
					try{
						var length:int=meta.@SIZE;
						//Alert.show("length="+length);
						//Alert.show("val.length="+val.length);
						if (null!=val && val.length>length )
			            {
			                Alert.show("输入的字符串个数不得多于"+length+"个！");
							obj.setFocus();
							return ;
			            } 
					}catch(eee:Error){
						Alert.show(eee.message);
					}
					
				}
				
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
		private function textAreaChange(e:flash.events.Event):void{
			try{
				var obj:TextArea=TextArea(e.target);
				var meta:XML=XML(obj.document);
				var type:String=meta.@TYPE;
				//Alert.show("type="+type);
				var name:String=meta.@NAME;
				var ISFK:String=meta.@ISFK;
				var val:String="";
				var valName:String=null;
				
					val=obj.text;
				
				if("varchar"==type ){
					try{
						var length:int=meta.@SIZE;
						//Alert.show("length="+length);
						//Alert.show("val.length="+val.length);
						if (null!=val && val.length>length )
			            {
			                Alert.show("输入的字符串个数不得多于"+length+"个！");
							obj.setFocus();
							return ;
			            } 
					}catch(eee:Error){
						Alert.show(eee.message);
					}
					
				}
				
				var row:XML=this.editDoc.TABLEDATA.ROWDATA.ROW[0];
				var oldrow:XML=this.doc.TABLEDATA.ROWDATA.ROW[0];
				this.editDoc.@flg=1;
				if(val!=oldrow.@[name]){
					//Alert.show(name+":::"+val);
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
		private function imageChange(e:event.FKEvent):void{
			try{
				
				var valobj:Object=e.getCN();
				var obj:IconLabel=IconLabel(e.target);
				var meta:XML=XML(obj.document);
				var type:String=meta.@TYPE;
				var name:String=meta.@NAME;
				var ISFK:String=meta.@ISFK;
				var val:String="";
				var valName:String=null;
				
				
				val=valobj.name;
				var row:XML=this.editDoc.TABLEDATA.ROWDATA.ROW[0];
				var oldrow:XML=this.doc.TABLEDATA.ROWDATA.ROW[0];
				this.editDoc.@flg=1;
				if(val!=oldrow.@[name]){
					row.@[name]=val;
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
		private function comboxChange(e:ListEvent):void{
			try{
				var obj:ComboBox=ComboBox(e.target);
				
				var name=obj.name;
				var val="";
				val=obj.selectedLabel;
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
		
		private function isSetDefaultValue(colName:String):Boolean{
			var flg:Boolean=false;
			if(null!=this.defaultValues){
				for each(var obj:Object in this.defaultValues){
					if(obj.NAME==colName){
						flg=true;
						break;
					}
				}
			}
			return flg;
		}
		private function isReadonly(colName:String):Boolean{
			var flg:Boolean=false;
			if(null!=this.readonlyCols){
				for each(var obj:Object in this.readonlyCols){
					if(obj.NAME==colName){
						flg=true;
						break;
					}
				}
			}
			return flg;
		}
		//readonlyCols
		public function addField2Array(mate:XML,row:XML):void{
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
					var val='';
					if("1"==ISFK && "ICON"!=name){
						val=XMLUtil.getAttributeByQName(row,new QName((mate.@NAME)+"_NAME"));
					}else{
						
						val=XMLUtil.getAttributeByQName(row,new QName(mate.@NAME));
					}
					
					
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
					
					//=================编辑显示=======================
					if("UPDATE_TIME"!=name && "CREATE_TIME"!=name){
						var p:HBox=new HBox();
					 	p.percentWidth=100;
					 	var el:Label=new Label();
		//			 	el.width
					 	el.styleName="label";
					 	el.text=label;
					 	//el.percentWidth=25;
					 	//if(0!=this.labelWidth)
					 	el.width=this.labelWidth;
					 	var editf:TextInput=new TextInput();
					 	editf.percentWidth=60;
		//			 	editf.textHeight=13;
					 	editf.setStyle("textHeight","13");
					 	editf.styleName="textInput";
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
							datef.styleName="textInput";
							datef.text=val;
							datef.percentWidth=60;
							datef.name=name;
							datef.document=mate;
							datef.addEventListener(flash.events.FocusEvent.FOCUS_OUT,dateChange);
							datef.id=name+"_id";
							datef.formatString=dateFormat;
							datef.dayNames=['日','一','二','三','四','五','六'];
							datef.monthNames=['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
							
							p.addChild(datef);
							
							
						}else if("file"==type || "image"==type){
							var fileSel2:FileUpload=new FileUpload();
							fileSel2.styleName="textInput";
							fileSel2.addEventListener(FileEvent.UPLOAD_SUCESSFUL,fileChange);
							if(isSetDefaultValue(name)){
								fileSel2.setEditAble(false);
							}
							if(isReadonly(name)){
								fileSel2.setEditAble(false);
							}
							fileSel2.percentWidth=60;
							fileSel2.setText(val);
							fileSel2.name=name;
							fileSel2.id=name+"_id";
							fileSel2.setComName(this.comp);
							fileSel2.setTable(this.doc.@TABLE);
							p.addChild(fileSel2);
						}else  if("password"==type){
							editf.displayAsPassword=true;
							p.addChild(editf);
						}else {
							if("ICON"==name){
								var icon:IconLabel=new IconLabel();
								icon.addEventListener(event.FKEvent.DATA_CHANGE,imageChange);
								icon.setText(val);
								icon.document=mate;
								icon.name=name;
								icon.id=name+"_id";
								p.addChild(icon);
							}else if("varchar"==type && size>50){
								var tarea:TextArea=new TextArea();
								tarea.styleName="textArea";
								
								tarea.percentWidth=100;
								//tarea.height=90;
								//tarea.maxHeight=90;
								if(0!=areaHeight){
									tarea.height=areaHeight;
								}else{
									tarea.percentHeight=30
								}
								//tarea.setText(val);
								tarea.text=val;
								tarea.name=name;
								tarea.id=name+"_id";
								tarea.addEventListener(flash.events.Event.CHANGE,textAreaChange);
								tarea.document=mate;
								if(isReadonly(name)){
									tarea.enabled=false;
								}
								p.addChild(tarea);
							}else if("1"==haveList){//添加list
								 var ls:ComboBox=new ComboBox();
								 ls.dataProvider=mate.LIST.OBJECT;
								 ls.addEventListener(mx.events.ListEvent.CHANGE,comboxChange);
								 //ls.percentWidth=60;
								 ls.styleName="textInput";
								//tarea.setText(val);
								//ls.height=90;
								//ls.text=val;
								ls.selectedItem=val;
								ls.name=name;
								ls.id=name+"_id";
								p.addChild(ls);
							}else{
								//处理创建人
								if("CREATE_ID"==name){
									//把当前用户id给create_di
									p.addChild(editf);
								}else if("CREATE_NAME"==name){
									//把当前用户name给create_name
									//addf.editable=false;
									//addf.text="设置成当前用户！"
									var namelabel:Label=new Label();
									namelabel.text=val;
									namelabel.styleName="textInput";
									p.addChild(namelabel);
								}else{
									p.addChild(editf);
								}
								
							}
							
							
						}
						if("CREATE_ID"==name){
							p.visible=false;
							p.height=0;
						}
						p.visible=dp;
						p.name="box"+name;
						textInputMap[name]=p;
						textInputLs.push(p);
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
						
					 	if("1"==ISFK && "ICON"!=name){
						 	valLabel=new FKLabel();
						 	valLabel.setInfo(mate,row);
						 	valLabel.styleName="htmlLabel";
						 	
		//				 	valLabel.
					 	}else{
					 		
					 		valLabel=new Text();
					 		//valLabel=new Label();
					 		valLabel.text=val;
					 		valLabel.styleName="label";
					 	}
					 	
					 	valLabel.percentWidth=60;
					 	
					 	l.text=label;
					 	//l.percentWidth=25;
					 	//if(0!=this.labelWidth)
					 	l.width=this.labelWidth;
					 	
					 	lp.addChild(l);
						if("image"==type){
							var img:ImageBrowse=new ImageBrowse();
							img.percentWidth=100;
							if(null!=this.panel){
								img.height=Application.application.screen.height/3;
								
							}
							//img.height=
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
								lp.addChild(valLabel);
							}
							
						}
						
						
						lp.visible=dp;
						lp.name="box"+name;
						labelMap[name]=lp;
						labelLs.push(lp);
					}
					
	/////////////////////////////////////////////////////////////////
					if("UPDATE_TIME"!=name && "CREATE_TIME"!=name && "ICON"!=name){
						var ap:HBox=new HBox();//添加显示
					 	ap.percentWidth=100;
					 	
					 	var addl:Label=new Label();
					 	addl.styleName="label";
					 	addl.text=label;
					 	//addl.percentWidth=25;
					 	//if(0!=this.labelWidth)
					 	addl.width=this.labelWidth;
						var addf:TextInput=new TextInput();
						addf.styleName="textInput";
						addf.name=name;
						//addf.text=val;
						//addf.editable=isEdit;
						addf.id=name+"_id";
						addf.percentWidth=60;
						ap.addChild(addl);
						if("1"==ISFK && "ICON"!=name){
							var fkinput:FKTextInput=new FKTextInput();
								fkinput.setInfo(mate,null);
								fkinput.document=mate;
								fkinput.styleName="textInput";
								fkinput.name=name;
								fkinput.id=name+"_id";
								ap.addChild(fkinput);
						}else
						if("date"==type){
							var datef1:DateField=new DateField();
							datef1.styleName="textInput";
							//datef1.text=val;
							datef1.percentWidth=60;
							datef1.name=name;
							datef1.id=name+"_id";
							datef1.formatString=dateFormat;
							datef1.dayNames=['日','一','二','三','四','五','六']; 
							datef1.monthNames=['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
							if(isSetDefaultValue(name)){
								datef1.text=val;
								datef1.editable=false;
							}
							
							ap.addChild(datef1);
							
							
						}else if("file"==type || "image"==type){
							var nfileSel:FileUpload=new FileUpload();
							nfileSel.styleName="textInput";
							nfileSel.percentWidth=60;
							//fileSel.setText(val);
							nfileSel.name=name;
							nfileSel.id=name+"_id";
							if(null!=this.comp)
								nfileSel.setComName(this.comp);
							else
								nfileSel.setComName(this.doc.@TABLECODE);
							nfileSel.setTable(this.doc.@TABLE);
							if(isSetDefaultValue(name)){
								nfileSel.setText(val);
								nfileSel.setEditAble(false);
								//nfileSel.browsebtn.enabled=false;
							}
							
							ap.addChild(nfileSel);
						}else {
							if("ICON"==name ){
								var icon:IconLabel=new IconLabel();
								//img.setName(val);
								icon.setText(val);
								ap.addChild(icon);
							}else if("varchar"==type && size>50){
								var tarea:TextArea=new TextArea();
								//tarea.styleName="textInput";
								tarea.styleName="textArea";
								tarea.percentWidth=60;
								//tarea.setText(val);
								tarea.percentHeight=15
								//tarea.text=val;
								tarea.name=name;
								tarea.id=name+"_id";
								if(isSetDefaultValue(name)){
									tarea.text=val;
									tarea.editable=false;
								}
								ap.addChild(tarea);
								//tarea.addEventListener(flash.events.FocusEvent.FOCUS_OUT,fieldChange);
							}else if("1"==haveList){//添加list
								 var ls:ComboBox=new ComboBox();
								 ls.dataProvider=mate.LIST.OBJECT;
								 //ls.percentWidth=60;
								 ls.styleName="textInput";
								//tarea.setText(val);
								//ls.height=90;
								
								if(isSetDefaultValue(name)){
									ls.text=val;
									ls.editable=false;
								}
								ls.name=name;
								ls.id=name+"_id";
								ap.addChild(ls);
							}else{
								//处理创建人
								
								
								if("CREATE_ID"==name){
									//把当前用户id给create_di
									var doc:XML=AppShareObj.getUserDoc();
									if(null!=doc)
										addf.text=doc.@ID;
					
									ap.addChild(addf);
								}else if("CREATE_NAME"==name){
									//把当前用户name给create_name
									//addf.editable=false;
									//addf.text="设置成当前用户！"
									var namelabel:Label=new Label();
									namelabel.text=val;
									//namelabel.text=doc.@NAME;
									namelabel.styleName="textInput";
									ap.addChild(namelabel);
								}else{
									if(isSetDefaultValue(name)){
										addf.text=val;
										addf.editable=false;
									}

									ap.addChild(addf);
								}
								
							}
							
	//						haveList
						}
						
						ap.visible=dp;
						ap.name="box"+name;
						
						if(!dp){
							p.percentHeight=0;
						}
						if("CREATE_ID"==name){
							ap.visible=false;
							ap.height=0;
						}
						addInputLs.push(ap);
						addInputMap[name]=ap;
					}
					
					//panel.addChild(p);	
				}//end if mate
			}catch(e:Error){
				Alert.show(e.getStackTrace());
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
			var children:Array=this.panel.getChildren();
			return this.panel;
		}
	}
}