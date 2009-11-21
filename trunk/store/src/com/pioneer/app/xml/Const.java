package com.pioneer.app.xml;

public class Const {

	public final static String SC_Text					= "1";
	public final static String SC_Select				= "2";
	public final static String SC_RadioSelect 	= "3";
	public final static String SC_BigText 	= "4"; 

	//Session para name 
	public final static String SA_UserId = "UserId"; //��ǰ��¼�û�ID
	public final static String SA_UserName = "UserName"; //��ǰ��¼�û���
	public final static String SA_OrgName = "OrgName"; //��ǰ��¼�û��������֯���.
	public final static String SA_OrgCode = "OrgCode"; //��ǰ��¼�û�������֯����.
	public final static String SA_workbook_obj="workbookobj";
	public final static String SA_UserRightSet="UserRightSet";
	
	//db type
	public final static String sys_db_type_oracle			="oracle";
	public final static String sys_db_type_mysql			="mysql";
	
	//����Ա�û���
	public final static String sys_admin = "superuser";
	
	public final static String sys_lang="sysLang";//ϵͳ��ʹ�õ�����.
	
	//CDS XML 
	public final static String 	CDS_RowsXPath = "/DATAPACKET/ROWDATA/ROW";

	
	//cache list keys
	public final static String cache_main_paras			="mainparas";
	public final static String cache_all_paras			="allparas";
	
	//reprot status
	public final static String report_status_noreport="0";//û���ϱ������//cltrҪ����
	public final static String report_status_nutread="1";//���û��ͨ������.//cltrҪ����
	public final static String report_status_wait="2";//���һ���¼���ϱ����Ϊstatus=2 ���ȴ�ȡ����˵Ľ��.
	public final static String report_status_pass="3";//���ͨ��ļ�¼
	public final static String report_status_update="4";//���µ���ݣ�
	
	public final static int sys_RowState_delete=1;//删除
	public final static int sys_RowState_insert=2;//添加
	public final static int sys_RowState_update=8;//更新
	
	//ϵͳ���Ͳ��� 0:�����ֲ��ʾ��1������ֲϵͳ��ʾ
	public final static int sys_type=1;//qiguan
	  
	//ȷ��ϵͳ�ǿͻ��˻��Ƿ�����ˡ�0=�ͻ��� 1=�������
	public final static int sys_app_type=1;//qiguan
	
	
}
