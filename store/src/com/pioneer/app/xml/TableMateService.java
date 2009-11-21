package com.pioneer.app.xml;

import org.apache.commons.collections.FastHashMap;
import org.dom4j.Document;

import com.pioneer.app.db.DBConnectionConfig;
import com.pioneer.app.util.Dom4jUtil;

/**
 * @name :  提供表的元数据服务
 * @URL : 
 * @URL_Parameter :  
 * @author Administrator
 * @version : v 1.0
 * @description : 
 */
public class TableMateService {
	
	private FastHashMap mates=new FastHashMap();
	
	private TableMateService(){
		
		try {
			
//			在此注册系统中的表，用于提供表的元数据服务
			//街道
			//mates.put("\u8857\u9053", TableMeta.getInst().getTableMeta("tcxh","\u8857\u9053",null,null));
			//商店
			//mates.put("\u5546\u5e97", TableMeta.getInst().getTableMeta("tcxh","\u5546\u5e97",null,null));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Document getTableMate(String tableName){
		Document doc=(Document)this.mates.get(tableName);
		if(doc==null){
			this.mates.put(tableName, TableMeta.getInst().getTableMeta(DBConnectionConfig.getInstance().getDb_name(),tableName,null,null));
			doc=(Document)this.mates.get(tableName);
			if(null==doc){
				this.mates.put(tableName, TableMeta.getInst().getTableMeta(DBConnectionConfig.getInstance().getDb_name(),tableName,null,null));
			}
		}
		return doc;
	}
	
	public void clearTableMate(String tableName){
		this.mates.put(tableName, null);
	}
	private static TableMateService _inst=new TableMateService();
	
	public static TableMateService getInst(){
		return _inst;
	}
	
	public static void main(String[] args) {
		Document doc=TableMateService.getInst().getTableMate("街道");
		Dom4jUtil.writeDocToFile(doc, "GBK", "c:/kk.xml");
	}

}
