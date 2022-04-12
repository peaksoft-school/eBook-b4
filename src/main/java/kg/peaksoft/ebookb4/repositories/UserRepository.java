package kg.peaksoft.ebookb4.repositories;

import kg.peaksoft.ebookb4.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    Optional<Object> findByEmail (String email);
}