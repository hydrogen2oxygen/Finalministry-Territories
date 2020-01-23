package net.hydrogen2oxygen.finalministry.jpa.repositories;

import net.hydrogen2oxygen.finalministry.jpa.TerritoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "territoryEntry", path = "territoryEntry")
public interface TerritoryEntryRepository extends JpaRepository<TerritoryEntry, UUID> {

    List<TerritoryEntry> findByTerritoryNumber(String number);
    List<TerritoryEntry> findByTerritoryName(String name);
    List<TerritoryEntry> findByMinisterName(String ministerName);
    List<TerritoryEntry> findByMinisterID(String ministerID);
}
