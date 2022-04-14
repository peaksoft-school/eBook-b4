package kg.peaksoft.ebookb4.dto.mapper;

import kg.peaksoft.ebookb4.dto.request.ElectronicBookRequest;
import kg.peaksoft.ebookb4.models.bookClasses.Book;
import kg.peaksoft.ebookb4.models.enums.BookType;
import org.springframework.stereotype.Component;

@Component
public class eBookMapper {

    public Book create(ElectronicBookRequest dto) {

        Book ebook = new Book();
        ebook.setTitle(dto.getTitle());
        ebook.setAuthorFullName(dto.getAuthorFullName());
        ebook.setAboutBook(dto.getAboutBook());
        ebook.setGenre(dto.getGenre());
        ebook.setLanguage(dto.getLanguage());
        ebook.setYearOfIssue(dto.getYearOfIssue());
        ebook.setIsBestSeller(dto.getIsBestSeller());
        ebook.setPrice(dto.getPrice());
        ebook.setDiscount(dto.getDiscount());
        ebook.setBookType(BookType.Ebook);
        ebook.getElectronicBook().setPublishingHouse(dto.getPublishingHouse());
        ebook.getElectronicBook().setFragmentOfBook(dto.getFragmentOfBook());
        ebook.getElectronicBook().setUrlOfBookFromCloud(dto.getUrlOfBookFromCloud());
        ebook.getElectronicBook().setNumberOfPages(dto.getNumberOfPages());

//        book.getImages().add(dto.getImages());

        return ebook;
    }
}
