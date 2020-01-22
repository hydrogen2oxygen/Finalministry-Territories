package net.hydrogen2oxygen.finalministry.jpa.repositories;

import net.hydrogen2oxygen.finalministry.jpa.Territory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "territory", path = "territory")
public interface TerritoryRepository extends JpaRepository<Territory, UUID> {

    Territory findByNumber(String number);
    Territory findByName(String name);
}
