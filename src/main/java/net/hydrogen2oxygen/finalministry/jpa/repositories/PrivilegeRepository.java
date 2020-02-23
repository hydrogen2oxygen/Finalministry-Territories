package net.hydrogen2oxygen.finalministry.jpa.repositories;

import net.hydrogen2oxygen.finalministry.jpa.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "privilege", path = "privilege")
public interface PrivilegeRepository extends JpaRepository<Privilege, UUID> {
}
