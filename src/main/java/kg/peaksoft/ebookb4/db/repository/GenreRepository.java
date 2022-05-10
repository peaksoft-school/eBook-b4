package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("select g.name from Genre g where g.name = ?1")
    Genre getGenreByName(String name);
}
