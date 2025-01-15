package co.com.jdti.api_jwt.configuration.security;

import co.com.jdti.api_jwt.security.TokenAuthenticationFilter;
import co.com.jdti.api_jwt.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityContext extends WebSecurityConfigurerAdapter {

	private final TokenAuthenticationFilter tokenAuthenticationFilter;

	private final IUserService userService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/users/sign-up").permitAll()
			.antMatchers("/actuator/health").permitAll()
			.antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
