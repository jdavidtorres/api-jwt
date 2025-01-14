package co.com.jdti.api_jwt.exceptions;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

	private final int statusCode;

	public UserException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}
}
