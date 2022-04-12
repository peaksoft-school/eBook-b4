package kg.peaksoft.ebookb4.repository;

import kg.peaksoft.ebookb4.models.ClientRegister;
import kg.peaksoft.ebookb4.models.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Repository
public interface ClientRepository extends JpaRepository<ClientRegister, Long> {

    Optional<ClientRegister> findByEmail(String email);


}
