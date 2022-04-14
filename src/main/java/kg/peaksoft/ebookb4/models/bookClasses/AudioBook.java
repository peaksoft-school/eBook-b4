package kg.peaksoft.ebookb4.models.bookClasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import kg.peaksoft.ebookb4.models.others.FileSources;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class AudioBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long audioBookId;

    @JsonFormat(pattern = "HH-mm-ss")
    private LocalTime duration;

    private String fragment;


    private String urlOfBookFromCloud;
}
