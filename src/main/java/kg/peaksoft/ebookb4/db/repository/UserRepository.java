package kg.peaksoft.ebookb4.db.repository;


import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.ResponseClient;
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


@Query("select c from User c where c.role.name = ?1 ")

   List<ResponseClient> getAllClients(ERole role);


}
