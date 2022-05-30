package kg.peaksoft.ebookb4.db.models.response;

import kg.peaksoft.ebookb4.db.models.enums.BookType;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseAfterSaved {
    private Long id;
    private String name;
    private BookType bookType;
    private String message;
}
