package kg.peaksoft.ebookb4;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.others.Basket;
import kg.peaksoft.ebookb4.db.models.userClasses.Role;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.BasketRepository;
import kg.peaksoft.ebookb4.db.repository.RoleRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;

@RestController
@SpringBootApplication
@OpenAPIDefinition
@AllArgsConstructor
public class EBookB4Application {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(EBookB4Application.class, args);
        System.out.println("Welcome colleagues, project name is eBook!");
    }

//        @PostConstruct
//    public void init(){
//        Role client = new Role();
//        client.setId(1L);
//        client.setName(ERole.ROLE_CLIENT);
//        Role vendor = new Role();
//        vendor.setId(2L);
//        vendor.setName(ERole.ROLE_VENDOR);
//        Role admin = new Role();
//        admin.setId(3L);
//        admin.setName(ERole.ROLE_ADMIN);
//        roleRepository.save(client);
//        roleRepository.save(vendor);
//        roleRepository.save(admin);
//
//            User c = new User();
//            c.setEmail("client@gmail.com");
//            c.setPassword(encoder.encode("client"));
//            c.setRole(roleRepository.getById(1L));
//            Basket basket1 = new Basket();
//            basket1.setUser(c);
//            c.setBasket(basket1);
//            userRepository.save(c);
//
//
//            User v = new User();
//            v.setEmail("vendor@gmail.com");
//            v.setPassword(encoder.encode("vendor"));
//            v.setRole(roleRepository.getById(2L));
//            Basket basket2 = new Basket();
//            basket2.setUser(v);
//            v.setBasket(basket2);
//            userRepository.save(v);
//
//        User a = new User();
//            a.setEmail("admin@gmail.com");
//            a.setPassword(encoder.encode("password"));
//            a.setRole(roleRepository.getById(3L));
//        Basket basket = new Basket();
//        basket.setUser(a);
//            a.setBasket(basket);
//        userRepository.save(a);
//
//    }
}
