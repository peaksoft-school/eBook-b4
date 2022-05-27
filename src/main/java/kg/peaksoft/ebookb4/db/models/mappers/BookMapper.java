package kg.peaksoft.ebookb4.db.models.mappers;


import kg.peaksoft.ebookb4.db.models.dto.BookDTO;
import kg.peaksoft.ebookb4.db.repository.GenreRepository;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@AllArgsConstructor
public class BookMapper {

private final GenreRepository repository;

    public Book create(BookDTO dto) {

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthorFullName(dto.getAuthorFullName());
        book.setPublishingHouse(dto.getPublishingHouse());
        book.setAboutBook(dto.getAboutBook());
        book.setLanguage(dto.getLanguage());
        book.setYearOfIssue(dto.getYearOfIssue());
//        book.setPublishingHouse(dto.getPublishingHouse());
        book.setIsBestSeller(dto.getIsBestSeller());
        book.setPrice(dto.getPrice());
        book.setDiscount(dto.getDiscount());
        book.setBookType(dto.getBookType());

        book.setAudioBook(dto.getAudioBook());
        book.setPaperBook(dto.getPaperBook());
        book.setElectronicBook(dto.getElectronicBook());
        book.setGenre(repository.getById(dto.getGenreId()));
        book.setFileInformation(dto.getFileInformation());

        return book;
    }

}
