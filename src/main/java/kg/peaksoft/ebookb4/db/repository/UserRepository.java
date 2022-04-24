package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Boolean existsByEmail(String email);


  @Query("select s from User s where s.email = ?1")
  Optional<User> getUser(String username);

  //fin all vendors / admin panel
  @Query("select u from User u where u.role.name=?1")
  List<User> findAllVendors(ERole role);

  //fin all clients / admin panel
  @Query("select u from User u where u.role.name=?1")
  List<User> findAllClients(ERole role);
}
