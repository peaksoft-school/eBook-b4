package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.dto.request.PromoRequest;
import org.springframework.http.ResponseEntity;

public interface PromoService {

    ResponseEntity<?> createPromo(PromoRequest promoRequest, String username);

    void checkPromos();

}
