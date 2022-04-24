package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.service.AdminService;
import kg.peaksoft.ebookb4.dto.response.ClientResponse;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Tag(name = "Admin",description = "Admin accessible apis")
public class AdminApi {

    private AdminService service;

    @Operation(summary = "Get all books" )
    @GetMapping("/getBooks/")
    public List<Book> getAllBook() {
        return service.getBooks();
    }

    @Operation(summary = "Get all by genre and book type",
            description = "Filter all books by genre and book type ")
    @GetMapping("/getBooksByBoth/{genre}/{bookType}")
    public List<Book> getBooksBy(@PathVariable Genre genre,
                                 @PathVariable BookType bookType) {
        return service.getBooksBy(genre, bookType);
    }

    @Operation(summary = "Get all by genre",
            description = "Filter all books only by genre ")
    @GetMapping("/getBooksByGenre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable Genre genre) {
        return service.getBooksByGenre(genre);
    }

    @Operation(summary = "Get all by book type",
            description = "Filter all books only by book type ")
    @GetMapping("/getBooksByType/{bookType}")
    public List<Book> getBooksByBookType(@PathVariable BookType bookType) {
        return service.getBooksByBookType(bookType);
    }

    @Operation(summary = "Get all Vendors",
            description = "Get all vendors with amount of books")
    @GetMapping("/getVendors")
    public List<VendorResponse> getAllVendors(){
       return service.findAllVendors();
    }

    @Operation(summary = "Delete client/vendor ",
            description = "Delete user by id ")
    @DeleteMapping({"/deleteById/{id}"})
    public ResponseEntity<?> deleteVendorById(@PathVariable("id") Long id) {
       return service.deleteById(id);
    }

    @Operation(summary = "Get all client",
            description = "Get all client ")
    @GetMapping("/getClients")
    public List<ClientResponse> getAllClient (){
        return service.findAllClient();
    }

    @GetMapping("/getVendorById/{id}")
    public VendorResponse getByVendorId(@PathVariable Long id){
        return service.getVendor(id);
    }

}
