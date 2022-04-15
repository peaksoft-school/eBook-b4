package kg.peaksoft.ebookb4.api;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.dto.request.BookRequest;
import kg.peaksoft.ebookb4.db.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@RolesAllowed("ROLE_VENDOR")
public class BookApi {

    private final BookService bookService;

    @PostMapping("{userId}")
    public ResponseEntity<?> saveBook(@RequestBody BookRequest bookRequest,
                                      @PathVariable("userId") Long userId){
        return  bookService.register(bookRequest, userId);
    }

    @GetMapping("{id}")
    public Book findById(@PathVariable Long id){
        return bookService.findByBookId(id);
    }


    @GetMapping
    public List<Book> findAll(){
        return bookService.findAll();
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @PatchMapping("{bookId}")
    public ResponseEntity<?> update(@RequestBody BookRequest request,
                                    @PathVariable Long bookId){
        return bookService.update(request,bookId);
    }

}
