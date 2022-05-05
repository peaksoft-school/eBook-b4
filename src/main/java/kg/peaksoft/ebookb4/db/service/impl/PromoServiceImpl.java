package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.booksClasses.Promocode;
import kg.peaksoft.ebookb4.db.models.entity.User;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.repository.PromocodeRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.db.models.entity.dto.mapper.PromoMapper;
import kg.peaksoft.ebookb4.db.models.entity.dto.request.PromoRequest;
import kg.peaksoft.ebookb4.db.models.entity.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import kg.peaksoft.ebookb4.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PromoServiceImpl implements PromoService {

    private PromocodeRepository promoRepository;
    private UserRepository userRepository;
    private PromoMapper promoMapper;
    private BookRepository bookRepository;

    @Override
    @Transactional
    public ResponseEntity<?> createPromo(PromoRequest promoRequest, String username) {
        //Getting authenticated vendor from db if exists
        User user = userRepository.getUser(username).orElseThrow(() ->
                new NotFoundException(String.format("User with username: %s doesn't exist!",
                        username)));

        //If vendor already have active will be a bad request
        if (promoRepository.ifVendorAlreadyCreatedPromo(user, LocalDate.now())) {
            throw new BadRequestException("You already have a promo that is not expired yet!");
        }

        //creating promo
        Promocode promo = promoMapper.create(promoRequest);
        if (Period.between(promo.getBeginningDay(), promo.getEndDay()).getDays() < 0) {
            throw new BadRequestException("You entered invalid date!");
        }

        promo.setUser(user);
        if (Period.between(LocalDate.now(), promo.getBeginningDay()).getDays() <= 0) {
            log.info("Hello world");
            promo.setIsActive(true);
        } else {
            promo.setIsActive(false);
        }
        promoRepository.save(promo);
        log.info("Create promo works");
        return ResponseEntity.ok(new MessageResponse(
                String.format("Promo with promo_name %s has been saved", promoRequest.getPromoName())
        ));
    }


    public void checkPromos() {
        List<Promocode> promos = promoRepository.getPromos().orElseThrow(() ->
                new BadRequestException("There are no promo codes yes!"));
        log.info("Promocode size: " + promos.size());
        for (Promocode i : promos) {
            if (Period.between(i.getBeginningDay(), i.getEndDay()).getDays() < 0) {
                log.info("Срок прошёл!");
                i.setIsActive(false);
                bookRepository.checkForPromos(i.getUser());
            } else {
                if (Period.between(LocalDate.now(), i.getBeginningDay()).getDays() <= 0) {
                    i.setIsActive(true);
                }
                log.info("Срок ещё не прошёл!");
                if (i.getIsActive()) {
                    bookRepository.givePromo(i.getUser(), i.getDiscount());
                }
            }
        }
    }
}
