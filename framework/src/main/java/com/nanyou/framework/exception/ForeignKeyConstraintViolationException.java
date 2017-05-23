package com.nanyou.framework.exception;

import org.springframework.dao.DataAccessException;

public class ForeignKeyConstraintViolationException extends DataAccessException {

	public ForeignKeyConstraintViolationException(String msg, Throwable ex) {
		super(msg, ex);
	}

	public ForeignKeyConstraintViolationException(String msg) {
		super(msg);
	}

}
