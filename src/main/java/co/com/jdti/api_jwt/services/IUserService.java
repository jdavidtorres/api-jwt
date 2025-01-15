package co.com.jdti.api_jwt.services;

import co.com.jdti.api_jwt.dtos.CreateUserDTO;
import co.com.jdti.api_jwt.dtos.CreatedUserResponseDTO;
import co.com.jdti.api_jwt.dtos.LoginResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

	CreatedUserResponseDTO signUp(CreateUserDTO createUser);

	LoginResponseDTO login(String token);
}
