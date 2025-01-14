package co.com.jdti.api_jwt.exceptions;

import co.com.jdti.api_jwt.dtos.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponseDTO> handleUserException(UserException ex) {
		ErrorResponseDTO errorResponse = new ErrorResponseDTO(LocalDateTime.now(), ex.getStatusCode(), ex.getMessage());
		return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
	}
}
