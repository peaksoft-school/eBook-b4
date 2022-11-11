package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.booksClasses.FileSources;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.notEntities.SortBooksGlobal;
import kg.peaksoft.ebookb4.db.models.request.GenreRequest;
import kg.peaksoft.ebookb4.db.service.BookGetService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/books")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Book API", description = "Book sort operations")
public class BookGlobalApi {

    private final BookGetService bookGetService;

    @Operation(summary = "Get all genre count books", description = "get genres countBooks")
    @GetMapping("genre-count")
    List<GenreRequest> getCountGenre() {
        return bookGetService.getCountGenre();
    }

    @Operation(summary = "Find all books by genre", description = "Sorting books by genre")
    @GetMapping("{genre}")
    public List<Book> findBooksByGenre(@PathVariable String genre) {
        return bookGetService.findByGenre(genre.toUpperCase(), RequestStatus.ACCEPTED);
    }

    @Operation(summary = "Find books by name", description = "Using linear search while finding, name, title, authorName or publishing house")
    @GetMapping("{name}")
    public List<Book> findBooksByName(@PathVariable String name) {
        return bookGetService.findBooksByName(name, RequestStatus.ACCEPTED);
    }

    @Operation(summary = "Find all active books if needs being sorted", description = "Sorted By Genre, BookType, Price, Language")
    @GetMapping("sort/{offset}")
    public List<Book> getBooks(@RequestBody SortBooksGlobal sortBook, @PathVariable Integer offset) {
        return bookGetService.getAllBooksOrSortedOnes(sortBook, --offset, 12);
    }

    @Operation(summary = "Find book by id", description = "Get a book by id")
    @GetMapping("{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookGetService.getBookById(id);
    }

    @Operation(summary = "Get books with bestSeller = true")
    @GetMapping("best-seller")
    public List<Book> isBestSeller() {
        return bookGetService.booksIsBestseller();
    }

    @Operation(summary = "Get all new books")
    @GetMapping("all-new-books")
    public List<Book> getAllNewBook() {
        return bookGetService.BooksNovelties();
    }

    @GetMapping("vendor-sell")
    @Operation(summary = "Intro to become vendor", description = "This page to intro-e client or guest to become vendor!")
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
    @GetMapping("three-book")
    public List<Book> getBooks() {
        return bookGetService.getBook();
    }

    @Operation(summary = "Get all audioBook")
    @GetMapping("audio-book")
    public List<Book> getAllAudioBook() {
        return bookGetService.getAllAudioBook();
    }

    @Operation(summary = "Get all electronic book")
    @GetMapping("ebook")
    public List<Book> getAllEBook() {
        return bookGetService.getAllEBook();
    }

}
