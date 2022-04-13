package kg.peaksoft.ebookb4;

import kg.peaksoft.ebookb4.models.userClasses.ERole;
import kg.peaksoft.ebookb4.models.userClasses.Role;
import kg.peaksoft.ebookb4.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@AllArgsConstructor
public class EBookB4Application {

    private final RoleRepository roleRepository;

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
    }

}
