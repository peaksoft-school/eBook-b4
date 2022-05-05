package kg.peaksoft.ebookb4.aws.model;

import kg.peaksoft.ebookb4.aws.enums.FolderName;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Getter
@Setter
@EqualsAndHashCode
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private FolderName folderName;

    private String fileName;


    public FileInfo(Long id, FolderName folderName, String fileName) {
        this.id = id;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public FileInfo() {

    }
}
