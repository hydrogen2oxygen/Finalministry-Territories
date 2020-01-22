package net.hydrogen2oxygen.finalministry.jpa;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Minister {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(unique=true)
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Minister{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
