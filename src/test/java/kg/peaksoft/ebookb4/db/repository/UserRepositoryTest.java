package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.others.Basket;
import kg.peaksoft.ebookb4.db.models.userClasses.Role;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void existsByEmail_whenGiveRole() {

            Role client = new Role();
            client.setId(1L);
            client.setName(ERole.ROLE_CLIENT);
            Role vendor = new Role();
            vendor.setId(2L);
            vendor.setName(ERole.ROLE_VENDOR);
            Role admin = new Role();
            admin.setId(3L);
            admin.setName(ERole.ROLE_ADMIN);
            roleRepository.save(client);
            roleRepository.save(vendor);
            roleRepository.save(admin);

            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setPassword("password");
            user.setRole(roleRepository.getById(3L));
            Basket basket = new Basket();
            basket.setUser(user);
            user.setBasket(basket);
            userRepository.save(user);


        Boolean exists = userRepository.existsByEmail("admin@gmail.com");
      assertThat(exists).isTrue();

    }
}