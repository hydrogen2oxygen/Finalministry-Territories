package net.hydrogen2oxygen.finalministry.rest;

import net.hydrogen2oxygen.finalministry.enums.UserRoles;
import net.hydrogen2oxygen.finalministry.jpa.User;
import net.hydrogen2oxygen.finalministry.jpa.repositories.UserRepository;
import net.hydrogen2oxygen.finalministry.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("user")
public class UserRest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;

    /**
     * For authentification, if it is possible to return the user, the basic authentification worked
     *
     * @param user
     * @return
     */
    @RequestMapping("/authentication")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/current")
    @ResponseBody
    public ResponseEntity<User> getCurrentUser() {
        User user = sessionService.getUser();

        if (sessionService.hasRole(UserRoles.DEACTIVATED.name())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity getAllUsers() {

        if (!sessionService.hasRole(UserRoles.ADMIN.name())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        List<User> userList = userRepository.findAll();
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    @PutMapping("/{userName}/password/{password}")
    @ResponseBody
    public ResponseEntity changeUserPassword(@PathVariable String userName, @PathVariable String password) {

        User user = userRepository.findByUserName(userName);
        User currentUser = sessionService.getUser();

        if (currentUser.getUserName().equals(user.getUserName()) || user.getRoles().contains("ADMIN")) {
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            userRepository.save(user);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{userName}/email/{email}")
    @ResponseBody
    public ResponseEntity changeUserEmail(@PathVariable String userName, @PathVariable String email) {

        User user = userRepository.findByUserName(userName);
        User currentUser = sessionService.getUser();

        if (currentUser.getUserName().equals(user.getUserName()) || user.getRoles().contains("ADMIN")) {
            user.setEmail(email);
            userRepository.save(user);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity changeUserDetails(@RequestBody User user) {

        if (!sessionService.hasRole(UserRoles.ADMIN.name())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        User persistedUser = userRepository.save(user);
        return new ResponseEntity(persistedUser, HttpStatus.OK);
    }

    @PostMapping("/validate")
    @ResponseBody
    public ResponseEntity validateNewUser(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity("EMAIL ALREADY EXISTS", HttpStatus.CONFLICT);
        } else if (userRepository.findByUserName(user.getUserName()) != null) {
            return new ResponseEntity("USER ALREADY EXISTS", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity addNewUser(@RequestBody User user) {

        if (!sessionService.hasRole(UserRoles.ADMIN.name())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User persistedUser = userRepository.save(user);
        return new ResponseEntity(persistedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity deleteUser(@PathVariable String id) {

        if (!sessionService.hasRole(UserRoles.ADMIN.name())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        userRepository.deleteById(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
