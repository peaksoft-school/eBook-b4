package kg.peaksoft.ebookb4.db.models.notEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomPage {

    private Long totalElements;

    private int totalPages;

    private int number;

    private int size;

}
