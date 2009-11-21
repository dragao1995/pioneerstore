package com.pioneer.app.xml;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.frame.util.security.ApplicationValidate;
import com.pioneer.app.cache.AppCache;
import com.pioneer.app.db.DBConnectionManager;
import com.pioneer.app.db.OADBUtil;
import com.pioneer.app.db.ResultSetToCDS;
import com.pioneer.app.util.DateTimeUtil;
import com.pioneer.app.xml.add.AddRenderFactory;
import com.pioneer.app.xml.add.IAddDocRender;
import com.pioneer.app.xml.delete.DelRenderFactory;
import com.pioneer.app.xml.delete.IDelDocRender;
import com.pioneer.app.xml.detail.DetailRenderFactory;
import com.pioneer.app.xml.detail.IDetailDocRender;
import com.pioneer.app.xml.list.IListDocRender;
import com.pioneer.app.xml.list.ListRenderFactory;

/**
 * @name :  xml格式文件的业务处理
 * @URL : 
 * @URL_Parameter :  
 * @author pioneer
 * @version : v 1.0
 * @description : 
 */
public class CDSDataService {
	
	
	public  final static String GET_LIST="list";//取列表命令
	public  final static String GET_DETAIL="detail";//取明细 命令
	public  final static String GET_ADD="add";//添加数据命令
	public  final static String GET_EDIT="edit";//编辑数据命令
	public  final static String GET_DELETE="del";//删除数据命令
	public  final static String GET_RELATION_DELETE="rdel";//删除关系表的内容
	public  final static String GET_RELATION_ADD="radd";//关系表内容的添加
	public  final static String  GET_SYS_LOGIN="login";//登陆系统命令
	
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
			if(CDSDataService.GET_LIST.equalsIgnoreCase(action)){
				rtdoc=ListFilter(doc);
			}else if(CDSDataService.GET_DETAIL.equalsIgnoreCase(action)){
				rtdoc=detailFilter(doc);
//				Dom4jUtil.writeDocToFile(rtdoc, "GBK", "c:/detail.xml");
			}else if(CDSDataService.GET_ADD.equalsIgnoreCase(action)){
				rtdoc=addFilter(doc);
			}else if(CDSDataService.GET_EDIT.equalsIgnoreCase(action)){
				rtdoc=editFilter(doc);
			}else if(CDSDataService.GET_DELETE.equalsIgnoreCase(action)){
				rtdoc=delFilter(doc);
			}else if(CDSDataService.GET_RELATION_DELETE.equalsIgnoreCase(action)){
				rtdoc=relationDel(doc);
			}else if(CDSDataService.GET_RELATION_ADD.equalsIgnoreCase(action)){
				rtdoc=relationAdd(doc);
			}else if(CDSDataService.GET_SYS_LOGIN.equalsIgnoreCase(action)){
				rtdoc=login(doc);
			}else{
				
			}//
		}
		return rtdoc;
	}
public static Document login(Document doc){
		Document rtdoc=DocumentHelper.createDocument();
		
		Element rtroot=rtdoc.addElement("DATAPACKET");
		Element root=doc.getRootElement();
		String name=root.valueOf("@NAME");
		String password=root.valueOf("@PASSWORD");
		rtroot.addAttribute("CODE", name);
		StringBuffer bf=new StringBuffer(100);
		//select ID from t_user where CODE='xiaoli' and password='xiaoli'
		bf.append("select ID,NAME from s_user where CODE='").append(name).append("' and password='").append(password).append("'");
		Connection con=null;
		try{
			con= DBConnectionManager.getInstance().getConn();
			//boolean flg=OADBUtil.runUpdateSql(bf.toString(), null, con);
			ResultSet rs=	OADBUtil.runQuerySql(bf.toString(), con);
			if(rs.next()){
				String id=rs.getString("ID");
				rtroot.addAttribute("ID", id);
				rtroot.addAttribute("NAME", rs.getString("NAME"));
				//取到用户权限。
				String psql="select distinct CODE from s_process where ID in (select rp.PROCESS from s_userrole ur,s_roleprocess rp where ur.role=rp.role and ur.user="+id+")";
				ResultSet prs=	OADBUtil.runQuerySql(psql, con);
				Element elt=null;
				while(prs.next()){
					elt=rtroot.addElement("PRIVIEW");
					elt.setText(prs.getString("CODE"));
				}
			}else{
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "用户名或密码错误！");
			}
		}catch(Exception e){
			rtroot.addAttribute("code", "error");
			rtroot.addAttribute("message", "系统错误！");
		}finally{
			try{con.close();}catch(Exception ee){}
			
		}
		//System.out.println(rtdoc.asXML());
		return rtdoc;
	}
	public static boolean validate(Document doc){
		
		return true;
	}
	
	private static void fkTableProcess(Document doc){
		if(null!=doc){
			
			List cols= doc.selectNodes("/DATAPACKET/METADATA/TABLE/FIELD[@ISFK='1']");
			if(null!=cols){
				Element col=null;
				Iterator it=cols.iterator();
				while(it.hasNext()){
					col=(Element)it.next();
					List rows=doc.selectNodes("/DATAPACKET/TABLEDATA/ROWDATA/ROW");
					if(null!=rows){
						Iterator rit=rows.iterator();
						Element rElt=null;
						while(rit.hasNext()){
							rElt=(Element)rit.next();
							String val=rElt.valueOf("@"+col.valueOf("@NAME"));
							if(null==val || "".equals(val))continue;
							String name=getNameByCode(col.valueOf("@FKTBL"),val);
							rElt.addAttribute(col.valueOf("@NAME")+"_NAME", name);
						}
					}
				}
			}
		}
		
	}
	
	private static String getNameByCode(String tblName,String code){
		String name=null;
		if(null!=tblName){
			Document doc=AppCache.getInst().getTableInfo(tblName);
			//Dom4jUtil.writeDocToFile(doc, "GBK", "c:/cach.xml");
			if(null!=doc){
				Element elt=(Element)doc.selectSingleNode("/DATAPACKET/TABLEDATA/ROWDATA/ROW[@ID='"+code+"']");
				if(null!=elt){
					name=elt.valueOf("@NAME");
				}
			}
		}
		return name;
	}
	//多对多关系表内容的删除
	private static Document relationDel(Document doc){
		Document rtdoc=DocumentHelper.createDocument();
		Element rtroot=rtdoc.addElement("DATAPACKET");
		Connection con=null;
		try{
			if(null==doc){
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "请求数据错误！");
			}else{
				Element root=doc.getRootElement();
				String tblCode=root.valueOf("@TABLE");
				List elts=doc.selectNodes("/DATAPACKET/COL");
				if(null!=elts){
					Iterator it=elts.iterator();
					StringBuffer bf=new StringBuffer(100);
					bf.append("delete from ").append(tblCode).append(" where ");
					while(it.hasNext()){
						Element elt=(Element)it.next();
						
						bf.append(elt.valueOf("@NAME")).append("=").append(elt.valueOf("@VALUE")).append(" and ");
					}
					String sql=bf.toString().substring(0, bf.length()-4);
					//System.out.println("sql==="+sql);
					con= DBConnectionManager.getInstance().getConn();
					boolean flg=OADBUtil.runUpdateSql(sql, null, con);
				}
			}
		}catch(Exception e){
			rtroot.addAttribute("code", "error");
			rtroot.addAttribute("message", "删除失败！");
		}finally{
			try{con.close();}catch(Exception ee){}
			
		}
		
		return rtdoc;
	}
//	多对多关系表内容的添加
	private static Document relationAdd(Document doc){
		
		Document rtdoc=DocumentHelper.createDocument();
		Element rtroot=rtdoc.addElement("DATAPACKET");
		Connection con=null;
		try{
			if(null==doc){
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "请求数据错误！");
			}else{
				Element root=doc.getRootElement();
				String tblCode=root.valueOf("@TABLE");
				List elts=doc.selectNodes("/DATAPACKET/COL");
				if(null!=elts){
					Iterator it=elts.iterator();
					StringBuffer sqlbf=new StringBuffer();
					StringBuffer colbf=new StringBuffer(100);
					//colbf.append("insert into ").append(tblCode);
					StringBuffer valbf=new StringBuffer(100);
					while(it.hasNext()){
						Element elt=(Element)it.next();
						colbf.append(elt.valueOf("@NAME")).append(" ,");
						valbf.append(elt.valueOf("@VALUE")).append(" ,");
						//colbf.append(elt.valueOf("@NAME")).append("=").append(elt.valueOf("@VALUE")).append(" and ");
					}
					sqlbf.append("insert into ").append(tblCode).append("(").append(colbf.substring(0, colbf.length()-1))
					.append(") values(").append(valbf.substring(0, valbf.length()-1))
					.append(")");
					
					
					//System.out.println("sql==="+sql);
					con= DBConnectionManager.getInstance().getConn();
					boolean flg=OADBUtil.runUpdateSql(sqlbf.toString(), null, con);
				}
			}
		}catch(Exception e){
			rtroot.addAttribute("code", "error");
			rtroot.addAttribute("message", "添加失败！");
		}finally{
			try{con.close();}catch(Exception ee){}
			
		}
		
		return rtdoc;
	}
	/**
	 * @desc:对象的详细信息请求处理。
	 * @param doc
	 * @return :
	 * @auther : Administrator
	 * @date : Jul 22, 2009
	 */
	private static Document detailFilter(Document doc){
		Document rtdoc=null;
		String sql=null;
		try{
			
			Element root= doc.getRootElement();
			String tbName=root.valueOf("@TABLE");
			String id=root.valueOf("@ID");
			String where =root.valueOf("@WHERE");
			sql=createDetailSql(tbName,id,where);
			String tblCode=TableInfoService.getInst().getTableCode(tbName);
			rtdoc=ResultSetToCDS.BuildCDSWithMate(tbName, sql);
			Element rtroot=rtdoc.getRootElement();
			rtroot.addAttribute("TABLECODE", tblCode);
			rtroot.addAttribute("TABLE", tbName);
			//找出参考其他表的列。
//		从缓存中找出对应的名称。属性名称加个后缀_NAME
			fkTableProcess(rtdoc);
			
			IDetailDocRender detailRender= DetailRenderFactory.getInst().getRender(tbName);
			if(null!=detailRender)
				detailRender.render(rtdoc);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("sql="+sql);
		}
		return rtdoc;
	}
	
	
	
	/**
	 * @desc: 列表查询请求处理
	 * @param doc
	 * @return :
	 * @auther : Administrator
	 * @date : Apr 1, 2009
	 */
	private static Document ListFilter(Document doc){
		Element root= doc.getRootElement();
		String tbName=root.valueOf("@TABLE");
//	Document doc=Tab55leMateService.getInst().getTableMate(tbName);
		String sql=creatSelSql(root);
		//根据
		Document rtdoc=ResultSetToCDS.BuildCDSWithMate(tbName, sql);
	//设置用户设置显示的列
		String displayCol=TableInfoService.getInst().getListCols(tbName);
		if(null!=displayCol){
			String[] cols=displayCol.split(",");
			for(int i=0;i<cols.length;i++){
				String col=cols[i];
				Element elt=(Element)rtdoc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='"+col+"']");
				if(null!=elt)
					elt.setAttributeValue("DP", "1");
			}
		}else{
			System.out.println("没有设置显示列");
		}
		
//		render the list doc
		IListDocRender listRender= ListRenderFactory.getInst().getRender(tbName);
		if(null!=listRender)
			listRender.render(rtdoc);
		return rtdoc;
	}
	/**
	 * @desc:更新处理
	 * @param doc
	 * @return :
	 * @auther : Administrator
	 * @date : Apr 1, 2009
	 */
	private static Document editFilter(Document doc){
	
		Connection	con=null;
		String idStr="-1";
		Document rtdoc=null;
		Element rtroot=null;
		try {
			
				rtdoc=DocumentHelper.createDocument();
				rtroot=rtdoc.addElement("DATAPACKET");
				
				String upsql=createUpdateSQL(doc);
				con= DBConnectionManager.getInstance().getConn();
				
				boolean flg=OADBUtil.runUpdateSql(upsql, null, con);
			
				
				
		}catch(Exception e){
			rtroot.addAttribute("ID", idStr);
			rtroot.addAttribute("code", "error");
			rtroot.addAttribute("message", "系统错误！");
		}finally{
			try{con.close();}catch(Exception ee){}
		}
		
		return rtdoc;
	}
	private static String createUpdateSQL(Document doc)throws Exception{
		
		Element tblElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE");
		String tblName=tblElt.valueOf("@NAME");
		String tblCode=TableInfoService.getInst().getTableCode(tblName);
		Element rowElt=(Element)doc.selectSingleNode("/DATAPACKET/TABLEDATA/ROWDATA/ROW");
		Element keyElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/KEYS/KEY");
		String keyName=keyElt.valueOf("@NAME");
		StringBuffer sqlbf=new StringBuffer(200);
		sqlbf.append("update ").append(tblCode).append(" set ");
		Iterator it= rowElt.attributeIterator();
		Attribute attr=null;
		String name=null, val=null;
		StringBuffer cols=new StringBuffer(100);
		List fkls=doc.selectNodes("/DATAPACKET/METADATA/TABLE/FIELD[@ISFK='1']");
		
		while(it.hasNext()){
			attr=(Attribute)it.next();
			name=attr.getName();
			boolean flg=false;
			Iterator fkit=fkls.iterator();
			while(fkit.hasNext()){
				Element elt=(Element)fkit.next();
				if((elt.valueOf("@NAME")+"_NAME").equals(name)){
					flg=true;
				}
			}
			if(flg)continue;
			val=attr.getValue();
			if(name.equalsIgnoreCase(keyName))continue;
			Element meta=getMeta(name,doc);
			String type=meta.valueOf("@TYPE");
			if("int".equalsIgnoreCase(type) || "double".equalsIgnoreCase(type) || "float".equalsIgnoreCase(type)){
				cols.append(name).append("=").append(val).append(",");
			}else if("file".equalsIgnoreCase(type) || "image".equalsIgnoreCase(type) || "date".equalsIgnoreCase(type) || "varchar".equalsIgnoreCase(type)){
				cols.append(name).append("='").append(val).append("',");
			}
		}
		Element upElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='UPDATE_TIME']");
		if(null==upElt){
			sqlbf.append(cols.toString().subSequence(0, cols.length()-1));
			
		}else{
			
			cols.append(" UPDATE_TIME='").append(DateTimeUtil.getDateTime(DateTimeUtil.DEFAULT_OA_PAGE_FORMAT)).append("'");
			sqlbf.append(cols.toString());
		}
		sqlbf.append(" where ").append(keyName).append("=").append(rowElt.valueOf("@"+keyName));
		
		return sqlbf.toString();
	}
	private static Element getMeta(String name,Document doc){
		Element elt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='"+name+"']");
		return elt;
	}
		
	/**
	 * @desc:添加新对象请求处理。
	 * @param doc
	 * @return :
	 * @auther : Administrator
	 * @date : Jul 22, 2009
	 */
	private static Document addFilter(Document doc){
		Element root=doc.getRootElement();
		Element tblElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE");
		//Element root=doc.getRootElement();
		String tblName=tblElt.valueOf("@NAME");
		String tblCode=TableInfoService.getInst().getTableCode(tblName);
		Document rtDoc=DocumentHelper.createDocument();
		addIcon(doc);
		String sql=createAddSql(doc);
		
		Connection	con=null;
		String idStr="-1";
		try {
			con= DBConnectionManager.getInstance().getConn();
			boolean flg=OADBUtil.runUpdateSql(sql, null, con);
			
			
				Element keyElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/KEYS/KEY");
				String keyName=keyElt.valueOf("@NAME");
				String idsql="select max("+keyName+") id from "+tblCode;
				ResultSet rs=OADBUtil.runQuerySql(idsql, con);
				
				if(rs.next()){
					idStr=rs.getString("id");
				}
			
		}catch (Exception e) {
		
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(Exception e){
				
			}
		}
		Element rtroot=(Element)rtDoc.addElement("DATAPACKET");
		rtroot.addAttribute("ID", idStr);
		root.addAttribute("ID", idStr);
		IAddDocRender addRender= AddRenderFactory.getInst().getRender(tblName);
		if(null!=addRender)
			addRender.render(doc);
		return rtDoc;
	}
	
	private static void addIcon(Document doc){
		Element companyElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='COMPANY']");
		Element IconElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='ICON']");
		Element tblElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE");
		String tblName=tblElt.valueOf("@NAME");
		Element row=(Element)doc.selectSingleNode("/DATAPACKET/TABLEDATA/ROWDATA/ROW");
		if(null!=companyElt && null!=IconElt){
			String setIcon=row.valueOf("@ICON");
			if(null==setIcon || "".equals(setIcon) ){
				String companyId=row.valueOf("@COMPANY");
				if(null!=companyId){
					String defIcon=getDefaultIcon(tblName,companyId);
					row.setAttributeValue("ICON", defIcon);
				}
			}
			
		}
	}
	private static String getDefaultIcon(String obj,String companyId){
		Document doc=AppCache.getInst().getTableInfo("记录图标");
		if(null!=doc){
			List rows=doc.selectNodes("/DATAPACKET/TABLEDATA/ROWDATA/ROW[@OBJECT='"+obj+"']");
			Iterator it=rows.iterator();
			while(it.hasNext()){
				Element elt=(Element)it.next();
				if(companyId.equals(elt.valueOf("@COMPANY"))){
					return elt.valueOf("@ICON");
				}
			}
		}
		return null;
	}
	private static String createAddSql(Document doc){
		List metas=doc.selectNodes("/DATAPACKET/METADATA/TABLE/FIELD");
		Element keyElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/KEYS/KEY");
		Element row=(Element)doc.selectSingleNode("/DATAPACKET/TABLEDATA/ROWDATA/ROW");
		Element root=doc.getRootElement();
		Element tblElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE");
		String tblName=tblElt.valueOf("@NAME");
		String tblCode=TableInfoService.getInst().getTableCode(tblName);
		String keyName=keyElt.valueOf("@NAME");
		Iterator it=metas.iterator();
		Element elt=null;
		StringBuffer sqlbf=new StringBuffer(200);
		StringBuffer colsbf=new StringBuffer(200);
		StringBuffer valsbf=new StringBuffer(200);
		sqlbf.append("insert into ").append(tblCode);
		while(it.hasNext()){
			elt=(Element)it.next();
			String colName=elt.valueOf("@NAME");
			String val=row.valueOf("@"+colName);
			String type=elt.valueOf("@TYPE");
			if(colName.equals(keyName))continue;
			if(TableMeta.DB_TYPE_DOUBLE.equalsIgnoreCase(type) || TableMeta.DB_TYPE_FLOAT.equalsIgnoreCase(type) || TableMeta.DB_TYPE_INT.equalsIgnoreCase(type)){
				if(null==val || "".equals(val))continue;
				valsbf.append(val).append(",");
			}else if(TableMeta.DB_TYPE_VARCHAR.equalsIgnoreCase(type)){
				if("UPDATE_TIME".equalsIgnoreCase(colName) || "CREATE_TIME".equalsIgnoreCase(colName)){
					valsbf.append("'").append(DateTimeUtil.getDateTime(DateTimeUtil.DEFAULT_OA_PAGE_FORMAT)).append("',");
				}else{
					valsbf.append("'").append(val).append("',");
				}
				
			}else if(TableMeta.DB_TYPE_FILE.equalsIgnoreCase(type) || TableMeta.DB_TYPE_IMAGE.equalsIgnoreCase(type)){
				valsbf.append("'").append(val).append("',");
			}else if(TableMeta.DB_TYPE_DATE.equalsIgnoreCase(type)){
				valsbf.append("'").append(val).append("',");
			}
			colsbf.append(colName).append(",");
		}
		String colsStr=colsbf.toString().substring(0,colsbf.toString().length()-1);
		String valsStr=valsbf.toString().substring(0,valsbf.toString().length()-1);
		sqlbf.append("(").append(colsStr).append(") values(").append(valsStr).append(")"); 
		return sqlbf.toString();
	}
	
/**
 * @desc:删除对象请求处理。
 * @param doc
 * @return :
 * @auther : Administrator
 * @date : Jul 22, 2009
 */
	private static Document delFilter(Document doc){
		
		
		Connection	con=null;
		String idStr="-1";
		Element root=null;
		try {
			String delSql=createDelSql(doc);
			root=doc.getRootElement();
			con= DBConnectionManager.getInstance().getConn();
			String tbName=root.valueOf("@TABLE");
			//COMNAME
			String comName=root.valueOf("@COMNAME");
			
//			在此添加删除使用的图片或文件
//			.查找为图片类型的列，删除。
			List imgElts=doc.selectNodes("/DATAPACKET/METADATA/TABLE/FIELD[@TYPE='IMAGE']");
//			2.查找为文件类型的列，删除
			List fileElts=doc.selectNodes("/DATAPACKET/METADATA/TABLE/FIELD[@TYPE='FILE']");
			
			IDelDocRender delRender= DelRenderFactory.getInst().getRender(tbName);
			if(null!=delRender)
				delRender.render(doc);
			boolean flg=OADBUtil.runUpdateSql(delSql, null, con);
		}catch(Exception e){
			root.setAttributeValue("ID", idStr);
		}finally{
			try{con.close();}catch(Exception ee){}
		}
		return doc;
	}
 
 
 

 


 private static String createDelSql(Document doc){
	 String sql=null;
	 Element root=doc.getRootElement();
	 String tblName=root.valueOf("@TABLE");
	 String idStr=root.valueOf("@ID");
	 String keyName=root.valueOf("@KEYNAME");
	 String tblCode=TableInfoService.getInst().getTableCode(tblName);
	 sql="delete from "+tblCode +" where "+keyName+" ="+idStr;
	 
	 return sql;
	 
 }

 private static String createDetailSql(String tblName,String id,String where){
	 	//if(null==id || "".equalsIgnoreCase(id) || "null".equalsIgnoreCase(id))return null;
		StringBuffer sql=null;
		sql=new StringBuffer(200);
		String tblCode=TableInfoService.getInst().getTableCode(tblName);
		if(null!=tblName && null !=id && !"".equalsIgnoreCase(id) && !"null".equalsIgnoreCase(id)){
			sql.append("SELECT * FROM ").append(tblCode).append(" WHERE ID=").append(id);
		}else if(null!=tblName && null !=where && !"null".equals(where) && !"".equals(where)){
			sql.append("SELECT * FROM ").append(tblCode).append(" WHERE ").append(where);
		}else{
			return null;
		}
		return sql.toString();
	}
	/**
	 * @desc:创建查询语句
	 * @param root
	 * @return :
	 * @auther : Administrator
	 * @date : Apr 1, 2009
	 */
	private static String creatSelSql(Element root){
		String tblName=root.valueOf("@TABLE");
		String tblCode=TableInfoService.getInst().getTableCode(tblName);
		String displayCol=TableInfoService.getInst().getListCols(tblName);
		String condition=root.valueOf("@CONDITION");
		
		Element condsElt=(Element)root.selectSingleNode("CONDITIONS");
		List eltLs=null;
		if(null!=condsElt){
			eltLs=condsElt.selectNodes("CONDITION");
		}
		StringBuffer bf=new StringBuffer();
		bf.append("SELECT ").append(displayCol).append(" FROM ").append(tblCode).append(" ");
		if(null!=condition){
			bf.append(" where ").append(condition);
			
		}
		//如果有条件，添加条件
		if(null!=eltLs && eltLs.size()>0){
			bf.append(" where ");
			Iterator it=eltLs.iterator();
			Element cElt=null;
			while(it.hasNext()){
				cElt=(Element)it.next();
				String operator=cElt.valueOf("@OPERATOR");
				String column=cElt.valueOf("@COLUMN");
				String colType=TableInfoService.getInst().getColumnType(tblName, column);
				if(null==colType)continue;
				if(null==operator || "".equals(operator)){
					operator=getOperator(tblName,column);
				}
				bf.append(cElt.valueOf("@LOGIC")).append(" ").append(column);
				if("like".equalsIgnoreCase(operator)){
					bf.append(" ").append(operator).append(" '%").append(cElt.valueOf("@VALUE")).append("%'");
				}else if("varchar".equalsIgnoreCase(colType)){
					bf.append(" ").append(operator).append(" '").append(cElt.valueOf("@VALUE")).append("'");
				}else {
					bf.append(" ").append(operator).append(" ").append(cElt.valueOf("@VALUE")).append(" ");
				}
			}
		}
		//如果有排序，添加排序功能
		Element ordersElt=(Element)root.selectSingleNode("ORDERS");
		if(null!=ordersElt){
			List orderLs=ordersElt.selectNodes("ORDER");
			Element oElt=null;
			if(null!=orderLs){
				Iterator oit=orderLs.iterator();
				bf.append(" ORDER BY ");
				boolean isFirst=true;
				while(oit.hasNext()){
					oElt=(Element)oit.next();
					if(isFirst){
						bf.append(oElt.valueOf("@COLUMN"));
						isFirst=false;
					}else{
						bf.append(",").append(oElt.valueOf("@COLUMN"));
					}
					
				}
				
			}
		}
		
		
		return bf.toString();
	}

	/**
	 * @desc: 取到运算符
	 * @return :
	 * @auther : Administrator
	 * @date : Apr 1, 2009
	 */
	private static String getOperator(String tblName,String column){
		String operator=null;
		if(null!=tblName && null!=column){
			String type=TableInfoService.getInst().getColumnType(tblName, column);
			if("varchar".equalsIgnoreCase(type)){
				operator= "LIKE";
			}else if("int".equalsIgnoreCase(type)){
				operator= "=";
			}
		}
		return  operator;
	}
	
	
	
	
	public static void main(String[] args) {
		
		try {
			Document doc=AppCache.getInst().getTableInfo("记录图标");
			System.out.println(doc.asXML());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
