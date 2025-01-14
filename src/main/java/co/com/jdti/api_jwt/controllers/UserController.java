package co.com.jdti.api_jwt.controllers;

import co.com.jdti.api_jwt.dtos.CreateUserDTO;
import co.com.jdti.api_jwt.dtos.CreatedUserResponseDTO;
import co.com.jdti.api_jwt.dtos.LoginResponseDTO;
import co.com.jdti.api_jwt.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final IUserService userService;

	@PostMapping("/sign-up")
	public ResponseEntity<CreatedUserResponseDTO> signUp(@RequestBody CreateUserDTO createUserDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(createUserDTO));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(HttpServletRequest request) {
		String token = request.getHeader("Authorization").replace("Bearer", "").trim();
		return ResponseEntity.ok(userService.login(token));
	}
}
