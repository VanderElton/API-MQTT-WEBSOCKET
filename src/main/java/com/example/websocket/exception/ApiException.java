package com.example.websocket.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Object[] messageParams;
	private final HttpStatus httpStatus;

	public ApiException(String messageKey, Object... messageParams) {
		super(messageKey);
		this.messageParams = Arrays.copyOf(messageParams, messageParams.length);
		httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public ApiException(String messageKey, HttpStatus httpStatus, Object... messageParams) {
		super(messageKey);
		this.messageParams = Arrays.copyOf(messageParams, messageParams.length);
		this.httpStatus = httpStatus;
	}

	public Object[] getMessageParams() {
		return Arrays.copyOf(messageParams, messageParams.length);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
