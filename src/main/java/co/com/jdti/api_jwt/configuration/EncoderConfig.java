package co.com.jdti.api_jwt.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncoderConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = "bearerAuth";

		return new OpenAPI()
			.info(new Info()
				.title("Authentication Microservice API")
				.version("1.0")
				.description("API documentation for the authentication microservice."))
			.addSecurityItem(new SecurityRequirement()
				.addList(securitySchemeName))
			.components(new Components()
				.addSecuritySchemes(securitySchemeName,
					new SecurityScheme()
						.name(securitySchemeName)
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")));
	}
}
