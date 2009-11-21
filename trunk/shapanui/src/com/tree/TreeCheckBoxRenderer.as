package com.tree
{
	import flash.events.Event;
	
	import mx.controls.Alert;
	import mx.controls.CheckBox;
	import mx.controls.treeClasses.TreeItemRenderer;
	import mx.controls.treeClasses.TreeListData;  

  
/**  
 *   注意:selected属性很重要, 此属性在数据源内, 标致当前节点的CheckBox是否选中.点击CheckBox会自动改变响应的数据源.  如果想知道哪些被选中的话, 就遍历Tree的dataProvider吧~ 
      谨以此文贡献给战斗在RIA第一线的兄弟姐妹们. 
 * 支持CheckBox的TreeItemRenderer    
 */    
public class TreeCheckBoxRenderer extends TreeItemRenderer   
{   
    public function TreeCheckBoxRenderer()   
    {   
        super();   
    }   
       
    /**  
     * 表示CheckBox控件从data中所取数据的字段  
     */        
    private var _selectedField:String = "selected";   
       
    protected var checkBox:CheckBox;   
       
    /**  
     * 构建CheckBox  
     */        
    override protected function createChildren():void  
    {   
        super.createChildren();   
        checkBox = new CheckBox();   
        addChild( checkBox );   
        checkBox.addEventListener(Event.CHANGE, changeHandler);   
    }   
       
    /**  
     * 点击checkbox时,更新dataProvider  
     * @param event  
     */        
    protected function changeHandler( event:Event ):void  
    {   
    	/*
    	 if( data && data[_selectedField] != undefined )   
        {   
            data[_selectedField] = checkBox.selected;   
        }  
    	 */
    	 var xmldata:XML=XML(data);
    	 
        if( null!=xmldata)   
        {   
        	
            xmldata.@selected = checkBox.selected;   
        }  
    }    
       
    /**  
     * 初始化控件时, 给checkbox赋值  
     */        
    override protected function commitProperties():void  
    {   
        super.commitProperties();   
        trace(data.@selected) 
          var xmldata:XML=XML(data);
        if( null!=xmldata)   
        {   
        	var flg:String=xmldata.@selected;
        	//Alert.show("null==flg="+(null==flg));
        	if(null==flg || ""==flg){
        		xmldata.@selected=false;
        		
        	}
        	var sel:String=xmldata.@selected;
        	if("false"==sel){
        		checkBox.selected = false; 
        	}else if("true"==sel){
        		checkBox.selected = true; 
        	}
              
        }   
        else  
        {   
            checkBox.selected = false;   
        }   
    }   
       
    /**  
     * 重置itemRenderer的宽度  
     */        
    override protected function measure():void  
    {   
        super.measure();   
        measuredWidth += checkBox.getExplicitOrMeasuredWidth();   
    }   
       
    /**  
     * 重新排列位置, 将label后移  
     * @param unscaledWidth  
     * @param unscaledHeight  
     */        
    override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void  
    {   
        super.updateDisplayList(unscaledWidth, unscaledHeight);   
        var startx:Number = data ? TreeListData( listData ).indent : 0;   
           
        if (disclosureIcon)   
        {   
            disclosureIcon.x = startx;   
  
            startx = disclosureIcon.x + disclosureIcon.width;   
               
            disclosureIcon.setActualSize(disclosureIcon.width,   
                                         disclosureIcon.height);   
               
            disclosureIcon.visible = data ?   
                                     TreeListData( listData ).hasChildren :   
                                     false;   
        }   
           
        if (icon)   
        {   
            icon.x = startx;   
            startx = icon.x + icon.measuredWidth;   
            icon.setActualSize(icon.measuredWidth, icon.measuredHeight);   
        }   
           
        checkBox.move(startx, ( unscaledHeight - checkBox.height ) / 2 );   
           
        label.x = startx + checkBox.getExplicitOrMeasuredWidth();   
    }   
}   

}