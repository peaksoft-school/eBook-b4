package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.booksClasses.SortBook;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@Tag(name = "Books",description = "Sort operations")
public class BookGlobalApi {

    private final BookGetService bookGetService;

    @Operation(summary = "find All By Genre", description = "Using enum genre")
    @GetMapping("/findBooksByGenre/{genre}")
    public List<Book> findBooksByGenre(@PathVariable Genre genre) {
        return bookGetService.findByGenre(genre);
    }

    @Operation(summary = "find books by name",description = "Using linear search while finding")
    @GetMapping("/findBooksByName/{name}")
    public List<Book> findBooksByName(@PathVariable String name) {
        return bookGetService.findBooksByName(name);
    }

    @Operation(summary = "Find active books" , description = "Sorted By Genre, BookType, Price, Language")
    @PostMapping("/getBooks/{offset}")
    public List<Book> getBooks(@RequestBody SortBook sortBook, @PathVariable Integer offset) {
        return bookGetService.getAllBooksOrSortedOnes(sortBook, --offset, 12);
    }

    @Operation(summary = "find book by id", description = "Get a book by id")
    @GetMapping("/findBookById{id}")
    public Book getBookById(@PathVariable Long id){
        return bookGetService.getBookById(id);
    }
}
