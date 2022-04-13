package kg.peaksoft.ebookb4.dto.request;

import kg.peaksoft.ebookb4.models.others.FileSources;
import kg.peaksoft.ebookb4.models.enums.Genre;
import kg.peaksoft.ebookb4.models.enums.Language;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class AudioBookRequest {

    private List<FileSources> images;

    private String title;
    private String authorFullName;

    private Genre genre;

    private String aboutBook;

    private Language language;

    private LocalDate yearOfIssue;

    private LocalDate duration;

    private Boolean isBestSeller;

    private BigDecimal price;

    private Integer discount;

    private List<FileSources> audioFragment;

    private String urlOfBookFromCloud;
}
