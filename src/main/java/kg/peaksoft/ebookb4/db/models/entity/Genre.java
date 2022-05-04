package kg.peaksoft.ebookb4.db.models.entity;

/**
 * Author: Nurbek Abdirasulov
 * Date: 04/5/22
 */

import kg.peaksoft.ebookb4.db.models.books.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "genres")
@NoArgsConstructor
public class Genre {
    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) && Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
//    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,
//    mappedBy = "genres")
//    private List<Book> books;
//    public void addBook(Book book){
//        if(this.books == null){
//            this.books = new ArrayList<>();
//        }
//        this.books.add(book);
//    }
    @OneToMany()
    private List<Book> books;
    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
