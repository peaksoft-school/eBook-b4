package kg.peaksoft.ebookb4.aws.repo;

import kg.peaksoft.ebookb4.aws.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileInfo,Long> {
}
