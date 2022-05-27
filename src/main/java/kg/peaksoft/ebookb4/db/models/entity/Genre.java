package kg.peaksoft.ebookb4.db.models.entity;

/**
 * Author: Nurbek Abdirasulov
 * Date: 04/5/22
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String rusName;

    @JsonIgnore
    @OneToMany(mappedBy = "genre")
    private List<Book> books;

    public Genre(Long id, String name, String rusName) {
        this.id = id;
        this.name = name;
        this.rusName=rusName;
    }

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

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
