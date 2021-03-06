package no.plasmid.blopp.exception;

import org.springframework.http.HttpStatus;

public class VertexNotFoundException extends BloppException {

	private static final long serialVersionUID = 1L;

	public VertexNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}

}
