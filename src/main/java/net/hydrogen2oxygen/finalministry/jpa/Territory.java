package net.hydrogen2oxygen.finalministry.jpa;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Territory {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(unique=true)
    private String number;
    private String name;
    private String urlForGoogleMap;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlForGoogleMap() {
        return urlForGoogleMap;
    }

    public void setUrlForGoogleMap(String urlForGoogleMap) {
        this.urlForGoogleMap = urlForGoogleMap;
    }

    @Override
    public String toString() {
        return "Territory{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", urlForGoogleMap='" + urlForGoogleMap + '\'' +
                '}';
    }
}
