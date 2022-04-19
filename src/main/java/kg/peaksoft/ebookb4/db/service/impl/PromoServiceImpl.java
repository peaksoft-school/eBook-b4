package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.others.Promocode;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.PromocodeRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.dto.mapper.PromoMapper;
import kg.peaksoft.ebookb4.dto.request.PromoRequest;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 19/4/22
 */
@Service
@AllArgsConstructor
public class PromoServiceImpl implements PromoService {

    private PromocodeRepository promoRepository;
    private UserRepository userRepository;
    private PromoMapper promoMapper;

    @Override
    public ResponseEntity<?> createPromo(PromoRequest promoRequest, String username) {
        User user = userRepository.getUser(username).orElseThrow(()->
                new NotFoundException(String.format("User with username: %s doesn't exist!",
                        username)));
        Promocode promo = promoMapper.create(promoRequest);
        promo.setUser(user);

        promoRepository.save(promo);

        return ResponseEntity.ok(new MessageResponse(
                String.format("Promo with promo_name %s has been saved",promoRequest.getPromoName())
        ));
    }
}
