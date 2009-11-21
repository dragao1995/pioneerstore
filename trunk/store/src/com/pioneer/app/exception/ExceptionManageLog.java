package com.pioneer.app.exception;

import java.util.Date;
import java.util.List;

import com.pioneer.frame.dao.IDAO;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class ExceptionManageLog implements ExceptionManageLogInf, Cloneable {

	private IDAO dao;

	public ExceptionManageLog() {
	}

	public ExceptionManageLog(IDAO dao) {
		this.dao = dao;
	}

	public IDAO getDao() {
		return dao;
	}

	public void setDao(IDAO dao) {
		this.dao = dao;
	}

	public List queryErrorLog(Date StartTime, Date EndTime) {
		return null;
	}

	public String queryErrorMsg(String Key) {
		return "";
	}

	public void addErrLog(String branchcode, Date policydate, String datakey,
			String msg) {
	}

	public void saveErrLog(String Key) {
	}

	public void deleteErrLog(String sysCode, String branchcode, Date policydate) {
	}

	public Object Clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
