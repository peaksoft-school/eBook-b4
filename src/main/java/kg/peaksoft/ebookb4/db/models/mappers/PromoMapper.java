package kg.peaksoft.ebookb4.db.models.mappers;

import kg.peaksoft.ebookb4.db.models.request.PromoRequest;
import kg.peaksoft.ebookb4.db.models.booksClasses.PromoCode;
import org.springframework.stereotype.Component;

@Component
public class PromoMapper {

    public PromoCode create(PromoRequest promoRequest) {
        PromoCode promocode = new PromoCode();
        promocode.setPromoCode(promoRequest.getPromoName());
        promocode.setBeginningDay(promoRequest.getBeginningDay());
        promocode.setEndDay(promoRequest.getEndDay());
        promocode.setDiscount(promoRequest.getDiscount());
        return promocode;
    }

}
