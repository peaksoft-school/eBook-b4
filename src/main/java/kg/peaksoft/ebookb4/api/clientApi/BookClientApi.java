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


    @Operation(summary = "book by id",description = "Get book by id")
    @GetMapping("/getBook/{id}")
    public Book findById(@PathVariable Long id){
        return bookService.findByBookId(id);
    }

    @Operation(summary = "get all",description = "Get all books in the store")
    @GetMapping("/getBooks")
    public List<Book> findAll(){
        return bookService.findAll();
    }


}
