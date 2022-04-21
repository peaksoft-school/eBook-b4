package kg.peaksoft.ebookb4.dto.mapper;

import kg.peaksoft.ebookb4.db.models.others.Promocode;
import kg.peaksoft.ebookb4.dto.request.PromoRequest;
import org.springframework.stereotype.Component;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 19/4/22
 */
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
