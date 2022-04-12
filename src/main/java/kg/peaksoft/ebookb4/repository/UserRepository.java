package kg.peaksoft.ebookb4.repository;

import kg.peaksoft.ebookb4.models.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Repository
public interface UserRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByEmail(String email);

}
