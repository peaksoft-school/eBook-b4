package kg.peaksoft.ebookb4;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.userClasses.Role;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.RoleRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void init(){
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
        user.setPassword(encoder.encode("password"));
        user.setRole(roleRepository.getById(3L));
        userRepository.save(user);
    }

}
