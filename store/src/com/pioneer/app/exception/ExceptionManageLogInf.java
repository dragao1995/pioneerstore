package com.pioneer.app.exception;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * 
 * ��־����ӿ�
 */
public interface ExceptionManageLogInf {
	// ��ѯ��־
	public List queryErrorLog(Date StartTime, Date EndTime);

	// ��ѯ������ݴ�����Ϣ
	public String queryErrorMsg(String Key);

	// ��¼��ݴ���
	public void addErrLog(String branchcode, Date policydate, String datakey,
			String msg);

	public void saveErrLog(String Key);

	public void deleteErrLog(String sysCode, String branchcode, Date policydate);
}
