package kg.peaksoft.ebookb4.dto.response;

import kg.peaksoft.ebookb4.db.models.enums.BookType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
