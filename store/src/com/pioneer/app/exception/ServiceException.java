package com.pioneer.app.exception;

public class ServiceException extends BaseException {

	public ServiceException() {
		super("业务逻辑出错");

	}

	public ServiceException(String s) {
		super(s);
	}

	public ServiceException(String s, Throwable e) {
		super(s, e);

	}

	public ServiceException(Throwable e) {
		super("业务逻辑出错", e);
	}

}
