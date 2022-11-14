package kg.peaksoft.ebookb4.db.models.booksClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class FileInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    private String firstPhoto;

    private String secondPhoto;

    private String thirdPhoto;

    private String bookFile;

    private String keyOfFirstPhoto;

    private String keyOfSecondPhoto;

    private String keyOfThirdPhoto;

    private String KeyOfBookFile;

}
