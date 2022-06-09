package kg.peaksoft.ebookb4;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import kg.peaksoft.ebookb4.db.models.booksClasses.Basket;
import kg.peaksoft.ebookb4.db.models.entity.Genre;
import kg.peaksoft.ebookb4.db.models.entity.PlaceCounts;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.entity.Role;
import kg.peaksoft.ebookb4.db.models.entity.User;
import kg.peaksoft.ebookb4.db.repository.GenreRepository;
import kg.peaksoft.ebookb4.db.repository.RoleRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@RestController
@SpringBootApplication
@OpenAPIDefinition
@AllArgsConstructor
public class EBookB4Application {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final GenreRepository genreRepository;

    public static void main(String[] args) {
        SpringApplication.run(EBookB4Application.class, args);
        System.out.println("Welcome colleagues, project name is eBook!");

    }

    @GetMapping("/")
    public String greetingPage(){
        return "<h1>Welcome to eBook application!!!<h1/>";
    }

    @PostConstruct
    public void init() {
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

        User c = new User();
        c.setEmail("client@gmail.com");
        c.setFirstName("Client");
        c.setPassword(encoder.encode("client"));
        c.setRole(roleRepository.getById(1L));
        PlaceCounts placeCounts = new PlaceCounts(null, 0.0,0.0,0.0,0,0,0, 0.0, 0.0, 0.0, 0.0);
        c.setPlaceCounts(placeCounts);
        Basket basket1 = new Basket();
        basket1.setUser(c);
        c.setBasket(basket1);
        c.setDateOfRegistration(LocalDate.now());
        userRepository.save(c);

        User v = new User();
        v.setEmail("vendor@gmail.com");
        v.setFirstName("Vendor");
        v.setPassword(encoder.encode("vendor"));
        v.setRole(roleRepository.getById(2L));
        v.setDateOfRegistration(LocalDate.now());
        userRepository.save(v);

        User a = new User();
        a.setEmail("admin@gmail.com");
        a.setFirstName("Admin");
        a.setPassword(encoder.encode("admin"));
        a.setRole(roleRepository.getById(3L));
        a.setDateOfRegistration(LocalDate.now());
        userRepository.save(a);

        Genre journal=new Genre(null,"JOURNAL", "Журнал");
        Genre romance=new Genre(null,"ROMANCE", "Роман");
        Genre fantasy=new Genre(null,"FANTASY","Фантастика");
        Genre detective=new Genre(null,"DETECTIVE", "Детектив");
        Genre scientific=new Genre(null,"SCIENTIFIC", "Научные");
        Genre adventure=new Genre(null,"ADVENTURE", "Приключение");
        Genre internationalLiterature=new Genre(null,"INTERNATIONAL LITERATURE","Международная литература" );
        Genre biography=new Genre(null,"BIOGRAPHY", "Биография");
        Genre poetry =new Genre(null,"POETRY","Поэзия");
        Genre horror=new Genre(null,"HORROR", "Ужас");
        Genre comics = new Genre(null, "COMICS", "Комиксы");
        Genre manga = new Genre(null,"MANGA", "Манга");
        Genre thriller = new Genre(null, "THRILLER", "Триллер");
        Genre prose = new Genre(null, "PROSE", "Проза");
        Genre businessLiterature = new Genre(null,"BUSINESS LITERATURE", "Бизнес литература");
        Genre psychology = new Genre(null, "PSYCHOLOGY", "Психология");
        Genre art = new Genre(null, "ART", "Исскуство");
        Genre culture = new Genre(null, "CULTURE", "Культура");
        Genre computerLiterature = new Genre(null, "COMPUTER LITERATURE", "Компьютерная литература");
        Genre history = new Genre(null, "HISTORY","История");
        Genre society = new Genre(null, "SOCIETY", "Общество");
        Genre health = new Genre(null, "HEALTH","Здоровье");
        Genre schoolchildren = new Genre(null, "SCHOOLCHILDREN", "Детская литература");
        Genre folklore = new Genre(null, "FOLKLORE", "Фольклор");
        Genre hobby = new Genre(null, "HOBBY", "Хобби");
        Genre humor = new Genre(null, "HUMOR", "Юмор");
        Genre technic = new Genre(null, "TECHNIC", "Технология");


        genreRepository.save(journal);
        genreRepository.save(romance);
        genreRepository.save(fantasy);
        genreRepository.save(detective);
        genreRepository.save(scientific);
        genreRepository.save(adventure);
        genreRepository.save(internationalLiterature);
        genreRepository.save(biography);
        genreRepository.save(poetry);
        genreRepository.save(horror);
        genreRepository.save(comics);
        genreRepository.save(manga);
        genreRepository.save(thriller);
        genreRepository.save(prose);
        genreRepository.save(businessLiterature);
        genreRepository.save(psychology);
        genreRepository.save(art);
        genreRepository.save(culture);
        genreRepository.save(computerLiterature);
        genreRepository.save(history);
        genreRepository.save(society);
        genreRepository.save(health);
        genreRepository.save(schoolchildren);
        genreRepository.save(folklore);
        genreRepository.save(hobby);
        genreRepository.save(humor);
        genreRepository.save(technic);

    }
}
