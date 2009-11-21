package com.pioneer.app.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

public class PaginationUtil {
	private int rowCount=0;//记录条数。
	
	private int pageIndex=0;//要查看页的下标
	
	private int pageSize=10;//每页显示的记录数。
	
	private int start=0;//要显示页的开始下标
	
	private int end=0;//要显示页的结束下标
	
	private int pageSet=4;//访问一次显示的条数。
	
	private List colsList=null;
	private Document pages=null;
	private Document leavePages=null;
	
	/**
	 * 创建并剪切用户请求的数据。
	 * @param rows
	 * @return
	 */
	public Document buildShowPagesAsCut(List rows){
		Document mypages=null;
		Document showPage=null;
		if(null!=rows && rows.size()>0){
			this.rowCount=rows.size();
			mypages=DocumentFactory.getInstance().createDocument("utf-8");
			Element root=mypages.addElement("PAGES");
			root.addAttribute("ROWSNUM",this.rowCount+"");
			Element colsElt=root.addElement("COLS");
			Map row=(Map)rows.get(0);
			Iterator cols=row.keySet().iterator();
			colsList=new ArrayList();
			String colName=null;
			while(cols.hasNext()){
				colName=(String)cols.next();
				colsElt.addElement("col").addAttribute("NAME",colName);
				colsList.add(colName);
			}
			int pageCount=0;
			if(this.rowCount%this.pageSize>0){
				pageCount=this.rowCount/this.pageSize+1;
			}else{
				pageCount=this.rowCount/this.pageSize;
			}
			int start=0,end=this.pageSize;
			for(int p=0;p<pageCount;p++){
				Element page=root.addElement("PAGE");
				page.addAttribute("INDEX",p+"");
				start= this.pageSize*p;
                end= start+this.pageSize;
                Map rowMap=null;
                String name=null,val=null;
                Element rowElt=null;
                for(int i = start;i<end;i++){
                   rowElt=page.addElement("row");
                   if(i< rows.size()){
                	   rowMap=(Map)rows.get(i);
                	   for(int c=0;c<this.colsList.size();c++){
                		   name=(String)this.colsList.get(c);
                		   val=(String)rowMap.get(name);
                		   rowElt.addAttribute(name,val);
                	   }
                   }else{
                	  continue;
                   }
                }
			}
			showPage=this.getShowPagesAsCut(mypages);
		}
		
		return showPage;
	}
	
	/**
	 * 创建并拷贝用户请求的数据。
	 * @param rows
	 * @return
	 */
	public Document buildShowPagesAsCopy(List rows){
		Document mypages=null;
		Document showPage=null;
		if(null!=rows && rows.size()>0){
			this.rowCount=rows.size();
			mypages=DocumentFactory.getInstance().createDocument("utf-8");
			Element root=mypages.addElement("PAGES");
			root.addAttribute("ROWSNUM",this.rowCount+"");
			Element colsElt=root.addElement("COLS");
			Map row=(Map)rows.get(0);
			Iterator cols=row.keySet().iterator();
			colsList=new ArrayList();
			String colName=null;
			while(cols.hasNext()){
				colName=(String)cols.next();
				colsElt.addElement("col").addAttribute("NAME",colName);
				colsList.add(colName);
			}
			int pageCount=0;
			if(this.rowCount%this.pageSize>0){
				pageCount=this.rowCount/this.pageSize+1;
			}else{
				pageCount=this.rowCount/this.pageSize;
			}
			int start=0,end=this.pageSize;
			for(int p=0;p<pageCount;p++){
				Element page=root.addElement("PAGE");
				page.addAttribute("INDEX",p+"");
				start= this.pageSize*p;
                end= start+this.pageSize;
                Map rowMap=null;
                String name=null,val=null;
                Element rowElt=null;
                for(int i = start;i<end;i++){
                   
                   if(i< rows.size()){
                	   rowElt=page.addElement("row");
                	   rowMap=(Map)rows.get(i);
                	   for(int c=0;c<this.colsList.size();c++){
                		   name=(String)this.colsList.get(c);
                		   val=(String)rowMap.get(name);
                		   rowElt.addAttribute(name,val);
                	   }
                   }else{
                	  break;
                   }
                }
			}
			this.leavePages=mypages;
			showPage=this.getShowPagesAsCopy(mypages);
		}
		
		return showPage;
	}
	
	/**
	 * 把用户请求的数据剪切出来。
	 * @param doc
	 * @return
	 */
	public Document getShowPagesAsCut(Document doc){
		System.out.println("       doc.asXML()="+doc.asXML());
		if(null!=doc){
			Document mypages=DocumentFactory.getInstance().createDocument("utf-8");
			Element root=mypages.addElement("PAGES");
			String rowCount=doc.getRootElement().valueOf("@ROWSNUM");
			root.addAttribute("ROWSNUM",rowCount);
			Element colsElt=(Element)doc.getRootElement().selectSingleNode("COLS").clone();
			colsElt.setParent(null);
			root.add(colsElt);
			for(int i=0;i<this.pageSet;i++){
				int index=i+this.pageIndex;
				Element pElt=(Element)doc.getRootElement().selectSingleNode("PAGE[@INDEX='"+index+"']");
				if(null==pElt)continue;
				Element newElt=(Element)pElt.clone();
				newElt.setParent(null);
				root.add(newElt);
				doc.getRootElement().remove(pElt);
			}
			this.leavePages=doc;
			return mypages;
		}
		return null;
	}
	/**
	 * 把用户请求的数据拷贝出来。
	 * @param doc
	 * @return
	 */
	public Document getShowPagesAsCopy(Document doc){
//		System.out.println("       doc.asXML()="+doc.asXML());
		if(null!=doc){
			Document mypages=DocumentFactory.getInstance().createDocument("utf-8");
			Element root=mypages.addElement("PAGES");
			String rowCount=doc.getRootElement().valueOf("@ROWSNUM");
			root.addAttribute("ROWSNUM",rowCount);
			Element colsElt=(Element)doc.getRootElement().selectSingleNode("COLS").clone();
			colsElt.setParent(null);
			root.add(colsElt);
			for(int i=0;i<this.pageSet;i++){
				int index=i+this.pageIndex;
				Element pElt=(Element)doc.getRootElement().selectSingleNode("PAGE[@INDEX='"+index+"']");
				if(null==pElt)continue;
				Element newElt=(Element)pElt.clone();
				newElt.setParent(null);
				root.add(newElt);
			}
			return mypages;
		}
		return null;
	}
	
	/**
	 * @return如果是剪切要显示的页面，取到剩下还没有传输给用户的数据。
	 */
	public Document getLeavePages(){
		//如果没有记录，返回null；
		if(null==this.leavePages)return null;
		List pages=this.leavePages.getRootElement().selectNodes("PAGE");
		if(null==pages || pages.size()<1){
			return null;
		}else{
			return this.leavePages;
		}
		
	}
	
	public int getEnd() {
		return end;
	}

	public void setEnd(int end){
		this.end = end;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}
	public int getPageSet() {
		return pageSet;
	}
	public void setPageSet(int pageSet) {
		this.pageSet = pageSet;
	}

}
