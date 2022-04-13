package kg.peaksoft.ebookb4.dto.mapper;

import kg.peaksoft.ebookb4.dto.request.AudioBookRequest;
import kg.peaksoft.ebookb4.models.bookClasses.Book;
import org.springframework.stereotype.Component;

@Component
public class AudioBookMapper {

    public Book create(AudioBookRequest dto) {

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthorFullName(dto.getAuthorFullName());
        book.setGenre(dto.getGenre());

        book.getAudioBook().setAudioBook(dto.getAboutBook());
        book.getAudioBook().setDuration(dto.getDuration());
        book.getAudioBook().setFragment(dto.getAudioFragment());
        book.getAudioBook().setUrlOfBookFromCloud(dto.getUrlOfBookFromCloud());

        book.setLanguage(dto.getLanguage());
        book.setYearOfIssue(dto.getYearOfIssue());
        book.setIsBestSeller(dto.getIsBestSeller());
        book.setPrice(dto.getPrice());
        book.setDiscount(dto.getDiscount());

        return book;
    }
}
