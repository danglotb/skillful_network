package fr.uca.cdr.skillful_network.request;

import fr.uca.cdr.skillful_network.entities.user.Role;

import javax.validation.constraints.Size;
import java.util.Set;

public class ConfirmationRegisterForm {
    @Size(min = 2, max = 20, message = "firstName must be between 2 and 20 characters")
    private String firstName;

    @Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters")
    private String lastName;

    private Set<Role> roleSet;

    private ConfirmationRegisterForm(){};

    public ConfirmationRegisterForm(@Size(min = 2, max = 20, message = "firstName must be between 2 and 20 characters") String firstName, @Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters") String lastName, Set<Role> roleSet) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleSet = roleSet;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }
}
