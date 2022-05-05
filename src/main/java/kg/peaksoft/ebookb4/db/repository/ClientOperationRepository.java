package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.booksClasses.ClientOperations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientOperationRepository extends JpaRepository<ClientOperations, Long> {
}
