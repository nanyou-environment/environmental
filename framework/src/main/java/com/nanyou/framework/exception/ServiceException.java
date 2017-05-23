/*
 ****************************************************
 *   ServiceException.java
 *   内部错误处理
 *   update    2007-01-05	yang jianguo
 *   错误信息改在类的构�?函数中生成，原来在getMessage()中生成，因为有些对象已经释放，信息不�?��能显�?
 *   Copyright 2006 SPSOFT. All Rights Reserved. 
 ****************************************************
*/

package com.nanyou.framework.exception;


import org.springframework.core.NestedRuntimeException;

public class ServiceException extends NestedRuntimeException {

	/**
	 * 
	 */
	private String message;
	
	private static final long serialVersionUID = -871158005964778467L;

	public ServiceException(String msg, Throwable ex) {
		super(msg, ex);
		this.message = msg;
	}

	public ServiceException(String msg) {
		super(msg);
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}
	
	
	
	
}
