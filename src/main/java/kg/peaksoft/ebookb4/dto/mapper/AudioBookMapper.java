package kg.peaksoft.ebookb4.dto.mapper;

import kg.peaksoft.ebookb4.dto.request.AudioBookRequest;
import kg.peaksoft.ebookb4.models.bookClasses.Book;
import kg.peaksoft.ebookb4.models.enums.BookType;
import kg.peaksoft.ebookb4.models.enums.Genre;
import kg.peaksoft.ebookb4.models.enums.Language;
import kg.peaksoft.ebookb4.models.others.FileSources;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class AudioBookMapper {

    public Book create(AudioBookRequest dto) {

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthorFullName(dto.getAuthorFullName());
        book.setAboutBook(dto.getAboutBook());
        book.setGenre(dto.getGenre());
        book.setLanguage(dto.getLanguage());
        book.setYearOfIssue(dto.getYearOfIssue());
        book.setIsBestSeller(dto.getIsBestSeller());
        book.setPrice(dto.getPrice());
        book.setDiscount(dto.getDiscount());
        book.setBookType(BookType.AudioBook);
        book.getAudioBook().setDuration(dto.getDuration());
        book.getAudioBook().setFragment(dto.getFragment());
        book.getAudioBook().setUrlOfBookFromCloud(dto.getUrlOfBookFromCloud());
//        book.getImages().add(dto.getImages());

        return book;
    }
}
