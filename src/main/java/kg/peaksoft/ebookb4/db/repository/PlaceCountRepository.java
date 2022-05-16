package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.entity.PlaceCounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceCountRepository extends JpaRepository<PlaceCounts, Long> {
}
