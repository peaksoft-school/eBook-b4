package kg.peaksoft.ebookb4.db.models.entity.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 19/4/22
 */
@Getter
@Setter
public class PromoRequest {

    private String promoName;
    private LocalDate beginningDay;
    private LocalDate endDay;
    private int discount;

}
