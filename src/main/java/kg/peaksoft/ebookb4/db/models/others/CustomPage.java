package kg.peaksoft.ebookb4.db.models.others;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 20/4/22
 */
@Data
@AllArgsConstructor
public class CustomPage {

    private Long totalElements;

    private int totalPages;

    private int number;

    private int size;

}
