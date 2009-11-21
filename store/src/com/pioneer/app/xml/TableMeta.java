package com.pioneer.app.xml;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.pioneer.app.db.DBConnectionManager;
import com.pioneer.app.db.OADBUtil;
import com.pioneer.app.util.Dom4jUtil;
import com.pioneer.app.vo.Column;
import com.pioneer.app.xml.meta.IMetaDocRender;
import com.pioneer.app.xml.meta.MetaRenderFactory;

public class TableMeta {
	public static String foreignKeyName="ISFK";
	public static String loadChild="LOADCHIND";
	public static String addFlag="1";
	public static String editFlag="5";
	public static String deleteFlag="7";
	
	public static String DB_TYPE_INT="int";
	public static String DB_TYPE_VARCHAR="varchar";
	public static String DB_TYPE_FILE="file";
	public static String DB_TYPE_IMAGE="image";
	public static String DB_TYPE_DOUBLE="double";
	public static String DB_TYPE_FLOAT="float";
	public static String DB_TYPE_DATE="date";
	
	//private Map tblInfo=new HashMap();
	
	
	/**
	 * @desc:
	 * @param dbName
	 * @param ptblCode
	 * @param ctblCode
	 * @param fkCol 子表的参考键
	 * @return :
	 * @auther : Administrator
	 * @date : Mar 31, 2009
	 */
	public  Document getTableMeta(String dbName,String ptblName,String ctblName,String fkCol){
		
		String ptblCode=TableInfoService.getInst().getTableCode(ptblName);
		String ctblCode=TableInfoService.getInst().getTableCode(ctblName);
		Document doc=null;
		String objkey=null;
		if(null==ptblCode )return null;
		else{
			
		
			if(null==ctblCode)
				objkey=ptblCode;
			else
				objkey=ptblCode+ctblCode;
			
			}
		if(doc==null){
			Connection con=null;
			try {
				doc=org.dom4j.DocumentFactory.getInstance().createDocument("UTF-8");
				Element root =null,metaElt,tblElt,fieldElt;
				doc.addElement("DATAPACKET");
				root =doc.getRootElement();
				metaElt=root.addElement("METADATA");
				tblElt=metaElt.addElement("TABLE");
				tblElt.addAttribute("LOADCONDITION", "");
				tblElt.addAttribute("MAXROW", "500");
				tblElt.addAttribute("NAME", ptblName);
				con=DBConnectionManager.getInstance().getConn();
				addPrimaryKey(tblElt,dbName,con);
				java.sql.DatabaseMetaData dbmeta=con.getMetaData();
				ResultSet tblcolrs=dbmeta.getColumns(dbName,dbName,ptblCode, null);
				while(tblcolrs.next()){
					fieldElt=tblElt.addElement("FIELD");
					fieldElt.addAttribute("NAME", tblcolrs.getString("COLUMN_NAME"));
					fieldElt.addAttribute("TYPE", tblcolrs.getString("TYPE_NAME"));
					fieldElt.addAttribute("SIZE", tblcolrs.getString("COLUMN_SIZE"));
					fieldElt.addAttribute("REMARK", tblcolrs.getString("REMARKS"));
					if("pid".equalsIgnoreCase(tblcolrs.getString("COLUMN_NAME"))){
						fieldElt.addAttribute("DP", "0");
					}
				}
			//取出参考列信息
				List pfkcols=TableInfoService.getInst().getFKTables(ptblName);
				if(null!=pfkcols){
					Iterator colsit=pfkcols.iterator();
					Column col=null;
					while(colsit.hasNext()){
						col=(Column)colsit.next();
						Element fkelt=(Element)tblElt.selectSingleNode("FIELD[@NAME='"+col.getName()+"']");
						fkelt.addAttribute("ISFK", "1");
						fkelt.addAttribute("FKTBL", col.getFkTable());
						fkelt.addAttribute("FKID", col.getFkTableId());
						fkelt.addAttribute("WIDTH", String.valueOf(col.getWinWidth()));
						fkelt.addAttribute("HEIGHT", String.valueOf(col.getWinHeight()));
						fkelt.addAttribute("LABELWIDTH", String.valueOf(col.getLabelWidth()));
						fkelt.addAttribute("STRUCT", col.getFkTableStruct());
					}
				}
				//取出列的单位
				{
					String unitsql="select COLUMN_NAME,UNIT from s_column_unit where TABLE_NAME='"+ptblName+"'";
					ResultSet rs=OADBUtil.runQuerySql(unitsql, con);
					while(rs.next()){
						String colName=rs.getString("COLUMN_NAME");
						String unit=rs.getString("UNIT");
						Element elt=(Element)tblElt.selectSingleNode("FIELD[@NAME='"+colName+"']");
						elt.addAttribute("UNIT", unit);
					}
				}
				{//取出列的取值列表
					String lssql="select * from s_column_value where table_name='"+ptblName+"'";
					ResultSet rs=OADBUtil.runQuerySql(lssql, con);
					while(rs.next()){
						String colName=rs.getString("COLUMN_NAME");
						String value=rs.getString("VALUE_ONE");
						Element elt=(Element)tblElt.selectSingleNode("FIELD[@NAME='"+colName+"']");
						elt.addAttribute("HAVELIST", "1");
						Element lsElt=(Element)elt.selectSingleNode("LIST");
						if(null==lsElt){
							lsElt=elt.addElement("LIST");
						}
						Element obj=lsElt.addElement("OBJECT");
						obj.setText(value);
					}
					
				}
				
				if(null!=ctblCode){
					ResultSet ctblcolrs=dbmeta.getColumns(dbName,dbName,ctblCode, null);
					Element ctblElt,cfieldElt;
					ctblElt=tblElt.addElement("TABLE");
					ctblElt.addAttribute("NAME", ctblCode);
					String colName=null;
					addPrimaryKey(ctblElt,dbName,con);
					while(ctblcolrs.next()){
						cfieldElt=ctblElt.addElement("FIELD");
						colName=ctblcolrs.getString("COLUMN_NAME");
						if(colName.toLowerCase().equalsIgnoreCase(fkCol)){
							cfieldElt.addAttribute(foreignKeyName, "true");
						}
						cfieldElt.addAttribute("NAME",colName );
						cfieldElt.addAttribute("TYPE", ctblcolrs.getString("TYPE_NAME"));
						cfieldElt.addAttribute("SIZE", ctblcolrs.getString("COLUMN_SIZE"));
						cfieldElt.addAttribute("REMARK", ctblcolrs.getString("REMARKS"));
					}
				//取出参考列信息
					List fkcols=TableInfoService.getInst().getFKTables(ctblName);
					if(null!=fkcols){
						Iterator colsit=fkcols.iterator();
						Column col=null;
						while(colsit.hasNext()){
							col=(Column)colsit.next();
							Element fkelt=(Element)ctblElt.selectSingleNode("FIELD[@NAME='"+col.getName()+"']");
							fkelt.addAttribute("ISFK", "1");
							fkelt.addAttribute("FKTBL", col.getFkTable());
							fkelt.addAttribute("FKID", col.getFkTableId());
						}
					}
				}
				//this.tblInfo.put(objkey,doc);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try{con.close();}catch(Exception ee){}
			}
		}
		IMetaDocRender render=MetaRenderFactory.getInst().getRender(ptblName);
		if(null!=render){
			render.render(doc);
		}
		//meta 文件的修改，不需要开发人员写代码，由数据库中的数据支持修改
		
		Connection	con=null;
		try {
			String sql="select * from s_colum_type where TABLE_NAME='"+ptblName+"'";
			con= DBConnectionManager.getInstance().getConn();
			ResultSet rs=OADBUtil.runQuerySql(sql, con);
			while(rs.next()){
				String colName=rs.getString("COLUM");
				String type=rs.getString("TYPE");
				Element elt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='"+colName+"']");
				if(null!=elt){
					elt.addAttribute("TYPE", type);
				}
			}
		}catch(Exception e){
			
		}finally{
			try{con.close();}catch(Exception ee){}
		}
		//Dom4jUtil.writeDocToFile(doc, "GBK", "d:/tableMeta.xml");
		return doc;
	}
	
	private static void addPrimaryKey(Element tblElt,String dbName,Connection con){
		
		String tblName=tblElt.valueOf("@NAME");
		String tblCode=TableInfoService.getInst().getTableCode(tblName);
		if(null!=tblName){
			
			try {
				java.sql.DatabaseMetaData dbmeta=con.getMetaData();
				ResultSet dbrs=dbmeta.getPrimaryKeys(dbName, dbName, tblCode);
				String colName=null;
				Element keyElts,keyElt;
				keyElts=tblElt.addElement("KEYS");
				while(dbrs.next()){
					colName=dbrs.getString("COLUMN_NAME");
					keyElt=keyElts.addElement("KEY");
					keyElt.addAttribute("NAME", colName);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}


	
	public static TableMeta getInst(){
		return _inst;
	}
	private static TableMeta _inst=new TableMeta();
	public static void main(String []args){
		
		Document doc=TableMeta.getInst().getTableMeta("store","商品",null,null);
		Dom4jUtil.writeDocToFile(doc, "GBK", "c:/tpl.xml");
	}
}
