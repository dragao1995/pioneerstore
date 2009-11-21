package com.pioneer.app.xml;

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

import com.frame.util.security.ApplicationValidate;
import com.pioneer.app.db.DBConnectionManager;
import com.pioneer.app.db.OADBUtil;
import com.pioneer.app.db.ResultSetToCDS;
import com.pioneer.app.util.Dom4jUtil;

public class ShaPanService {
	public  final static String GET_PHYSICAL_OBJ_FABU="physicalObjFabu";
	
	public  final static String GET_PHYSICAL_OBJ_DEL="physicalObjDel";
	public  final static String GET_PHYSICAL_OBJ_TYPE="physicalObjType";
	public  final static String GET_PHYSICAL_OBJ_SEARCH="search";
	public  final static String GET_PHYSICAL_OBJ_FRESH_ICON="freshObjIcon";
	public  final static String GET_PHYSICAL_OBJ_LAST_DATA="lastdetail";
	public static Document FirstFilter(Document doc){
		Document rtdoc=null;
		Element root=doc.getRootElement();
		boolean flg=ApplicationValidate.getInst().isValidate();
		
		if(!flg){
			String message=ApplicationValidate.getInst().getMessage();
			rtdoc=DocumentHelper.createDocument();
			Element rtroot=rtdoc.addElement("DATAPACKET");
			rtroot.addAttribute("code", "error");
			rtroot.addAttribute("message", message);
		}else
		if(null!=root){
			String action=root.valueOf("@ACTION");
			if(ShaPanService.GET_PHYSICAL_OBJ_FABU.equalsIgnoreCase(action)){
				rtdoc=physicalObjFabu(doc);
				
			}else if(ShaPanService.GET_PHYSICAL_OBJ_DEL.equalsIgnoreCase(action)){
				rtdoc=physicalObjDel(doc);
			}else if(ShaPanService.GET_PHYSICAL_OBJ_TYPE.equalsIgnoreCase(action)){
				rtdoc=physicalObjType();
			}else if(ShaPanService.GET_PHYSICAL_OBJ_SEARCH.equalsIgnoreCase(action)){
				rtdoc=physicalObjSearch(doc);
			}else if(ShaPanService.GET_PHYSICAL_OBJ_FRESH_ICON.equalsIgnoreCase(action)){
				rtdoc=physicalObjFreshIcon(doc);
			}else if(ShaPanService.GET_PHYSICAL_OBJ_LAST_DATA.equalsIgnoreCase(action)){
				rtdoc=lastData(doc);
			}
			
			
		}//
		return rtdoc;
		}
	private static Document physicalObjFabu(Document doc){
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String type=root.valueOf("@TYPE");
			String objId=root.valueOf("@OBJ");
			List users=doc.selectNodes("/DATAPACKET/users/user");
			Iterator it=users.iterator();
			Element elt=null;
			StringBuffer sqlbf=new StringBuffer(200);
			Connection	con=null;
			Element rtroot=null;
			try{
				rtdoc=DocumentHelper.createDocument();
				rtroot=rtdoc.addElement("DATAPACKET");
				con= DBConnectionManager.getInstance().getConn();
				
				while(it.hasNext()){
					elt=(Element)it.next();
					String userId=elt.valueOf("@ID");
					sqlbf.append("insert into ctl_user_physical_obj(USER, PHYSICAL_OBJ, TYPE) values(")
					.append(userId).append(",")
					.append(objId).append(",")
					.append(type).append(")");
					
					OADBUtil.runUpdateSql(sqlbf.toString(), null, con);
					sqlbf.setLength(0);
				}
				
			}catch(Exception e){
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
				//con.rollback();
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
	private static Document physicalObjDel(Document doc){
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String type=root.valueOf("@TYPE");
			String objId=root.valueOf("@OBJ");
			
			Connection	con=null;
			Element rtroot=null;
			try{
				rtdoc=DocumentHelper.createDocument();
				rtroot=rtdoc.addElement("DATAPACKET");
				con= DBConnectionManager.getInstance().getConn();
				StringBuffer bf=new StringBuffer(200);
				bf.append("delete from ctl_user_physical_obj where PHYSICAL_OBJ=").append(objId).append(" and type=").append(type);
				OADBUtil.runUpdateSql(bf.toString(), null, con);

			}catch(Exception e){
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
				//con.rollback();
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
	private static Document physicalObjType(){
		
		Document doc=null;
		Connection con=DBConnectionManager.getInstance().getConn();
		String tblName="ctl_physical_obj_type";
		Element rtroot=null;
		try {
			doc=doFindObjectsCDSByCondition(tblName, null, "PID is null", con, true);
			String sql="select * from "+tblName +" where PID is not null";
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
					AddPhysicalObj2Type(elt);
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
			if(rtroot==null){
				doc=DocumentHelper.createDocument();
				rtroot=doc.addElement("DATAPACKET");
			}
			rtroot.addAttribute("code", "error");
			rtroot.addAttribute("message", "系统错误！");
		}finally{
			try {
				
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return doc;
	}
	private static void AddPhysicalObj2Type(Element elt){
		String id=elt.valueOf("@ID");
		List ls=TableInfoService.getInst().getTables();
		Iterator it=ls.iterator();
		while(it.hasNext()){
			Map map=(Map)it.next();
			String typeId=String.valueOf(map.get("objtype"));
			if(id.equalsIgnoreCase(typeId)){
				Element newElt=elt.addElement("ROW");
				newElt.setAttributeValue("ID",String.valueOf(map.get("id")));
				newElt.setAttributeValue("PID",id);
				newElt.setAttributeValue("NAME",String.valueOf(map.get("name")));
				newElt.setAttributeValue("CODE", String.valueOf(map.get("code")));
				
			}
		}
	}
private static Document physicalObjSearch(Document doc){

	Document rtdoc=null;
	Element rtroot=null;
	Connection con=null;
	if(null!=doc){
		try{
			boolean flg=false;
			boolean cflg=false;
			Element root=doc.getRootElement();
			StringBuffer wherebf=new StringBuffer(600);
			StringBuffer nocompanwybf=new StringBuffer(600);
			List ocompanyElts=doc.selectNodes("/DATAPACKETS/ocompanys/ocompany");
			
			Element eltKey=(Element)doc.selectSingleNode("/DATAPACKETS/key");
			String keyStr=null;
			if(null!=eltKey){
				keyStr=eltKey.valueOf("@value");
				keyStr=keyStr.trim();
			}
			
			Element areaElt=(Element)doc.selectSingleNode("/DATAPACKETS/areas");
			String areaType=null;
			if(null!=areaElt){
				areaType=areaElt.valueOf("@type");
			}
			List areas=doc.selectNodes("/DATAPACKETS/areas/area");
			//添加key限制
			if(null!=keyStr && !"".equalsIgnoreCase(keyStr)){
				flg=true;cflg=true;
				wherebf.append(" NAME like'%").append(keyStr).append("%'");
				nocompanwybf.append(" NAME like'%").append(keyStr).append("%'");
			}
			//添加集团限制
			if(null!=ocompanyElts && ocompanyElts.size()>0){
				if(flg){
					wherebf.append(" and ");
					
				}
				flg=true;
				Iterator cpit=ocompanyElts.iterator();
				if(cpit.hasNext()){
					Element elt=(Element)cpit.next();
					wherebf.append(" COMPANY not in (").append(elt.valueOf("@ID"));
				}
				while(cpit.hasNext()){
					Element elt=(Element)cpit.next();
					String cpid=elt.valueOf("@ID");
					wherebf.append(",").append(elt.valueOf("@ID"));
				}
				wherebf.append(")");
			}
			//添加地域限制
			if(null!=areas && areas.size()>0){
				if(flg){
					wherebf.append(" and ");
				}
				//if(cflg)nocompanwybf.append(" and ");
				flg=true;
				cflg=true;
				Iterator areait=areas.iterator();
				if(areait.hasNext()){
					Element elt=(Element)areait.next();
					wherebf.append(" AREA  in (").append(elt.valueOf("@ID"));
					//nocompanwybf.append(" AREA  in (").append(elt.valueOf("@ID"));
				}
				while(areait.hasNext()){
					Element elt=(Element)areait.next();
					wherebf.append(",").append(elt.valueOf("@ID"));
					//nocompanwybf.append(",").append(elt.valueOf("@ID"));
				}
				wherebf.append(")");
				//nocompanwybf.append(")");
			}
			
			List objs=doc.selectNodes("/DATAPACKETS/objs/obj");
			
			//组织不同表的sql。提取obj
			Iterator objit=objs.iterator();
			StringBuffer sql=new StringBuffer(600);
			rtdoc=DocumentHelper.createDocument();
			rtroot=rtdoc.addElement("DATAPACKET");
			con=DBConnectionManager.getInstance().getConn();
			while(objit.hasNext()){
				try{
					Element elt=(Element)objit.next();
					if("ctl_area".equalsIgnoreCase(elt.valueOf("@CODE"))){
						sql.append("SELECT ID,NAME,ICON,X,Y  FROM ").append(elt.valueOf("@CODE"));
						if(nocompanwybf.toString().length()>1){
							sql.append(" WHERE ").append(nocompanwybf.toString());
						}
						
					}else{
						sql.append("SELECT ID,NAME,ICON,X,Y FROM ").append(elt.valueOf("@CODE"));
						
						if(wherebf.toString().length()>1){
							sql.append(" WHERE ").append(wherebf.toString());
						}
					}
					
					
					//System.out.println("sql="+sql.toString());
					ResultSet rs=OADBUtil.runQuerySql(sql.toString(), con);
					while(rs.next()){
						Element objelt=rtroot.addElement("OBJ");
						objelt.addAttribute("ID", rs.getString("ID"));
						objelt.addAttribute("NAME", rs.getString("NAME"));
						objelt.addAttribute("TYPE", elt.valueOf("@NAME"));
						objelt.addAttribute("ICON", rs.getString("ICON"));
						objelt.addAttribute("X", rs.getString("X"));
						objelt.addAttribute("Y", rs.getString("Y"));
					}
					
					sql=new StringBuffer(600);
				}catch(Exception e){
					System.out.println("error sql is ："+sql.toString());
				}
				
			}
		}catch(Exception e){
			rtroot.addAttribute("code", "error");
			rtroot.addAttribute("message", "系统错误！");
			//con.rollback();
		}finally{
			try{con.close();}catch(Exception ee){}
		}
		
	}
	return rtdoc;

}

private static Document physicalObjFreshIcon(Document doc){
	Document rtdoc=null;
	if(null!=doc){
		Element root=doc.getRootElement();
		String tableName=root.valueOf("@OBJECT");
		String companyId=root.valueOf("@COMPANY");
		String icon=root.valueOf("@ICON");
		String tblCode=TableInfoService.getInst().getTableCode(tableName);
		Connection	con=null;
		Element rtroot=null;
		try{
			rtdoc=DocumentHelper.createDocument();
			rtroot=rtdoc.addElement("DATAPACKET");
			con= DBConnectionManager.getInstance().getConn();
			StringBuffer bf=new StringBuffer(200);
			//update ctl_balance_office set icon="111" where company=1
			bf.append("update ").append(tblCode).append(" set icon='").append(icon).append("' where company=").append(companyId);
			OADBUtil.runUpdateSql(bf.toString(), null, con);
			
		}catch(Exception e){
			rtroot.addAttribute("code", "error");
			rtroot.addAttribute("message", "系统错误！");
			//con.rollback();
		}finally{
			try{con.close();}catch(Exception ee){}
		}
		
	}
	return rtdoc;
}
private static Document lastData(Document doc){//DATAMONTH
	Document rtdoc=null;
	if(null!=doc){
		Element root=doc.getRootElement();
		String tableName=root.valueOf("@TABLE");
		String objId=root.valueOf("@OBJID");
		String dataMonth=root.valueOf("@DATAMONTH");
		String tblCode=TableInfoService.getInst().getTableCode(tableName);
		Connection	con=null;
		Element rtroot=null;
		try{
			
			con= DBConnectionManager.getInstance().getConn();
			String maxsql=null;
			if(null==dataMonth || "".equals(dataMonth)){
				maxsql="select ID from "+tblCode+" where DATAMONTH in (select max(DATAMONTH) DATAMONTH from "+tblCode+" where PHYOBJ="+objId+")";
				ResultSet rs=OADBUtil.runQuerySql(maxsql, con);
				if(rs.next()){
					int id=rs.getInt("ID");
					StringBuffer bf=new StringBuffer(200);
					root.addAttribute("ID", String.valueOf(id));
					root.addAttribute("ACTION", "detail");
					rtdoc=CDSDataService.FirstFilter(doc);
				}
			}else{
				root.addAttribute("WHERE", " PHYOBJ="+objId+" and DATAMONTH="+dataMonth);
				root.addAttribute("ACTION", "detail");
				rtdoc=CDSDataService.FirstFilter(doc);
			}
			
			
			
		}catch(Exception e){
			if(null==rtdoc){
				rtdoc=DocumentHelper.createDocument();
			}
			rtroot=rtdoc.addElement("DATAPACKETS");
			rtroot.addAttribute("code", "error");
			rtroot.addAttribute("message", "系统错误！");
			//con.rollback();
		}finally{
			try{con.close();}catch(Exception ee){}
		}
		
	}
	return rtdoc;
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
		Document doc=physicalObjType();
		Dom4jUtil.writeDocToFile(doc, "GBK", "j:/test.xml");
	}
}	
		

