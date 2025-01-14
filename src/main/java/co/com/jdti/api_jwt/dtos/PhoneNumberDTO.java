package co.com.jdti.api_jwt.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PhoneNumberDTO {

	private Long number;
	private Integer citycode;
	private String countrycode;
}
