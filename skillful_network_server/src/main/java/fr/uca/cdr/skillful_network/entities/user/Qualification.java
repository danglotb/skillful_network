package fr.uca.cdr.skillful_network.entities.user;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Qualification extends Perk {

    public Qualification() {
        super();
    }

    public Qualification(String name) {
        super(name);
    }
}
