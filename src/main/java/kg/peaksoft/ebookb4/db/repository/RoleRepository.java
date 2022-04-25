package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.userClasses.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
