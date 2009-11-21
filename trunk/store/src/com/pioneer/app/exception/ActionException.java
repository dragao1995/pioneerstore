package com.pioneer.app.exception;

public class ActionException extends BaseException {

	public ActionException() {
		super("控制逻辑出错");
	}

	public ActionException(String s) {
		super(s);
	}

	public ActionException(String s, Throwable e) {
		super(s, e);
	}

	public ActionException(Throwable e) {
		super("控制逻辑出错", e);
	}

}
