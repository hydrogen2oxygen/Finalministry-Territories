package net.hydrogen2oxygen.finalministry.services;

import net.hydrogen2oxygen.finalministry.jpa.Privilege;
import net.hydrogen2oxygen.finalministry.jpa.Role;
import net.hydrogen2oxygen.finalministry.jpa.User;
import net.hydrogen2oxygen.finalministry.jpa.repositories.PrivilegeRepository;
import net.hydrogen2oxygen.finalministry.jpa.repositories.RoleRepository;
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

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FmUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(FmUserDetailsService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public FmUserDetailsService(final UserRepository userRepository, final RoleRepository roleRepository, final PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
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

            Privilege superUserPrivilege = new Privilege();
            superUserPrivilege.setName("SUPERUSER");
            privilegeRepository.save(superUserPrivilege);

            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Collection<Privilege> privileges = new ArrayList<>();
            privileges.add(superUserPrivilege);
            adminRole.setPrivileges(privileges);

            roleRepository.save(adminRole);

            Collection<Role> adminRoles = new ArrayList<>();
            adminRoles.add(adminRole);
            adminUser.setRoles(adminRoles);

            userRepository.save(adminUser);

            user = adminUser;
        }
        return user;
    }

}
