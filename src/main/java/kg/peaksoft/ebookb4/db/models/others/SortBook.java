package kg.peaksoft.ebookb4.db.models.others;

import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class SortBook {

    private BigDecimal price;

    private Genre genre;

    private BookType bookType;

    private Language language;
}
