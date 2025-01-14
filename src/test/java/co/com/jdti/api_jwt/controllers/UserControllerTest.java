package co.com.jdti.api_jwt.controllers;

import co.com.jdti.api_jwt.dtos.CreateUserDTO;
import co.com.jdti.api_jwt.dtos.CreatedUserResponseDTO;
import co.com.jdti.api_jwt.dtos.PhoneNumberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void signUp_Success() throws Exception {
		List<PhoneNumberDTO> phonesDTO = List.of(new PhoneNumberDTO(123456L, 57, "1"));
		CreateUserDTO createUserDTO = new CreateUserDTO("John Doe", "john.doe@example.com", "Password123", phonesDTO);

		mockMvc.perform(post("/users/sign-up")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(createUserDTO)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.created").isNotEmpty())
			.andExpect(jsonPath("$.lastLogin").isNotEmpty())
			.andExpect(jsonPath("$.token").isNotEmpty())
			.andExpect(jsonPath("$.isActive").value(true));
	}

	@Test
	void signUp_InvalidPassword() throws Exception {
		List<PhoneNumberDTO> phonesDTO = List.of(new PhoneNumberDTO(123456L, 57, "1"));
		CreateUserDTO createUserDTO = new CreateUserDTO("John Doe", "john.doe@example.com", "invalid", phonesDTO);

		mockMvc.perform(post("/users/sign-up")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(createUserDTO)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(400))
			.andExpect(jsonPath("$.detail").value(containsString("Password must have")));
	}

	@Test
	void login_Success() throws Exception {
		List<PhoneNumberDTO> phonesDTO = List.of(new PhoneNumberDTO(123456L, 57, "1"));
		CreateUserDTO signUpRequest = new CreateUserDTO("Jane Doe", "john.doe@example.com", "Password123", phonesDTO);

		MvcResult signUpResult = mockMvc.perform(post("/users/sign-up")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(signUpRequest)))
			.andExpect(status().isCreated())
			.andReturn();

		String signUpResponse = signUpResult.getResponse().getContentAsString();
		CreatedUserResponseDTO registeredUser = objectMapper.readValue(signUpResponse, CreatedUserResponseDTO.class);
		String token = registeredUser.getToken();

		mockMvc.perform(post("/users/login")
				.header("Authorization", "Bearer " + token))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.created").isNotEmpty())
			.andExpect(jsonPath("$.lastLogin").isNotEmpty())
			.andExpect(jsonPath("$.token").isNotEmpty())
			.andExpect(jsonPath("$.isActive").value(true))
			.andExpect(jsonPath("$.email").value("jane.doe@example.com"))
			.andExpect(jsonPath("$.password").isNotEmpty())
			.andExpect(jsonPath("$.phones").isArray());
	}
}
