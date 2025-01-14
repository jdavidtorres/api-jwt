package co.com.jdti.api_jwt.repositories;

import co.com.jdti.api_jwt.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, String> {

	Optional<UserEntity> findByEmail(String email);
}
