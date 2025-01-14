package co.com.jdti.api_jwt.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponseDTO {

	private LocalDateTime timestamp;
	private int code;
	private String detail;
}
