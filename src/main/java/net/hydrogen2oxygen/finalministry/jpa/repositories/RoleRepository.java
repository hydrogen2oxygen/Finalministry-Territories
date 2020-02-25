package net.hydrogen2oxygen.finalministry.jpa.repositories;

import net.hydrogen2oxygen.finalministry.jpa.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "role", path = "role")
public interface RoleRepository extends JpaRepository<Role, UUID> {
}
