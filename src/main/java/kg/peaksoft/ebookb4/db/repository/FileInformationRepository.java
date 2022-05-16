package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.booksClasses.FileInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInformationRepository extends JpaRepository<FileInformation, Long> {
}
