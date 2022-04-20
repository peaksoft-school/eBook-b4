package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.others.Promocode;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.PromocodeRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.dto.mapper.PromoMapper;
import kg.peaksoft.ebookb4.dto.request.PromoRequest;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import kg.peaksoft.ebookb4.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

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
    @Transactional
    public ResponseEntity<?> createPromo(PromoRequest promoRequest, String username) {

        User user = userRepository.getUser(username).orElseThrow(()->
                new NotFoundException(String.format("User with username: %s doesn't exist!",
                        username)));

        System.out.println(promoRepository.ifVendorAlreadyCreatedPromo(user));

        if(promoRepository.ifVendorAlreadyCreatedPromo(user)){
            throw new BadRequestException("You already have an active promo!");
        }

        Promocode promo = promoMapper.create(promoRequest);
        System.out.println(Period.between(promo.getBeginningDay(),promo.getEndDay()).getDays());

        promo.setUser(user);
        promo.setIsActive(true);
        System.out.println(user);
        List<Book> vendorAddedBooks = user.getVendorAddedBooks();
        for (Book i: vendorAddedBooks) {
            if(i.getDiscount() == null){
                i.setDiscountFromPromo(promo.getDiscount());
            }
        }

        promoRepository.save(promo);

        return ResponseEntity.ok(new MessageResponse(
                String.format("Promo with promo_name %s has been saved",promoRequest.getPromoName())
        ));
    }




}
