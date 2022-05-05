package kg.peaksoft.ebookb4.db.models.mappers;

import kg.peaksoft.ebookb4.db.models.request.PromoRequest;
import kg.peaksoft.ebookb4.db.models.booksClasses.Promocode;
import org.springframework.stereotype.Component;


@Component
public class PromoMapper {

    public Promocode create(PromoRequest promoRequest){
        Promocode promocode = new Promocode();

        promocode.setPromocode(promoRequest.getPromoName());
        promocode.setBeginningDay(promoRequest.getBeginningDay());
        promocode.setEndDay(promoRequest.getEndDay());
        promocode.setDiscount(promoRequest.getDiscount());

        return promocode;
    }

}
