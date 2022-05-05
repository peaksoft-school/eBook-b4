package kg.peaksoft.ebookb4.db.models.entity.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GenreRequest {

    private String genreName;
    private int count;

    public GenreRequest(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return "GenreRequest{" +
                "genre=" + genreName +
                ", count=" + count +
                '}';
    }
}
