package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.booksClasses.FileSources;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.notEntities.SortBooksGlobal;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import kg.peaksoft.ebookb4.db.models.request.GenreRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/books")
@AllArgsConstructor
@Tag(name = "Books", description = "Sort operations")
public class BookGlobalApi {

    private final BookGetService bookGetService;

    @Operation(summary = "Find all books by genre", description = "Sorting books by genre")
    @GetMapping("/genre/{genre}")
    public List<Book> findBooksByGenre(@PathVariable String genre) {
        return bookGetService.findByGenre(genre.toUpperCase(), RequestStatus.ACCEPTED);
    }

    @Operation(summary = "Find books by name", description = "Using linear search while finding, name, title, authorName or publishing house")
    @GetMapping("/name/{name}")
    public List<Book> findBooksByName(@PathVariable String name) {
        return bookGetService.findBooksByName(name, RequestStatus.ACCEPTED);
    }

    @Operation(summary = "Find all active books if needs being sorted", description = "Sorted By Genre, BookType, Price, Language")
    @GetMapping("/sort/{offset}")
    public List<Book> getBooks(@RequestBody SortBooksGlobal sortBook, @PathVariable Integer offset) {
        return bookGetService.getAllBooksOrSortedOnes(sortBook, --offset, 12);
    }

    @Operation(summary = "Find book by id", description = "Get a book by id")
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookGetService.getBookById(id);
    }

    @Operation(summary = "Get all genre count books", description = "get genres countBooks")
    @GetMapping("/genre_count")
    List<GenreRequest> getCountGenre() {
        return bookGetService.getCountGenre();
    }

    @Operation(summary = "Get books with bestSeller = true")
    @GetMapping("/bestSeller")
    public List<Book> isBestSeller(){
        return bookGetService.booksIsBestseller();
    }

    @Operation(summary = "Get all new books")
    @GetMapping("/get-all-new-book")
    public List<Book> getAllNewBook(){
        return bookGetService.BooksNovelties();
    }

    @GetMapping("/vendor-sell")
    @Operation(summary = "Intro to become vendor", description = "This page to intro-e client or guest " +
            "to become vendor!")
    public List<FileSources> becomeVendor() {
        List<FileSources> files = new ArrayList<>();
        FileSources file = new FileSources();
        file.setLine1("В целом, конечно, экономическая повестка");
        file.setLine2("сегодняшнего дня прекрасно подходит для");
        file.setLine3("реализации переосмысления");
        file.setLine4("внешнеэкономических политик.");
        files.add(file);
        files.add(file);
        files.add(file);
        files.add(file);
        files.add(file);
        files.add(file);
        return files;
    }

    @Operation(summary = "Get three books")
    @GetMapping("/three_book")
    public List<Book> getBooks(){
        return bookGetService.getBook();
    }

    @Operation(summary = "Get all audioBook")
    @GetMapping("/audio-book")
    public List<Book> getAllAudioBook(){
        return bookGetService.getAllAudioBook();
    }

    @Operation(summary = "Get all electronic book")
    @GetMapping("/ebook")
    public List<Book> getAllEBook(){
        return bookGetService.getAllEBook();
    }
}
