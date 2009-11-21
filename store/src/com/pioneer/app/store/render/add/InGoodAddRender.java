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
public class InGoodAddRender implements IAddDocRender {

	public void render(Document doc) {
		if(null!=doc){
			Element root=doc.getRootElement();
			String type=root.valueOf("@TABLE");
			if("入货单".equals(type)){
				Element row=(Element)doc.selectSingleNode("/DATAPACKET/TABLEDATA/ROWDATA/ROW");
			
				String goodId=row.valueOf("@GOOD");
				String num=row.valueOf("@NUMBER");
				String total=row.valueOf("@TOTALPRICE");
				if(null==goodId || "".equals(goodId) || "null".equals(goodId))return;
				if(null==num || "".equals(num) || "null".equals(num))return;
				if(null==total || "".equals(total) || "null".equals(total))return;
				Connection	con=null;
				try {
					con= DBConnectionManager.getInstance().getConn();
					
					String phid=root.valueOf("ID");
					String upsql="update t_good set INPRICE= (INPRICE*NUMBER+"+total+")/(NUMBER+"+num+")  , NUMBER=NUMBER+"+num+" where ID="+goodId;
					
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
