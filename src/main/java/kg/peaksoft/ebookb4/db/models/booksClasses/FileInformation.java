package kg.peaksoft.ebookb4.db.models.booksClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class FileInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    private String firstPhoto;
    private String secondPhoto;
    private String bookFile;

    private String keyOfFirstPhoto;
    private String keyOfSecondPhoto;
    private String KeyOfBookFile;

}
