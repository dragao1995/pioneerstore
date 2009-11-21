package com.pioneer.app.exception;

import com.pioneer.app.comm.I18Message;

public class DAOException extends BaseException {

	public DAOException() {
		super(I18Message.getInstance(null).getJSPMessage("exception.dao"));
	}

	public DAOException(String s) {
		super(s);
	}

	public DAOException(String s, Throwable e) {
		super(s, e);
	}

	public DAOException(Throwable e) {
		super(I18Message.getInstance(null).getJSPMessage("exception.dao"), e);
	}

}
