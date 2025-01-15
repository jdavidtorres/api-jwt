package co.com.jdti.api_jwt.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserDTO {

	private String name;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	private List<PhoneNumberDTO> phones;
}
