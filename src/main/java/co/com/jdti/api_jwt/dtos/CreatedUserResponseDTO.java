package co.com.jdti.api_jwt.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreatedUserResponseDTO {

	private String id;
	private String created;
	private String lastLogin;
	private String token;
	private Boolean isActive;
}
