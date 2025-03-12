package com.interesting.common.exception;

public class InterestingBootException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InterestingBootException(String message){
		super(message);
	}

	public InterestingBootException(Throwable cause)
	{
		super(cause);
	}

	public InterestingBootException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
