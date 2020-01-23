package net.hydrogen2oxygen.finalministry.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class TerritoryEntry {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private Boolean congregationPool;
    private Boolean archived;
    private UUID ministerID;
    private UUID territoryID;
    private String territoryNumber;
    private String territoryName;
    private String ministerName;
    private LocalDateTime registered;

    public static TerritoryEntry generateForCongregationPool(Territory territory) {
        TerritoryEntry entry = new TerritoryEntry();
        entry.setCongregationPool(true);
        entry.setArchived(false);
        entry.setRegistered(LocalDateTime.now());
        entry.setTerritoryID(territory.getId());
        entry.setTerritoryName(territory.getName());
        entry.setTerritoryNumber(territory.getNumber());
        return entry;
    }

    public static TerritoryEntry generateForMinister(Territory territory, Minister minister) {
        TerritoryEntry entry = new TerritoryEntry();
        entry.setCongregationPool(false);
        entry.setArchived(false);
        entry.setRegistered(LocalDateTime.now());
        entry.setTerritoryID(territory.getId());
        entry.setTerritoryName(territory.getName());
        entry.setTerritoryNumber(territory.getNumber());
        entry.setMinisterID(minister.getId());
        entry.setMinisterName(minister.getName());
        return entry;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getCongregationPool() {
        return congregationPool;
    }

    public void setCongregationPool(Boolean congregationPool) {
        this.congregationPool = congregationPool;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public UUID getMinisterID() {
        return ministerID;
    }

    public void setMinisterID(UUID ministerID) {
        this.ministerID = ministerID;
    }

    public UUID getTerritoryID() {
        return territoryID;
    }

    public void setTerritoryID(UUID territoryID) {
        this.territoryID = territoryID;
    }

    public String getTerritoryNumber() {
        return territoryNumber;
    }

    public void setTerritoryNumber(String territoryNumber) {
        this.territoryNumber = territoryNumber;
    }

    public String getTerritoryName() {
        return territoryName;
    }

    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    public String getMinisterName() {
        return ministerName;
    }

    public void setMinisterName(String ministerName) {
        this.ministerName = ministerName;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "TerritoryEntry{" +
                "id=" + id +
                ", congregationPool=" + congregationPool +
                ", archived=" + archived +
                ", ministerID=" + ministerID +
                ", territoryID=" + territoryID +
                ", territoryNumber='" + territoryNumber + '\'' +
                ", territoryName='" + territoryName + '\'' +
                ", ministerName='" + ministerName + '\'' +
                ", registered=" + registered +
                '}';
    }
}
