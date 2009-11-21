package com.pioneer.app.xml;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.frame.util.security.ApplicationValidate;
import com.pioneer.app.db.DBConnectionManager;
import com.pioneer.app.db.OADBUtil;

/**
 * @name :  xml格式文件的业务处理
 * @URL : 
 * @URL_Parameter :  
 * @author pioneer
 * @version : v 1.0
 * @description : 
 */
public class SystemDefService {
	
	
	public  final static String GET_COL_TYPE_ADD="col_type_add";
	public  final static String GET_COL_TYPE_DEL="col_type_del";
	public  final static String GET_COL_TYPE_EDIT="col_type_edit";
	public  final static String GET_TABLE_COL_EDIT="table_col_edit";
	public  final static String GET_TABLE_COL_DEL="table_col_del";
	public  final static String GET_TABLE_COL_ADD="table_col_add";
	public  final static String GET_TABLE_CREATE="table_create";
	
	public  final static String GET_UNIT_ADD="unit_add";
	public  final static String GET_UNIT_EDIT="unit_edit";
	public  final static String GET_UNIT_DEL="unit_del";
	public  final static String GET_TABLE_KF_FRESH="table_fk_fresh";
	
	//
	
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
			if(SystemDefService.GET_COL_TYPE_ADD.equalsIgnoreCase(action)){
				rtdoc=ColTypeAdd(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
			}else if(SystemDefService.GET_COL_TYPE_DEL.equalsIgnoreCase(action)){
				rtdoc=ColTypeDel(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
				//TableInfoService.getInst().f
			}else if(SystemDefService.GET_COL_TYPE_EDIT.equalsIgnoreCase(action)){
				rtdoc=ColTypeEdit(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
			}else if(SystemDefService.GET_TABLE_COL_EDIT.equalsIgnoreCase(action)){
				rtdoc=tableColEdit(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
			}else if(SystemDefService.GET_TABLE_COL_DEL.equalsIgnoreCase(action)){
				rtdoc=tableColDel(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
			}else if(SystemDefService.GET_TABLE_COL_ADD.equalsIgnoreCase(action)){
				rtdoc=tableColAdd(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
			}else if(SystemDefService.GET_TABLE_CREATE.equalsIgnoreCase(action)){
				rtdoc=tableCreate(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
			}else if(SystemDefService.GET_UNIT_ADD.equalsIgnoreCase(action)){
				rtdoc=tableUnitAdd(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
			}else if(SystemDefService.GET_UNIT_DEL.equalsIgnoreCase(action)){
				rtdoc=tableUnitDel(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
			}else if(SystemDefService.GET_UNIT_EDIT.equalsIgnoreCase(action)){
				rtdoc=tableUnitEdit(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
			}else if(SystemDefService.GET_TABLE_KF_FRESH.equalsIgnoreCase(action)){
				rtdoc=tablefkFresh(doc);
				TableMateService.getInst().clearTableMate(root.valueOf("@TABLE"));
			}else{
				
			}//
		}
		return rtdoc;
	}
private static Document tablefkFresh(Document doc){
		
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String tblName=root.valueOf("@TABLE");
			
			//ALTER TABLE test CHANGE AK AK varchar(30) Comment 'ak47'
			StringBuffer bf=new StringBuffer(200);
			bf.append("select KPI from s_table where KPI=1 and  NAME in (select fk_table from s_fk_define where table_name='").append(tblName)
			.append("')");
			
			
			Connection	con=null;
			String idStr="-1";
			
			Element rtroot=null;
			try {
				
					rtdoc=DocumentHelper.createDocument();
					rtroot=rtdoc.addElement("DATAPACKET");
					
					
					con= DBConnectionManager.getInstance().getConn();
					
					ResultSet rs=OADBUtil.runQuerySql(bf.toString(), con);
					StringBuffer ubf=new StringBuffer(120);
					ubf.append("update s_table set KPI=");
					if(rs.next()){
						ubf.append("1 ");
					}else{
						ubf.append("0 ");
					}
					ubf.append(" where name='").append(tblName).append("'");
					OADBUtil.runUpdateSql(ubf.toString(), null, con);
			}catch(Exception e){
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
private static Document tableUnitEdit(Document doc){
		
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String tblName=root.valueOf("@TABLE");
			String colName=root.valueOf("@COLUMN");
			String unit=root.valueOf("@UNIT");
			//ALTER TABLE test CHANGE AK AK varchar(30) Comment 'ak47'
			StringBuffer bf=new StringBuffer(200);
			bf.append("update s_column_unit set UNIT='").append(unit)
			.append("' where TABLE_NAME='")
			.append(tblName).append("' and COLUMN_NAME='")
			.append(colName).append("'");
			
			Connection	con=null;
			String idStr="-1";
			
			Element rtroot=null;
			try {
				
					rtdoc=DocumentHelper.createDocument();
					rtroot=rtdoc.addElement("DATAPACKET");
					
					
					con= DBConnectionManager.getInstance().getConn();
					
					boolean flg=OADBUtil.runUpdateSql(bf.toString(), null, con);
					
			}catch(Exception e){
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
private static Document tableUnitDel(Document doc){
		
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String tblName=root.valueOf("@TABLE");
			String colName=root.valueOf("@COLUMN");
			String unit=root.valueOf("@UNIT");
			//ALTER TABLE test CHANGE AK AK varchar(30) Comment 'ak47'

			StringBuffer bf=new StringBuffer(200);
			bf.append("delete from s_column_unit where TABLE_NAME='")
			.append(tblName).append("' and COLUMN_NAME='")
			.append(colName).append("'");
			
			Connection	con=null;
			String idStr="-1";
			
			Element rtroot=null;
			try {
				
					rtdoc=DocumentHelper.createDocument();
					rtroot=rtdoc.addElement("DATAPACKET");
					
					
					con= DBConnectionManager.getInstance().getConn();
					
					boolean flg=OADBUtil.runUpdateSql(bf.toString(), null, con);
					
			}catch(Exception e){
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
private static Document tableUnitAdd(Document doc){
		
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String tblName=root.valueOf("@TABLE");
			String colName=root.valueOf("@COLUMN");
			String unit=root.valueOf("@UNIT");
			//ALTER TABLE test CHANGE AK AK varchar(30) Comment 'ak47'

			StringBuffer bf=new StringBuffer(200);
			bf.append("insert into s_column_unit (TABLE_NAME, COLUMN_NAME, UNIT) values('")
			.append(tblName).append("','")
			.append(colName).append("','")
			.append(unit).append("')");
			
			Connection	con=null;
			String idStr="-1";
			
			Element rtroot=null;
			try {
				
					rtdoc=DocumentHelper.createDocument();
					rtroot=rtdoc.addElement("DATAPACKET");
					
					
					con= DBConnectionManager.getInstance().getConn();
					
					boolean flg=OADBUtil.runUpdateSql(bf.toString(), null, con);
					
			}catch(Exception e){
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
	private static Document tableCreate(Document doc){
		/*xml.@NAME=tblName.text;
		xml.@CODE=tblCode.text;
		xml.@XY=physical.selected;*/
			Document rtdoc=null;
			if(null!=doc){
				Element root=doc.getRootElement();
				String tblName=root.valueOf("@NAME");
				String tblCode=root.valueOf("@CODE");
				String remark=root.valueOf("@REMARK");
				String xyCh=root.valueOf("@XY");
				String appCode=root.valueOf("@APPCODE");
				String type=root.valueOf("@TYPE");
				String kpi=root.valueOf("@KPI");
				String struct=root.valueOf("@STRUCT");
				String frequency=root.valueOf("@FREQUENCE");
				//CREATE TABLE `t_good` (
					//	  `ID` int(11) NOT NULL auto_increment COMMENT '编号',
				
				StringBuffer cbf=new StringBuffer(200);
				cbf.append("CREATE TABLE ").append(tblCode).append(" ( ")
				.append("ID int(11) NOT NULL auto_increment primary key COMMENT '编号'");
				if("true".equalsIgnoreCase(xyCh)){
					
					cbf.append(" ,X float Comment '经度'")
					.append(" ,Y float Comment '纬度'");
					
				}
				if("true".equalsIgnoreCase(kpi) && "基础数据".equals(type)){
					
					cbf.append(" ,SCORE float  Comment '考核分'");
					
				}
				if("tree".equalsIgnoreCase(struct)){
					cbf.append(" ,PID int(11)  Comment '父结点'");
				}
				
				cbf.append(")");
				
				StringBuffer dybf=new StringBuffer(200);
				dybf.append("insert into s_table  (CODE, NAME, REMARK, APPCODE,TYPE, PHYSICAL, KPI,STRUCT,FREQUENCY) values('")
				.append(tblCode).append("','")
				.append(tblName).append("','")
				.append(remark).append("','")
				.append(appCode).append("','")
				.append(type).append("',")
				.append(xyCh).append(",")
				.append(kpi).append(",'")
				.append(struct).append("','")
				.append(frequency).append("')");
				;
				
				Connection	con=null;
				String idStr="-1";
				
				Element rtroot=null;
				try {
					
						rtdoc=DocumentHelper.createDocument();
						rtroot=rtdoc.addElement("DATAPACKET");
						
						
						con= DBConnectionManager.getInstance().getConn();
						
						boolean flg=OADBUtil.runUpdateSql(cbf.toString(), null, con);
					
							OADBUtil.runUpdateSql(dybf.toString(), null, con);
						TableInfoService.getInst().freshTableInfo();
				}catch(SQLException e){//SQLException
					System.out.println("sql="+cbf.toString());
					System.out.println("sql="+dybf.toString());
					rtroot.addAttribute("ID", idStr);
					rtroot.addAttribute("code", "error");
					rtroot.addAttribute("message", "创建表错误::"+e.getMessage());
				}catch(Exception e){//
					System.out.println("sql="+cbf.toString());
					System.out.println("sql="+dybf.toString());
					rtroot.addAttribute("ID", idStr);
					rtroot.addAttribute("code", "error");
					rtroot.addAttribute("message", "系统错误！");
				}finally{
					try{con.close();}catch(Exception ee){}
				}


			}
			return rtdoc;
		}
private static Document tableColAdd(Document doc){
	/*xml.@TABLE=this.currentTable;
	xml.@COLUMN=colName;
	xml.@REMARK=remark;
	xml.@TYPE=type;
	xml.@SIZE=size;*/
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String tblName=root.valueOf("@TABLE");
			String col=root.valueOf("@COLUMN");
			col=col.toUpperCase();
			String remark=root.valueOf("@REMARK");
			String type=root.valueOf("@TYPE");
			String size=root.valueOf("@SIZE");
			
			String tblCode=TableInfoService.getInst().getTableCode(tblName);
			String newType=null;
			if("image".equals(type) || "file".equals(type)){
				newType="varchar";
			}else if("password".equals(type)){
				newType="varchar";

			}else if("date".equals(type)){
				newType="int";
			}else{
				newType=type;
			}
			
			//ALTER TABLE t_spending ADD AK111 varchar(30) Comment 'ak111'
			StringBuffer bf=new StringBuffer(200);
			bf.append("ALTER TABLE ").append(tblCode)
			.append(" ADD ").append(col).append(" ")
			.append(newType).append("(").append(size).append(") ")
			.append("Comment '").append(remark).append("'");
			
			StringBuffer typbf=new StringBuffer(200);
			typbf.append("insert into s_colum_type (TABLE_NAME, COLUM, TYPE) values('")
			.append(tblName).append("','").append(col).append("','").append(type).append("')");
			
			Connection	con=null;
			String idStr="-1";
			
			Element rtroot=null;
			try {
				
					rtdoc=DocumentHelper.createDocument();
					rtroot=rtdoc.addElement("DATAPACKET");
					
					
					con= DBConnectionManager.getInstance().getConn();
					
					boolean flg=OADBUtil.runUpdateSql(bf.toString(), null, con);
				
					if("date".equals(type) || "image".equals(type)  || "file".equals(type) || "password".equals(type)){
						OADBUtil.runUpdateSql(typbf.toString(), null, con);
					}
					
				if("icon".equalsIgnoreCase(col)){
					//加入参考表中
					StringBuffer sqlbf=new StringBuffer(200);
					sqlbf.append("insert into s_fk_define (TABLE_NAME, COLUM, FK_TABLE) values('")
					.append(tblName).append("','").append("ICON").append("','").append("标记").append("')");
					
					OADBUtil.runUpdateSql(sqlbf.toString(), null, con);
					
					//加入显示列中
					StringBuffer listsql=new StringBuffer();
					listsql.append("insert into s_list_def (TABLE_NAME, COLS, REMARK) values('")
					.append(tblName).append("','").append("ICON").append("','").append("标记").append("')");
					OADBUtil.runUpdateSql(listsql.toString(), null, con);
				}else if("AREA".equalsIgnoreCase(col)){
					//加入参考表中
					StringBuffer sqlbf=new StringBuffer(200);
					sqlbf.append("insert into s_fk_define (TABLE_NAME, COLUM, FK_TABLE) values('")
					.append(tblName).append("','").append("AREA").append("','").append("区域").append("')");
					
					OADBUtil.runUpdateSql(sqlbf.toString(), null, con);
				}else if("COMPANY".equalsIgnoreCase(col)){
					//加入参考表中
					StringBuffer sqlbf=new StringBuffer(200);
					sqlbf.append("insert into s_fk_define (TABLE_NAME, COLUM, FK_TABLE) values('")
					.append(tblName).append("','").append("COMPANY").append("','").append("对比集团").append("')");
					
					OADBUtil.runUpdateSql(sqlbf.toString(), null, con);
				}
			}catch(SQLException e){//SQLException
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "添加的列重名::"+e.getMessage());
			}catch(Exception e){//
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
			/*String tblName=root.valueOf("@TABLE");
			String col=root.valueOf("@COLUMN");
			String remark=root.valueOf("@REMARK");
			String type=root.valueOf("@TYPE");
			String size=root.valueOf("@SIZE");*/
			rtroot.addAttribute("TABLE", tblName);
			rtroot.addAttribute("COLUMN", col);
			rtroot.addAttribute("REMARK", remark);
			rtroot.addAttribute("TYPE", type);
			rtroot.addAttribute("SIZE", size);
		}
		return rtdoc;
	}
private static Document tableColDel(Document doc){
		
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String tblName=root.valueOf("@TABLE");
			String col=root.valueOf("@COLUMN");
			//col=col.toUpperCase();
			String tblCode=TableInfoService.getInst().getTableCode(tblName);
			
			
			//ALTER TABLE test DROP AK
			StringBuffer bf=new StringBuffer(200);
			bf.append("ALTER TABLE ").append(tblCode)
			.append(" DROP ").append(col).append(" ");
			
			
			Connection	con=null;
			String idStr="-1";
			
			Element rtroot=null;
			try {
				
					rtdoc=DocumentHelper.createDocument();
					rtroot=rtdoc.addElement("DATAPACKET");
					
					
					con= DBConnectionManager.getInstance().getConn();
					
					boolean flg=OADBUtil.runUpdateSql(bf.toString(), null, con);
				
					
					
			}catch(Exception e){
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
	/*xml.@TABLE=this.currentTable;
	xml.@OLDCOLUMN=obj["NAME"];
	xml.@NEWCOLUMN=colCode.text;
	xml.@TYPE=newType;
	xml.@SIZE=this.size.text;*/
private static Document tableColEdit(Document doc){
		
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String tblName=root.valueOf("@TABLE");
			String oldCol=root.valueOf("@OLDCOLUMN");
			String newCol=root.valueOf("@NEWCOLUMN");
			newCol=newCol.toUpperCase();
			String type=root.valueOf("@TYPE");
			String size=root.valueOf("@SIZE");
			String remark=root.valueOf("@REMARK");
			String newType=null;
			String tblCode=TableInfoService.getInst().getTableCode(tblName);
			/*<mx:Object label="日期" code="date"/>
			<mx:Object label="图片" code="image"/>
			<mx:Object label="文件" code="file"/>*/
			
			if("image".equals(type) || "file".equals(type)){
				newType="varchar";
			}else if("password".equals(type)){
				newType="varchar";

			}else if("date".equals(type)){
				newType="int";
			}else{
				newType=type;
			}
			//ALTER TABLE test CHANGE AK AK varchar(30) Comment 'ak47'

			StringBuffer bf=new StringBuffer(200);
			bf.append("ALTER TABLE ").append(tblCode)
			.append(" CHANGE ").append(oldCol).append(" ")
			.append(newCol.toUpperCase()).append(" ")
			.append(newType).append("(").append(size)
			.append(") Comment '").append(remark).append("'");
			
			
			Connection	con=null;
			String idStr="-1";
			
			Element rtroot=null;
			try {
				
					rtdoc=DocumentHelper.createDocument();
					rtroot=rtdoc.addElement("DATAPACKET");
					
					
					con= DBConnectionManager.getInstance().getConn();
					
					boolean flg=OADBUtil.runUpdateSql(bf.toString(), null, con);
				
					
					
			}catch(Exception e){
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
	/*xml.@TABLE=tblname;
	xml.@COLUMN=colName;
	xml.@TYPE=type;
	xml.@SIZE=size;*/
	private static Document ColTypeAdd(Document doc){
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String tblName=root.valueOf("@TABLE");
			String colName=root.valueOf("@COLUMN");
			String type=root.valueOf("@TYPE");
			
			StringBuffer bf=new StringBuffer(200);
			bf.append("insert into s_colum_type (TABLE_NAME, COLUM, TYPE) values('")
			.append(tblName).append("','").append(colName).append("','").append(type).append("')");
			
			Connection	con=null;
			String idStr="-1";
			
			Element rtroot=null;
			try {
				
					rtdoc=DocumentHelper.createDocument();
					rtroot=rtdoc.addElement("DATAPACKET");
					
					
					con= DBConnectionManager.getInstance().getConn();
					
					boolean flg=OADBUtil.runUpdateSql(bf.toString(), null, con);
				
					
					
			}catch(Exception e){
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
		
	private static Document ColTypeDel(Document doc){
		
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String tblName=root.valueOf("@TABLE");
			String colName=root.valueOf("@COLUMN");
			
			
			StringBuffer bf=new StringBuffer(200);
			bf.append("delete from  s_colum_type where TABLE_NAME='")
			.append(tblName).append("' and COLUM='").append(colName).append("'");
			
			
			Connection	con=null;
			String idStr="-1";
			
			Element rtroot=null;
			try {
				
					rtdoc=DocumentHelper.createDocument();
					rtroot=rtdoc.addElement("DATAPACKET");
					
					
					con= DBConnectionManager.getInstance().getConn();
					
					boolean flg=OADBUtil.runUpdateSql(bf.toString(), null, con);
				
					
					
			}catch(Exception e){
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
	private static Document ColTypeEdit(Document doc){
		
		Document rtdoc=null;
		if(null!=doc){
			Element root=doc.getRootElement();
			String tblName=root.valueOf("@TABLE");
			String colName=root.valueOf("@COLUMN");
			String type=root.valueOf("@TYPE");
			
			StringBuffer bf=new StringBuffer(200);
			bf.append("update  s_colum_type set TYPE='").append(type).append("' where TABLE_NAME='")
			.append(tblName).append("' and COLUM='").append(colName).append("'");
			
			
			Connection	con=null;
			String idStr="-1";
			
			Element rtroot=null;
			try {
				
					rtdoc=DocumentHelper.createDocument();
					rtroot=rtdoc.addElement("DATAPACKET");
					
					
					con= DBConnectionManager.getInstance().getConn();
					
					boolean flg=OADBUtil.runUpdateSql(bf.toString(), null, con);
				
					
					
			}catch(Exception e){
				System.out.println("sql="+bf.toString());
				rtroot.addAttribute("ID", idStr);
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "系统错误！");
			}finally{
				try{con.close();}catch(Exception ee){}
			}
			
		}
		return rtdoc;
	}
}
