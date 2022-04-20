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
@PreAuthorize("hasAnyAuthority('ROLE_VENDOR','ROLE_CLIENT')")
@AllArgsConstructor
@Tag(name = "Books",description = "Sort operations")
public class BookGetApi {

    private final BookGetService bookGetService;

    @Operation(summary = "Find All By Genre")
    @GetMapping("/genre/{genre}")
    public List<Book> findAllByGenre(@PathVariable Genre genre) {
        return bookGetService.findByGenre(genre);
    }

    @Operation(summary = "Find All By Title,or AuthorName,or PublishingHouse")
    @GetMapping("/findAll/{name}")
    public List<Book> findAll(@PathVariable String name) {
        return bookGetService.findAll(name);
    }

    @Operation(summary = "Find All By Sorted" , description = "Sorted By Genre, BookType, Price, Language")
    @PostMapping("/getBySort")
    public List<Book> getBook(@RequestBody SortBook sortBook) {
        return bookGetService.sortBooks(sortBook);
    }
}
