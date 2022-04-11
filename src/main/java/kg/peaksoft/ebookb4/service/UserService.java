package kg.peaksoft.ebookb4.service;

import kg.peaksoft.ebookb4.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser();

    void removeUserById(Long id);

    Optional<User> getById(Long id);


    List<User> getAllUser();

    User update(User user,Long id);

    User findById(Long id);
}

