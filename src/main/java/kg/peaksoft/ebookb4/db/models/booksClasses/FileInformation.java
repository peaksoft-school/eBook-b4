package kg.peaksoft.ebookb4.db.models.booksClasses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class FileInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "audiobook_seq", allocationSize = 1)
    @Column(name = "file_id")
    private Long fileId;

    private String firstPhoto;
    private String secondPhoto;
    private String bookFile;

    private String keyOfFirstPhoto;
    private String keyOfSecondPhoto;
    private String KeyOfBookFile;

}
