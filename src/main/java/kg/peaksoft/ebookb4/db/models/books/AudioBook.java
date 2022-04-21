package kg.peaksoft.ebookb4.db.models.books;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class AudioBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "audiobook_seq", allocationSize = 1)
    private Long audioBookId;

    @JsonFormat(pattern = "HH-mm-ss")
    private LocalTime duration;

    private String fragment;


    private String urlOfBookFromCloud;
}
