package com.pioneer.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.property.Dom4jAccessor;

import com.pioneer.app.cache.AppCache;
import com.pioneer.app.db.DBConnectionManager;
import com.pioneer.app.db.OADBUtil;
import com.pioneer.app.db.ResultSetToCDS;
import com.pioneer.app.exception.BaseException;
import com.pioneer.app.util.Dom4jUtil;

public class TreeService {
	
	
	/**
	 * @desc:取树的跟
	 * @return
	 * @throws BaseException :
	 * @auther : pioneer
	 * @date : Jul 28, 2009
	 */
	public static Document getRootZone(String tblName)throws Exception{
		Document doc=null;
		Connection con=DBConnectionManager.getInstance().getConn();
		doc=doFindObjectsCDSByCondition(tblName, null, "pid is null", con, true);
		return doc;
	}
	
	/**
	 * @ 取到一个类型下的子类型 
	 * @author pioneer
	 * @param pid
	 * @return
	 * @throws BaseException
	 */
	public static Document getSubZoneCDS(String tblCode,String pid)throws Exception{
		Document doc=null;
		/*Connection con=DBConnectionManager.getInstance().getConn();
		doc=doFindObjectsCDSByCondition(tblName, null, "pid ="+pid, con, true);*/
		Document fullDoc=getAllZone(tblCode);
		doc=DocumentHelper.createDocument();
		Element root=doc.addElement("DATAPACKETS");
		Element dpElt=root.addElement("DATAPACKET");
		Element rowsElt=dpElt.addElement("ROWDATA");
		if(null!=fullDoc){
			List rows=fullDoc.selectNodes("//ROW[@PID="+pid+"]");
			if(null!=rows){
				Iterator rowsIt=rows.iterator();
				while(rowsIt.hasNext()){
					Element elt=(Element)rowsIt.next();
					Element nowElt=(Element)elt.clone();
					nowElt.setParent(null);
					rowsElt.add(nowElt);
				}
			}
			
		}
		return doc;
	}
	
	/**
	 * 取到所有类型信息。
	 * @return
	 * @throws BaseException
	 */
	public static Document getAllZone(String tblCode)throws Exception{
		Document doc=null;
		doc=AppCache.getInst().getTree(tblCode);
		if(null==doc){
			Connection con=DBConnectionManager.getInstance().getConn();
			try {
				doc=doFindObjectsCDSByCondition(tblCode, null, "PID is null", con, true);
				String sql="select * from "+tblCode +" where PID is not null";
				List types=OADBUtil.find(sql,con);
				
				Element rowsElt=(Element)doc.selectSingleNode("/DATAPACKETS/DATAPACKET/ROWDATA");
				List currentRows=rowsElt.selectNodes("ROW");
				List selectedRows=new ArrayList();//刚插入到树中的节点
				List haveSeltObjs=new ArrayList();//已经转换为Element的type对象
				Map  obj=null;
				Element elt=null;
				String id;
				while(null!=currentRows && currentRows.size()>0){
					for(int i=0;i<currentRows.size();i++){//要查找子节点的数据 
						elt=(Element)currentRows.get(i);
						id=elt.valueOf("@ID");
						for(int k=0;k<types.size();k++){
							obj=(Map)types.get(k);
							if(id.equals(String.valueOf(obj.get("PID")))){
								Element newElt=elt.addElement("ROW");
								newElt.setAttributeValue("ID",String.valueOf(obj.get("ID")));
								newElt.setAttributeValue("PID",String.valueOf(obj.get("PID")));
								newElt.setAttributeValue("NAME",String.valueOf(obj.get("NAME")));
								selectedRows.add(newElt);
								haveSeltObjs.add(obj);
							}
						}
					}
					currentRows.clear();
					currentRows.addAll(selectedRows);
					types.removeAll(haveSeltObjs);
					selectedRows.clear();
				}
				
			} catch (Exception e) {
				throw e;
			}finally{
				try {
					
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			AppCache.cacheTree(tblCode, doc);
		}
		
		
		return doc;
	}

	private static Document doFindObjectsCDSByCondition(String table,Object [] showCols,String whereCause,Connection conn,boolean type)throws Exception{
		Document doc=null;
		ResultSet rs=null;
		StringBuffer bf=new StringBuffer(200);
		
		
			 bf.append(" select * ").append(" from  ").append(table);
		
		if(null!=whereCause){
			bf.append(" WHERE ").append(whereCause);
			rs=OADBUtil.runQuerySql(bf.toString(),conn);
			if(type)
				doc=ResultSetToCDS.getInstance().buildCDS(rs);
			else
				doc=ResultSetToCDS.getInstance().buildNormalCDS(rs);
		}
	return doc;
	}
	
	public static void main(String[] args) {
		try {
			//Document doc=getRootZone("t_department");
			//Document doc=getAllZone("shop_good_type");
			Document doc=getSubZoneCDS("shop_good_type","6");
			Dom4jUtil.writeDocToFile(doc, "GBK", "j:/tree.xml");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
