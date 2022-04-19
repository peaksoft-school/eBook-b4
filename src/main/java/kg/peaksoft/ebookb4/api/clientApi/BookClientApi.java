package kg.peaksoft.ebookb4.api.clientApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@PermitAll
@Tag(name = "Books",description = "crud operations")
public class BookClientApi {

    private final BookService bookService;


    @Operation(summary = "We can find by id",description = "We can find by id in the store the book")
    @GetMapping("/getBook/{id}")
    public Book findById(@PathVariable Long id){
        return bookService.findByBookId(id);
    }

    @Operation(summary = "find All",description = "We can find all books in the store")
    @GetMapping("/getBooks")
    public List<Book> findAll(){
        return bookService.findAll();
    }


}
