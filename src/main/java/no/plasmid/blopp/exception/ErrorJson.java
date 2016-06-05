package no.plasmid.blopp.exception;

import org.springframework.http.HttpStatus;

public class ErrorJson {

	private int statusCode;
	private String message;
	
	public ErrorJson() {}

	public ErrorJson(BloppException exception) {
		this.statusCode = exception.getHttpStatus().value();
		this.message = exception.getMessage();
	}

	public ErrorJson(HttpStatus status, String message) {
		this.statusCode = status.value();
		this.message = message;
	}

	public int getStatusCode() { return statusCode; }
	public String getMessage() { return message; }

	public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
	public void setMessage(String message) { this.message = message; }

}
