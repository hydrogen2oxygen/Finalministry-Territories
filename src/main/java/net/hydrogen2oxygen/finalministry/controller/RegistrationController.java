package net.hydrogen2oxygen.finalministry.controller;

import net.hydrogen2oxygen.finalministry.dto.UserDto;
import net.hydrogen2oxygen.finalministry.jpa.User;
import net.hydrogen2oxygen.finalministry.jpa.repositories.UserRepository;
import net.hydrogen2oxygen.finalministry.services.EmailService;
import net.hydrogen2oxygen.finalministry.services.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("userRegistration")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public String showRegistrationForm(@RequestBody UserDto userDto) {

        String password = UUID.randomUUID().toString();

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(new BCryptPasswordEncoder().encode(password));

        userRepository.save(user);

        Mail mail = new Mail();
        mail.setFrom("territory@jw-software.org"); // TODO from settings + translations
        mail.setTo(userDto.getEmail());
        mail.setSubject("Registration at Finalministry Territories");
        mail.setContent("Dear " + userDto.getFirstName()
                + ",\n\nthanks for creating an account on Finalministry Territories.\n"
                + "Your automatically generated password is:\n\n"
                + password
                + "\n\nNote that your account still need to be activated by your territory manager.");

        emailService.sendSimpleMessage(mail);

        return "done";
    }
}
