package kg.peaksoft.ebookb4.models.bookClasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.peaksoft.ebookb4.models.others.FileSources;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class AudioBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long audioBookId;

    @JsonFormat(pattern = "")
    private LocalDate duration;

    private String audioBook;

    @OneToMany
    private List<FileSources> fragment;

    private String urlOfBookFromCloud;
}
