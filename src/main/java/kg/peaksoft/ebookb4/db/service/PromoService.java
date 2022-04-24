package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.dto.dto.others.PromoRequest;
import org.springframework.http.ResponseEntity;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 19/4/22
 */
public interface PromoService {

    ResponseEntity<?> createPromo(PromoRequest promoRequest, String username);

    void checkPromos();

}
