package kg.peaksoft.ebookb4.db.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreRequest {

    private Long genreId;

    private String genreName;

    private int count;

    public GenreRequest(String genreName, Long genreId) {
        this.genreName = genreName;
        this.genreId = genreId;
    }

}
