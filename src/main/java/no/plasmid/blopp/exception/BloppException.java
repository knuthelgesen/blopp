package no.plasmid.blopp.exception;

import org.springframework.http.HttpStatus;

public abstract class BloppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus httpStatus;

	public BloppException(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public BloppException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}
	
	public BloppException(HttpStatus httpStatus, Throwable cause) {
		super(cause);
		this.httpStatus = httpStatus;
	}
	
	public BloppException(HttpStatus httpStatus, String message, Throwable cause) {
		super(message, cause);
		this.httpStatus = httpStatus;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
