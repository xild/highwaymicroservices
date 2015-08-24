package br.com.highwaypath.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FAILED_DEPENDENCY, reason="Relacionamento não pode ser salvo")
public class RelationshipInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RelationshipInvalidException() {
		super();
	}
}
