package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.notEntities.SortBooksGlobal;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import kg.peaksoft.ebookb4.dto.request.GenreRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public List<Book> findBooksByGenre(@PathVariable Genre genre) {
        return bookGetService.findByGenre(genre, RequestStatus.ACCEPTED);
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
}
