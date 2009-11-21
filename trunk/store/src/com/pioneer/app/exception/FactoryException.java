package com.pioneer.app.exception;

public final class FactoryException extends BaseException {

	private static final long serialVersionUID = 1L;

	public FactoryException() {
		super("创建实例出错");

	}

	public FactoryException(String s) {
		super(s);

	}

	public FactoryException(String s, Throwable e) {
		super(s, e);

	}

	public FactoryException(Throwable e) {
		super("创建实例出错", e);

	}

}// END CLASS OF FactoryException
