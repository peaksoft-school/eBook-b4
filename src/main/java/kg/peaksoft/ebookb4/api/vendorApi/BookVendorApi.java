package kg.peaksoft.ebookb4.api.vendorApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.dto.request.BookRequest;
import kg.peaksoft.ebookb4.db.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "Books",description = "crud operations")
public class BookVendorApi {

    private final BookService bookService;

    @Operation(summary = "save book",description = "add a new book to the store")
    @PostMapping("/saveBook")
    public ResponseEntity<?> saveBook(@RequestBody BookRequest bookRequest){
        return  bookService.register(bookRequest, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Operation(summary = "delete",description = "We can delete by id book in the store")
    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @Operation(summary = "update",description = "We can update or chage book here")
    @PatchMapping("/updateBook/{bookId}")
    public ResponseEntity<?> update(@RequestBody BookRequest request,
                                    @PathVariable Long bookId){
        return bookService.update(request,bookId);
    }

}
