package fr.uca.cdr.skillful_network.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@MappedSuperclass
public abstract class Perk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @NotNull(message = "Subscription name cannot be null")
    @Size(min = 2, max = 20, message = "Subscription name must be between 3 and 20 characters")
    @Column(name = "name", nullable = false)
    protected String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "subscriptionSet")
    @JsonIgnore
    protected Set<User> userList = new HashSet<User>();

    public Perk() {
        super();
    }

    public Perk(@NotNull(message = "Subscription name cannot be null") @Size(min = 2, max = 20, message = "Subscription name must be between 3 and 20 characters") String name) {
        this.name = name;
    }

    private Perk(@NotNull(message = "Subscription name cannot be null") @Size(min = 2, max = 20, message = "Subscription name must be between 3 and 20 characters") String name,
                 Set<User> userList) {
        this.name = name;
        this.userList = userList;
    }

    private Perk(long id,
                 @NotNull(message = "Subscription name cannot be null") @Size(min = 2, max = 20, message = "Subscription name must be between 3 and 20 characters") String name,
                 Set<User> userList) {
        this.id = id;
        this.name = name;
        this.userList = userList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, this.getClass().getName());
    }

    // TODO Maybe it does not work because of they share the common super class
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perk that = (Perk) o;
        return id == that.id;
    }

}
