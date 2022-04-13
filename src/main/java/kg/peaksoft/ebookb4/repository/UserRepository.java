package kg.peaksoft.ebookb4.repository;

import kg.peaksoft.ebookb4.models.userClasses.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Boolean existsByEmail(String email);
}
