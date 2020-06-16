package fr.uca.cdr.skillful_network.entities.user;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Subscription extends Perk {
    public Subscription() {
        super();
    }

    public Subscription(String name) {
        super(name);
    }
}
