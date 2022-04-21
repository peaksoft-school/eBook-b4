package kg.peaksoft.ebookb4.db.models.others;

import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class SortBook {

    private Double min;

    private Double max;

    private List<Genre> genre;

    private BookType bookType;

    private List<Language> language;
}
