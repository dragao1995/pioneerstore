package com.pioneer.app.cache;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;

import com.pioneer.app.db.DBConnectionManager;
import com.pioneer.app.db.OADBUtil;
import com.pioneer.app.db.ResultSetToCDS;
import com.pioneer.app.util.DatCache;
import com.pioneer.app.xml.TableInfoService;

public class AppCache {

	private List tbl=new ArrayList();
	private AppCache(){
		init();
	}
	
	public void init(){
		String sql="select distinct fk_table from s_fk_define";
		Connection	con=null;
		try {
			con= DBConnectionManager.getInstance().getConn();
			ResultSet rs=OADBUtil.runQuerySql(sql, con);
			while(rs.next()){
				String tblName=rs.getString("fk_table");
				cacheTable(tblName);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{con.close();}catch(Exception ee){}
		}
		
		cacheTable("记录图标");
	}
	
	public void cacheTable(String tblName){
		try {
			String tblcode=TableInfoService.getInst().getTableCode(tblName);
			String sql="select * from "+tblcode;
			Document 	rtdoc=ResultSetToCDS.BuildCDSWithMate(tblName, sql);
			DatCache.inst().setDat("tblful_"+tblName, rtdoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Document getTableInfo(String tblName){
		Document doc=(Document)DatCache.inst().getDat("tblful_"+tblName);
		if(null==doc){
			this.init();
			doc=(Document)DatCache.inst().getDat("tblful_"+tblName);
		}
		return doc;
	}
	
	public static Document getTree(String tblCode){
		return (Document)DatCache.inst().getDat("tree_"+tblCode);
	}
	public static void cacheTree(String tblCode,Document doc){
		try{
			DatCache.inst().setDat("tree_"+tblCode,doc);
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	public static void main(String[] args) {
		AppCache.getInst().cacheTable("类型");
	}
	
	public static AppCache getInst(){
		return _inst;
	}
	
	private static AppCache _inst=new AppCache();
	
	
}
