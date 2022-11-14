package kg.peaksoft.ebookb4.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PromoRequest {

    private String promoName;

    private LocalDate beginningDay;

    private LocalDate endDay;

    private int discount;

}
