package kg.peaksoft.ebookb4.api;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import kg.peaksoft.ebookb4.dto.PriseDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookGetApi {

    private final BookGetService bookGetService;
    private final BookRepository bookRepository;

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR','ROLE_CLIENT')")
    @GetMapping("/audioBook")
    public List<Book> findAllAudioBook() {
        return bookGetService.bookGetAudio();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR','ROLE_CLIENT')")
    @GetMapping("/paperBook")
    public List<Book> findAllPaperBook() {
        return bookGetService.bookGetPaper();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR','ROLE_CLIENT')")
    @GetMapping("/eBook")
    public List<Book> findAllEbook() {
        return bookGetService.bookGetElectronic();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR','ROLE_CLIENT')")
    @GetMapping("/genre/{genre}")
    public List<Book> findAllByGenre(@PathVariable Genre genre) {
        return bookRepository.findByGenre(genre);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR','ROLE_CLIENT')")
    @GetMapping("/bookType/{bookType}")
    public List<Book> findAllByBookType(@PathVariable BookType bookType) {
        return bookGetService.findByBookType(bookType);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR','ROLE_CLIENT')")
    @GetMapping("/language/{language}")
    public List<Book> findAllByLanguage(@PathVariable Language language) {
        return bookGetService.findByLanguage(language);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR','ROLE_CLIENT')")
    @GetMapping("/prise")
    public List<Book> findAllByPrice(@RequestBody PriseDto priseDto) {
        return bookGetService.getByPrice(priseDto.getMin(), priseDto.getMax());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR','ROLE_CLIENT')")
    @GetMapping("/findAll/{name}")
    public List<Book> findAll(@PathVariable String name) {
        return bookGetService.findAll(name);
    }

}
