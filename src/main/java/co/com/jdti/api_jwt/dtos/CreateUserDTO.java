package co.com.jdti.api_jwt.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserDTO {

	private String name;

	@NotBlank
	@Email
	private String email;

	// This is another validation option for the password field
	@Pattern(
		regexp = "^(?=.*[A-Z])(?=(.*\\d){2})(?=.*[a-z]).{8,12}$",
		message = "Password must have one uppercase letter, two digits and be between 8 and 12 characters"
	)
	@NotBlank
	private String password;

	private List<PhoneNumberDTO> phones;
}
