package net.hydrogen2oxygen.finalministry.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

// TODO set for same domain only
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserRest {

    /**
     * For authentification, if it is possible to return the user, the basic authentification worked
     * @param user
     * @return
     */
    @RequestMapping("/authentication")
    public Principal user(Principal user) {
        return user;
    }
}
