package co.com.jdti.api_jwt.services;

import co.com.jdti.api_jwt.dtos.CreateUserDTO;
import co.com.jdti.api_jwt.dtos.CreatedUserResponseDTO;
import co.com.jdti.api_jwt.dtos.PhoneNumberDTO;
import co.com.jdti.api_jwt.entities.PhoneEntity;
import co.com.jdti.api_jwt.entities.UserEntity;
import co.com.jdti.api_jwt.exceptions.UserException;
import co.com.jdti.api_jwt.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

	private IUserRepository userRepository;

	private TokenHelperService tokenHelperService;

	private IUserService userService;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@BeforeEach
	public void setUp() {
		userRepository = mock(IUserRepository.class);
		tokenHelperService = mock(TokenHelperService.class);
		bCryptPasswordEncoder = new BCryptPasswordEncoder();
		userService = new UserServiceImpl(userRepository, tokenHelperService, bCryptPasswordEncoder);
	}

	@Test
	public void testCreateUser_Success() {
		// Given
		PhoneNumberDTO phoneDTO = new PhoneNumberDTO(12345678L, 1, "57");
		List<PhoneNumberDTO> phonesDTO = List.of(phoneDTO);
		CreateUserDTO userDTO = new CreateUserDTO("John Doe", "john.doe@example.com", "passworD123", phonesDTO);

		// When
		when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
		when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
			UserEntity userEntity = invocation.getArgument(0);
			userEntity.setId(UUID.randomUUID().toString());
			return userEntity;
		});
		when(tokenHelperService.generateToken(userDTO.getEmail())).thenReturn("token");

		CreatedUserResponseDTO response = userService.signUp(userDTO);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();
		assertThat(response.getCreated()).isNotNull();
		assertThat(response.getLastLogin()).isNotNull();
		assertThat(response.getToken()).isNotNull();
		assertTrue(response.getIsActive());

		assertEquals("token", response.getToken());

		verify(userRepository, times(1)).save(any(UserEntity.class));
		verify(tokenHelperService, times(1)).generateToken(userDTO.getEmail());
	}

	@Test
	public void testCreateUser_Fail() {
		// Given
		PhoneNumberDTO phoneDTO = new PhoneNumberDTO(12345678L, 1, "57");
		List<PhoneNumberDTO> phonesDTO = List.of(phoneDTO);
		CreateUserDTO userDTO = new CreateUserDTO("John Doe", "john.doe@example.com", "passworD123", phonesDTO);

		// When
		when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(new UserEntity()));

		// Then
		UserException exception = assertThrows(UserException.class, () -> userService.signUp(userDTO));

		assertEquals("Registration could not be completed", exception.getMessage());
		verify(userRepository, never()).save(any(UserEntity.class));
	}

	@Test
	public void testCreateUser_PasswordInvalid() {
		// Given
		PhoneNumberDTO phoneDTO = new PhoneNumberDTO(12345678L, 1, "57");
		List<PhoneNumberDTO> phonesDTO = List.of(phoneDTO);
		CreateUserDTO userDTO = new CreateUserDTO("John Doe", "john.doe@example.com", "invalid", phonesDTO);

		// When
		UserException exception = assertThrows(UserException.class, () -> userService.signUp(userDTO));

		// Then
		assertEquals("Password must have one uppercase letter, two digits and be between 8 and 12 characters", exception.getMessage());
		verify(userRepository, never()).save(any(UserEntity.class));
	}

	@Test
	public void testLogin_Success() {
		// Given
		String token = "fake-token";
		UserEntity userEntity = new UserEntity();
		userEntity.setId(UUID.randomUUID().toString());
		userEntity.setName("John Doe");
		userEntity.setEmail("john.doe@example.com");
		userEntity.setPassword("passworD123");
		userEntity.setIsActive(true);
		userEntity.setPhones(List.of(new PhoneEntity(12345678L, 1, "57")));

		// When
		when(tokenHelperService.getEmail(token)).thenReturn(userEntity.getEmail());
		when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.of(userEntity));
		when(tokenHelperService.generateToken(userEntity.getEmail())).thenReturn("token");

		// Then
		assertThat(userService.login(token)).isNotNull();
		verify(tokenHelperService, times(1)).getEmail(token);
		verify(tokenHelperService, times(1)).generateToken(userEntity.getEmail());
		verify(userRepository, times(1)).findByEmail(userEntity.getEmail());
		verify(userRepository, times(1)).save(userEntity);
	}

	@Test
	public void testLogin_UserNotFound() {
		// Given
		String token = "fake-token";
		UserEntity userEntity = new UserEntity();
		userEntity.setId(UUID.randomUUID().toString());
		userEntity.setName("John Doe");
		userEntity.setEmail("john.doe@example.com");
		userEntity.setPassword("passworD123");
		userEntity.setIsActive(true);
		userEntity.setPhones(List.of());

		// When
		when(tokenHelperService.getEmail(token)).thenReturn(userEntity.getEmail());
		when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.empty());

		// Then
		UserException exception = assertThrows(UserException.class, () -> userService.login(token));
		assertEquals("User not found", exception.getMessage());
	}
}
