package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.Role;

public interface RoleService {

    Role findByName(final Role.Name name);

}
