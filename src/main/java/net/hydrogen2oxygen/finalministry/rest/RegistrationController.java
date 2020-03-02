package net.hydrogen2oxygen.finalministry.rest;

import net.hydrogen2oxygen.finalministry.dto.UserDto;
import net.hydrogen2oxygen.finalministry.jpa.User;
import net.hydrogen2oxygen.finalministry.jpa.repositories.UserRepository;
import net.hydrogen2oxygen.finalministry.services.EmailService;
import net.hydrogen2oxygen.finalministry.services.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("userRegistration")
public class RegistrationController {

    private static Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping
    @ResponseBody
    public ResponseEntity userRegistration(@RequestBody UserDto userDto) {

        String password = UUID.randomUUID().toString();
        logger.info(userDto.toString());
        logger.info(password);

        try {

            User user = new User();

            user.setUserName(userDto.getUserName());
            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setRegistrationDate(Calendar.getInstance());

            userRepository.save(user);

        } catch (DataIntegrityViolationException e) {
            logger.error("User already exists", e);
            return new ResponseEntity("User already exists!", HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while creating user!", e);
            return new ResponseEntity("Error while creating user!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            Mail mail = new Mail();
            mail.setFrom("territory@jw-software.org"); // TODO from settings + translations
            mail.setTo(userDto.getEmail());
            mail.setSubject("Registration at Finalministry Territories");
            mail.setContent("Dear " + userDto.getFirstName()
                    + ",\n\nthanks for creating an account on Finalministry Territories.\n"
                    + "Your automatically generated password is:\n\n"
                    + password
                    + "\n\nNote that your account still needs to be activated by your territory manager.");

            emailService.sendSimpleMessage(mail);
        } catch (Exception e) {
            logger.error("Could not send the password via email!", e);
            return new ResponseEntity("Could not send the password via email!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
