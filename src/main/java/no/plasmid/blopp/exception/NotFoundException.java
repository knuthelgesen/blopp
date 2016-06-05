package no.plasmid.blopp.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BloppException {

	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super(HttpStatus.NOT_FOUND);
	}

	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}

}
