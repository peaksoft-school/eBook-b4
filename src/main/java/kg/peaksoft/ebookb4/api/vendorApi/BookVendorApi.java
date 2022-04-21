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

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
@PreAuthorize("hasRole('ROLE_VENDOR')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Books API", description = "Crud operations")
public class BookVendorApi {

    private final BookService bookService;

    @Operation(summary = "Add book", description = "This endpoint will save a new book")
    @PostMapping("")
    public ResponseEntity<?> saveBook(@RequestBody BookRequest bookRequest) {
        return bookService.register(bookRequest, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Operation(summary = "Delete book", description = "This endpoint will delete existing book")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @Operation(summary = "Update book", description = "This endpoint will update existing book")
    @PatchMapping("/{bookId}")
    public ResponseEntity<?> update(@RequestBody BookRequest request,
                                    @PathVariable Long bookId) {
        return bookService.update(request, bookId);
    }

}
