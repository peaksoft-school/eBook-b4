package kg.peaksoft.ebookb4.api;

import kg.peaksoft.ebookb4.dto.request.BookRequest;
import kg.peaksoft.ebookb4.models.bookClasses.Book;
import kg.peaksoft.ebookb4.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class AudioBookApi {

    private final BookService bookService;

    @PostMapping("/save/{userId}")
    @PreAuthorize("hasRole('ROLE_VENDOR')")
    public ResponseEntity<?> saveBook(@RequestBody BookRequest bookRequest,
                                      @PathVariable("userId") Long userId){
        return  bookService.register(bookRequest, userId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR')")
    @GetMapping("/getById/{id}")
    public Book findById(@PathVariable Long id){
        return bookService.findByBookId(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR')")
    @GetMapping("/getAll")
    public List<Book> findAll(){
        return bookService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR')")
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR')")
    @GetMapping("/update/{bookId}")
    public ResponseEntity<?> update(@RequestBody BookRequest request,
                                    @PathVariable Long bookId){
        return bookService.update(request,bookId);
    }


}
