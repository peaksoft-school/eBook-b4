package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.booksClasses.FileSources;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/")
@AllArgsConstructor
@Tag(name = "Books", description = "Sort operations")
public class GlobalApi {

    @GetMapping("/vendor-sell")
    @Operation(summary = "Intro to become vendor", description = "This page to intro-e client or guest " +
            "to become vendor!")
    public List<FileSources> becomeVendor() {
        List<FileSources> files = new ArrayList<>();
        FileSources file = new FileSources();
        file.setLine1("В целом, конечно, экономическая повестка");
        file.setLine2("сегодняшнего дня прекрасно подходит для");
        file.setLine3("реализации переосмысления");
        file.setLine4("внешнеэкономических политик.");
        files.add(file);
        files.add(file);
        files.add(file);
        files.add(file);
        files.add(file);
        files.add(file);
        return files;
    }

}
