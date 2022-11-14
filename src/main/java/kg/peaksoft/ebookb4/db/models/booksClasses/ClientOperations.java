package kg.peaksoft.ebookb4.db.models.booksClasses;

import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class ClientOperations {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "operation_seq", allocationSize = 1)
    @Column(name = "operation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "operations")
    private List<Book> boughtBooks;

}