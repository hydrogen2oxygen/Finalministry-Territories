package net.hydrogen2oxygen.finalministry.services;

import net.hydrogen2oxygen.finalministry.jpa.User;
import net.hydrogen2oxygen.finalministry.jpa.repositories.UserRepository;
import net.hydrogen2oxygen.finalministry.security.FmUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FmUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(FmUserDetailsService.class);
    private final UserRepository userRepository;

    @Autowired
    public FmUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String userOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(userOrEmail);

        if (user == null) {
            user = userRepository.findByEmail(userOrEmail);
        }

        user = getSuperUser(userOrEmail, user);

        if (user == null) {
            throw new UsernameNotFoundException(userOrEmail);
        }

        return new FmUserDetails(user);
    }

    /**
     * Initial start of the server requires a superuser
     *
     * @param username
     * @param user
     * @return
     */
    private User getSuperUser(String username, User user) {
        if (user == null && "admin".equals(username)) {

            logger.info("First time using Finalministry-Territories. You have now a user and password admin/admin. Please change the password for this user and insert your own email address!");

            User adminUser = new User();
            adminUser.setEmail("toBeSet");
            adminUser.setUserName("admin");
            adminUser.setPassword(new BCryptPasswordEncoder().encode("admin"));
            adminUser.setRoles("ADMIN");

            userRepository.save(adminUser);

            user = adminUser;
        }
        return user;
    }

}
