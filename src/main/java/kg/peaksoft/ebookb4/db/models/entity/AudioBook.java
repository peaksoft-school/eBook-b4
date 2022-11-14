package kg.peaksoft.ebookb4.db.models.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class AudioBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "audiobook_seq", allocationSize = 1)
    private Long audioBookId;

    private int hour;

    private int minute;

    private int second;

    private String urlFragment;

    private String keyOfFragment;

}
