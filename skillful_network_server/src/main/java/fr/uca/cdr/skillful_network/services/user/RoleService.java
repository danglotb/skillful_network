package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.Role;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public interface RoleService {

    Role findByName(final Role.Name name);

    EnumSet<Role.Name> getRoles();

    Map<Role.Name,String> getInfoRoles();

    Set<String> getRoleDescription();


}
