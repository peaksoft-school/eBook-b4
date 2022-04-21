package kg.peaksoft.ebookb4.dto.mapper;

import kg.peaksoft.ebookb4.dto.request.BookRequest;
import kg.peaksoft.ebookb4.db.models.books.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book create(BookRequest dto) {

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthorFullName(dto.getAuthorFullName());
        book.setPublishingHouse(dto.getPublishingHouse());
        book.setAboutBook(dto.getAboutBook());
        book.setGenre(dto.getGenre());
        book.setLanguage(dto.getLanguage());
        book.setYearOfIssue(dto.getYearOfIssue());
        book.setPublishingHouse(dto.getPublishingHouse());
        book.setIsBestSeller(dto.getIsBestSeller());
        book.setPrice(dto.getPrice());
        book.setDiscount(dto.getDiscount());
        book.setBookType(dto.getBookType());

        book.setAudioBook(dto.getAudioBook());
        book.setPaperBook(dto.getPaperBook());
        book.setElectronicBook(dto.getElectronicBook());

        return book;
    }
}
