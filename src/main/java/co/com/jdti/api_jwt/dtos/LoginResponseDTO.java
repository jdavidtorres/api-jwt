package co.com.jdti.api_jwt.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDTO {

	private String id;
	private LocalDateTime created;
	private LocalDateTime lastLogin;
	private String token;
	private Boolean isActive;
	private String name;
	private String email;
	private List<PhoneNumberDTO> phones;
}
