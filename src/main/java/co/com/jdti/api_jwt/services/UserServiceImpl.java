package co.com.jdti.api_jwt.services;

import co.com.jdti.api_jwt.dtos.CreateUserDTO;
import co.com.jdti.api_jwt.dtos.CreatedUserResponseDTO;
import co.com.jdti.api_jwt.dtos.LoginResponseDTO;
import co.com.jdti.api_jwt.dtos.PhoneNumberDTO;
import co.com.jdti.api_jwt.entities.PhoneEntity;
import co.com.jdti.api_jwt.entities.UserEntity;
import co.com.jdti.api_jwt.exceptions.UserException;
import co.com.jdti.api_jwt.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

	private final IUserRepository userRepository;
	private final TokenHelperService tokenHelperService;

	@Override
	public CreatedUserResponseDTO signUp(CreateUserDTO createUser) {
		if (userRepository.findByEmail(createUser.getEmail()).isPresent()) {
			throw new UserException("Registration could not be completed", HttpStatus.BAD_REQUEST.value());
		}

		isPasswordValid(createUser.getPassword());

		List<PhoneEntity> phones = new ArrayList<>();
		createUser.getPhones().forEach(phone -> {
			PhoneEntity phoneEntity = PhoneEntity.builder()
				.number(phone.getNumber())
				.citycode(phone.getCitycode())
				.countrycode(phone.getCountrycode())
				.build();
			phones.add(phoneEntity);
		});

		UserEntity userToSave = UserEntity.builder()
			.id(UUID.randomUUID().toString())
			.name(createUser.getName())
			.email(createUser.getEmail())
			.password(createUser.getPassword())
			.created(LocalDateTime.now())
			.lastLogin(LocalDateTime.now())
			.isActive(true)
			.phones(phones)
			.build();

		UserEntity userSaved = userRepository.save(userToSave);

		return CreatedUserResponseDTO.builder()
			.id(userSaved.getId())
			.created(userSaved.getCreated().toString())
			.lastLogin(userSaved.getLastLogin().toString())
			.token(tokenHelperService.generateToken(userSaved.getEmail()))
			.isActive(userSaved.getIsActive())
			.build();
	}

	@Override
	public LoginResponseDTO login(String token) {
		String emailFromToken = tokenHelperService.getEmail(token);
		UserEntity user = userRepository.findByEmail(emailFromToken).orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND.value()));

		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);

		return LoginResponseDTO.builder()
			.id(user.getId())
			.created(user.getCreated())
			.lastLogin(user.getLastLogin())
			.token(tokenHelperService.generateToken(user.getEmail()))
			.isActive(user.getIsActive())
			.name(user.getName())
			.email(user.getEmail())
			.phones(user.getPhones()
				.stream().map(phone -> new PhoneNumberDTO(phone.getNumber(), phone.getCitycode(), phone.getCountrycode())).collect(Collectors.toList()))
			.build();
	}

	// This method is a validation into the service layer
	private void isPasswordValid(String password) {
		String regex = "^(?=.*[A-Z])(?=(.*\\d.*){2})(?!.*[^a-zA-Z\\d]).{8,12}$";
		if (!password.matches(regex)) {
			throw new UserException("Password must have one uppercase letter, two digits and be between 8 and 12 characters", HttpStatus.BAD_REQUEST.value());
		}
	}
}
