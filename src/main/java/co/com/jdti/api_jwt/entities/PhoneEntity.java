package co.com.jdti.api_jwt.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Table(name = "phones")
public class PhoneEntity {

	private Long number;
	private Integer citycode;
	private String countrycode;
}
