package kg.peaksoft.ebookb4.repositories;

import kg.peaksoft.ebookb4.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Long> {

}