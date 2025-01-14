package co.com.jdti.api_jwt.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private String id;

	private String name;

	private String email;

	private String password;

	@ElementCollection
	private List<PhoneEntity> phones;

	private LocalDateTime created;

	private LocalDateTime lastLogin;

	private Boolean isActive;
}
