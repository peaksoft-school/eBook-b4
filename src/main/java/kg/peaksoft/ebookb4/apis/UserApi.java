package kg.peaksoft.ebookb4.apis;

import kg.peaksoft.ebookb4.entities.User;
import kg.peaksoft.ebookb4.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserApi {

    private UserService userService;
@PostMapping("/save")
    public User createUser(@RequestBody User user){
        return userService.saveUser();

    }
    @PutMapping("update{}id")
    public User update(@RequestBody User user,@PathVariable("id")Long id){
    return  userService.update(user,id);
    }
    @DeleteMapping({"id"})
    public void deleteUserById(@PathVariable Long id){
    userService.removeUserById(id);

    }
    @GetMapping("/get")
    public List<User>getAllUser(){
    return userService.getAllUser();
    }

    @GetMapping("/get/{id}")
    private Optional<User>getById(@PathVariable("id")Long id){
    return userService.getById(id);
    }
}
