package kg.peaksoft.ebookb4.dto.request;

import kg.peaksoft.ebookb4.db.models.enums.Genre;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GenreRequest {

    private Genre genre;
    private int count;

    public GenreRequest(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "GenreRequest{" +
                "genre=" + genre +
                ", count=" + count +
                '}';
    }
}
