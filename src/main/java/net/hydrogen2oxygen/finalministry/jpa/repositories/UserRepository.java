package net.hydrogen2oxygen.finalministry.jpa.repositories;

import net.hydrogen2oxygen.finalministry.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserName(String userName);
    User findByEmail(String email);
}
