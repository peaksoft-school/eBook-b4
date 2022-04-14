package kg.peaksoft.ebookb4.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.peaksoft.ebookb4.models.enums.BookType;
import kg.peaksoft.ebookb4.models.enums.Genre;
import kg.peaksoft.ebookb4.models.enums.Language;
import kg.peaksoft.ebookb4.models.others.FileSources;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class AudioBookRequest {

    private String title;

//    private FileSources images;

    private String authorFullName;
    private String aboutBook;
    private LocalDate yearOfIssue;
    private Integer discount;
    private BigDecimal price;
    private Boolean isBestSeller;
    private Language language;
    private Genre genre;
    private LocalDate duration;
    private String fragment;
    private String urlOfBookFromCloud;


}
