package fr.uca.cdr.skillful_network.entities.user;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Skill extends Perk {
    public Skill() {
        super();
    }

    public Skill(@NotNull(message = "Subscription name cannot be null") @Size(min = 2, max = 20, message = "Subscription name must be between 3 and 20 characters") String name) {
        super(name);
    }
}
