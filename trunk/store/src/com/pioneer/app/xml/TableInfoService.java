package com.pioneer.app.xml;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.FastHashMap;
import org.dom4j.Document;
import org.dom4j.Element;

import com.pioneer.app.db.DBConnectionManager;
import com.pioneer.app.db.OADBUtil;
import com.pioneer.app.vo.Column;

/**
 * @name : 数据表服务 
 * @URL : 
 * @URL_Parameter :  
 * @author Administrator
 * @version : v 1.0
 * @description : 
 */
public class TableInfoService {
//	表名称-表编号的存储
	FastHashMap tables=new FastHashMap();
//	列表中显示列的设置
	FastHashMap listCols=new FastHashMap();
//	详细信息中显示的列定义。
	FastHashMap detailCols=new FastHashMap();
//	表中参考列的说明
	FastHashMap fktbls=new FastHashMap();
	private TableInfoService(){
		init();
		initListCols();
		initDetailCols();
		FKinit();
	}
	
	public void freshFK(){
		this.fktbls.clear();
		FKinit();
	}
	private void FKinit(){
		
		Connection	con=null;
		//user fk
		List userfkls=new ArrayList();
		Column col=new Column();
		col.setName("COMPANY");
		col.setFkTable("分公司");
		col.setFkTableStruct("tree");
		userfkls.add(col);
		this.fktbls.put("用户", userfkls);
		///////////obj////////////
		List objls=new ArrayList();
		Column type=new Column();
		type.setName("OBJTYPE");
		type.setFkTable("物理对象类型");
		type.setFkTableStruct("tree");
		objls.add(type);
		this.fktbls.put("表注册", objls);
		try {
//			String sql="select * from s_fk_define order by table_name";
			String sql="select sd.*,st.struct from s_fk_define sd,s_table st where sd.fk_table=st.name  order by table_name";
			con= DBConnectionManager.getInstance().getConn();
			ResultSet rs=OADBUtil.runQuerySql(sql, con);
			String tblName=null;
			
			while(rs.next()){
				tblName=rs.getString("TABLE_NAME");
				List arr=(List)this.fktbls.get(tblName);
				if(null==arr){
					arr=new ArrayList();
					this.fktbls.put(tblName, arr);
				}
				Column tcol=new Column();
				tcol.setName(rs.getString("COLUM"));
				//col.setType("INT");
				tcol.setFkTable(rs.getString("FK_TABLE"));
				tcol.setWinHeight(rs.getInt("height"));
				tcol.setWinWidth(rs.getInt("width"));
				tcol.setFkTableStruct(rs.getString("struct"));
				arr.add(tcol);
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{con.close();}catch(Exception ee){}
		}
	}
	public void freshTableInfo(){
		this.tables.clear();
		init();
	}
	private void init(){
		//////////////////系统定义表/////////////////
		tables.put("表注册", "s_table");
		tables.put("列表定义", "s_list_def");
		tables.put("参考列定义", "s_fk_define");
		tables.put("字段类型定义", "s_colum_type");
		tables.put("字段单位", "s_column_unit");
		tables.put("字段取值", "s_column_value");
////////////系统表/////////////
		tables.put("用户", "s_user");
		tables.put("角色", "s_role");
		tables.put("角色用户", "s_userrole");
		tables.put("功能", "s_process");
		tables.put("角色功能", "s_roleprocess");
		
		
		Connection	con=null;
		try {
			String sql="select * from s_table";
			con= DBConnectionManager.getInstance().getConn();
			ResultSet rs=OADBUtil.runQuerySql(sql, con);
			while(rs.next()){
				tables.put(rs.getString("NAME"), rs.getString("CODE"));
			}
		}catch(Exception e){
			
		}finally{
			try{con.close();}catch(Exception ee){}
		}
		
	}
	private String getTableNameByTableCode(String tblCode){
		Iterator it=this.tables.keySet().iterator();
		String tblName=null;
		while(it.hasNext()){
			String tName=String.valueOf(it.next());
			String code=(String)this.tables.get(tName);
			if(code.equalsIgnoreCase(tblCode)){
				tblName=tName;
				break;
			}
		}
		return tblName;
	}
	private List tableInfo=null;
	public List getTables(){
		
		if(null==this.tableInfo){
			this.tableInfo=new ArrayList();
			Connection	con=null;
			try {
				String sql="select * from s_table";
				con= DBConnectionManager.getInstance().getConn();
				ResultSet rs=OADBUtil.runQuerySql(sql, con);
				while(rs.next()){
					Map mp=new HashMap();
					mp.put("id", rs.getString("ID"));
					mp.put("name", rs.getString("NAME"));
					mp.put("code", rs.getString("CODE"));
					mp.put("objtype", rs.getString("OBJTYPE"));
					this.tableInfo.add(mp);
				}
			}catch(Exception e){
				
			}finally{
				try{con.close();}catch(Exception ee){}
			}
		}
		return this.tableInfo;
	}
	public void freshListCols(){
		this.listCols.clear();
		initListCols();
	}
	/**
	 * @desc: :列表显示列的定义/树形结构暂时不用注册。
	 * @auther : Administrator
	 * @date : Apr 3, 2009
	 */
	private void initListCols(){
		//////////////////系统定义表/////////////////
		listCols.put("表注册", "ID,CODE,NAME");
		listCols.put("列表定义", "ID,COLS,REMARK");
		listCols.put("参考列定义", "ID,COLUM,FK_TABLE");
//		字段取值
		listCols.put("字段取值", "ID,VALUE_ONE");
		////////////////////系统表/////////////////////
		listCols.put("用户", "ID,NAME,MOBILE");
		listCols.put("角色", "ID,NAME,REMARK");
		listCols.put("功能", "ID,NAME,REMARK");
		//取出应用表的定义
		Connection	con=null;
		try {
			String sql="select * from s_list_def";
			con= DBConnectionManager.getInstance().getConn();
			ResultSet rs=OADBUtil.runQuerySql(sql, con);
			while(rs.next()){
				String tblName=rs.getString("TABLE_NAME");
				String cols=(String)listCols.get(tblName);
				if(null==cols){
					cols="ID";
				}
				cols+=","+rs.getString("COLS");
				listCols.put(tblName,cols);
			}
		}catch(Exception e){
			
		}finally{
			try{con.close();}catch(Exception ee){}
		}
	}
	
	/**
	 * @desc: :记录详细休息显示列的定义，
	 * @auther : pioneer
	 * 
	 * @date : Apr 3, 2009
	 */
	private void initDetailCols(){
		//this.detailCols.put("街道", "ID,NAME,REMARK");
	}
	
	/**
	 * @desc:去列表中显示的列名
	 * @param tblName
	 * @return :
	 * @auther : Administrator
	 * @date : Apr 1, 2009
	 */
	public String getListCols(String tblName){
		return (String)listCols.get(tblName);
	}
	
	public List getFKTables(String tblName){
		return (List)this.fktbls.get(tblName);
	}
	/**
	 * @desc:详细页面中显示的字段
	 * @param tblName
	 * @return :
	 * @auther : Administrator
	 * @date : Apr 3, 2009
	 */
	public String getDetailCols(String tblName){
		return (String)detailCols.get(tblName);
	}
	public String getColumnType(String tblName,String column){
		String colType=null;
		if(null!=tblName && null!=column){
			Document doc=TableMateService.getInst().getTableMate(tblName);
			if(null!=doc){
				Element elt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/TABLE/FIELD[@NAME='"+column+"']");
				if(null!=elt){
					colType=elt.valueOf("@TYPE");
				}
			}
		}
		return colType;
	}
	
	/**
	 * @desc:取到表的编码
	 * @param tblName
	 * @return :
	 * @auther : Administrator
	 * @date : Apr 3, 2009
	 */
	public String getTableCode(String tblName){
		return (String)this.tables.get(tblName);
	}
	
	public static TableInfoService getInst(){
		return _inst;
	}

	private static TableInfoService _inst=new TableInfoService();
	
	public static void main(String[] args) {
		String type=TableInfoService.getInst().getColumnType("街道", "NAME");
		System.out.println("type="+type);
	}
	
}
