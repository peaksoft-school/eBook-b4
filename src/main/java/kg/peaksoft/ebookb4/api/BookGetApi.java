package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.models.others.SortBook;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import kg.peaksoft.ebookb4.db.service.impl.BookGetServiceImpl;
import kg.peaksoft.ebookb4.dto.PriseDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@Tag(name = "Books",description = "Sort operations")
public class BookGetApi {

    private final BookGetService bookGetService;

    @Operation(summary = "Find All By Genre")
    @GetMapping("/findBooksByGenre/{genre}")
    public List<Book> findBooksByGenre(@PathVariable Genre genre) {
        return bookGetService.findByGenre(genre);
    }

    @Operation(summary = "Find All By Title,or AuthorName,or PublishingHouse")
    @GetMapping("/findBooksByName/{name}")
    public List<Book> findBooksByName(@PathVariable String name) {
        return bookGetService.findBooksByName(name);
    }

    @Operation(summary = "Find All books if needs being sorted" , description = "Sorted By Genre, BookType, Price, Language")
    @PostMapping("/getBooks")
    public List<Book> getBooks(@RequestBody SortBook sortBook) {
        return bookGetService.getAllBooksOrSortedOnes(sortBook);
    }

    @Operation(summary = "Find book by id", description = "Get a book by id")
    @GetMapping("/findBookById{id}")
    public Book getBookById(@PathVariable Long id){
        return bookGetService.getBookById(id);
    }
}
