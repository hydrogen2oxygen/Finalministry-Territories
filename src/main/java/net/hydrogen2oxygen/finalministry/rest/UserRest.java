package net.hydrogen2oxygen.finalministry.rest;

import net.hydrogen2oxygen.finalministry.jpa.User;
import net.hydrogen2oxygen.finalministry.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

// TODO set for same domain only (CorsConfiguration)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("user")
public class UserRest {

    @Autowired
    private UserRepository userRepository;

    /**
     * For authentification, if it is possible to return the user, the basic authentification worked
     * @param user
     * @return
     */
    @RequestMapping("/authentication")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity getAllUsers() {
        List<User> userList = userRepository.findAll();
        return new ResponseEntity(userList, HttpStatus.OK);
    }
}
