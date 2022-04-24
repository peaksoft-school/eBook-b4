package kg.peaksoft.ebookb4.dto;

import kg.peaksoft.ebookb4.db.models.enums.Genre;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GenreDto {

    Long id;
    Genre genre;
}
