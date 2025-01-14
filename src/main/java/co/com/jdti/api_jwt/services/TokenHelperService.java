package co.com.jdti.api_jwt.services;

import co.com.jdti.api_jwt.exceptions.UserException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenHelperService {

	private static final SecretKey KEY = Jwts.SIG.HS256.key().build();

	public String generateToken(String email) {
		return Jwts.builder()
			.header()
			.type("JWT")
			.and()
			.subject(email)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
			.signWith(KEY)
			.compact();
	}

	public String getEmail(String token) {
		try {
			return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload().getSubject();
		} catch (Exception e) {
			throw new UserException("Invalid token", HttpStatus.BAD_REQUEST.value());
		}
	}
}
