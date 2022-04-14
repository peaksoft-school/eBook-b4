package kg.peaksoft.ebookb4.dto.mapper;

import kg.peaksoft.ebookb4.dto.request.ElectronicBookRequest;
import kg.peaksoft.ebookb4.dto.request.PaperBookRequest;
import kg.peaksoft.ebookb4.models.bookClasses.Book;
import kg.peaksoft.ebookb4.models.enums.BookType;

public class PaperBookMapper {

    public Book create(PaperBookRequest dto) {

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
        book.setBookType(BookType.Ebook);
        book.getPaperBook().setPublishingHouse(dto.getPublishingHouse());
        book.getPaperBook().setFragmentOfBook(dto.getFragmentOfBook());
        book.getPaperBook().setNumberOfPages(dto.getNumberOfPages());
        book.getPaperBook().setNumberOfSelected(dto.getNumberOfSelected());

//        book.getImages().add(dto.getImages());

        return book;
    }
}
