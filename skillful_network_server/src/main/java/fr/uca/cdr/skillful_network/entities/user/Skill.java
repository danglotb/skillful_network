package fr.uca.cdr.skillful_network.entities.user;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Skill extends Perk {
    public Skill() {
        super();
    }

    public Skill(String name) {
        super(name);
    }
}
