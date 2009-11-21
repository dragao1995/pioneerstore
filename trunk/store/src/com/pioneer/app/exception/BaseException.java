package com.pioneer.app.exception;

import org.apache.log4j.Logger;

public class BaseException extends RuntimeException {

	private final Logger logger = Logger.getLogger(getClass());

	private static final long serialVersionUID = 3861264310559318709L;

	public BaseException() {
		super();

	}

	public BaseException(String s) {
		this(s, null);
		_rootCause = this;
	}

	public BaseException(String s, Throwable e) {
		super(s);
		if (e instanceof BaseException) {
			_rootCause = ((BaseException) e)._rootCause;
		} else {
			_rootCause = e;
		}
		logger.error(s, e);
	}

	public BaseException(Throwable e) {
		this("", e);
	}

	public Throwable getRootCause() {
		return _rootCause;
	}

	private Throwable _rootCause;

}
