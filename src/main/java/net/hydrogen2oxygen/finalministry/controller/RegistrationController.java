package net.hydrogen2oxygen.finalministry.controller;

import net.hydrogen2oxygen.finalministry.dto.UserDto;
import net.hydrogen2oxygen.finalministry.jpa.User;
import net.hydrogen2oxygen.finalministry.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("userRegistration")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String showRegistrationForm(@RequestBody UserDto userDto) {

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(UUID.randomUUID().toString());

        // TODO email password to user

        userRepository.save(user);

        return "done";
    }
}
