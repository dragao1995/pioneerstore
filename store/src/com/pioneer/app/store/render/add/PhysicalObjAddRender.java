package com.pioneer.app.store.render.add;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.pioneer.app.db.DBConnectionManager;
import com.pioneer.app.db.OADBUtil;
import com.pioneer.app.xml.TableInfoService;
import com.pioneer.app.xml.add.IAddDocRender;
import com.pioneer.app.xml.delete.DelRenderFactory;
import com.pioneer.app.xml.delete.IDelDocRender;

/**
 * @author ArcGISSOC
 *
 */
public class PhysicalObjAddRender implements IAddDocRender {

	public void render(Document doc) {
		if(null!=doc){
			Element root=doc.getRootElement();
			String type=root.valueOf("@TABLE");
			if("地理对象存储".equals(type)){
				Element row=(Element)doc.selectSingleNode("/DATAPACKET/TABLEDATA/ROWDATA/ROW");
				String tblName=row.valueOf("@PHYSICAL_NAME");
				String id=row.valueOf("@PHYSICAL_ID");
				String tblCode=TableInfoService.getInst().getTableCode(tblName);
				String sql="select X,Y from "+tblCode+" where ID="+id;
				System.out.println(sql);
				Connection	con=null;
				try {
					con= DBConnectionManager.getInstance().getConn();
					//COMNAME
					ResultSet rs=OADBUtil.runQuerySql(sql, con);
					String x=null,y=null;
					if(rs.next()){
						x=rs.getString("X");
						y=rs.getString("Y");
						
					}
					String phid=root.valueOf("ID");
					String upsql="update ctl_physical_obj_store set X="+x+" ,Y="+y+" where ID="+phid;
					
					boolean flg=OADBUtil.runUpdateSql(upsql, null, con);
				}catch(Exception e){
					
				}finally{
					try{con.close();}catch(Exception ee){}
				}
			}
			
			
		}
		System.out.println(doc.asXML());
	}

}
