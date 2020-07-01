package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.Role;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface RoleService {

    Role findByName(final Role.Name name);

    EnumSet<Role.Name> getRoles();

    HashMap<Role.Name,String> getInfoRoles();

    HashSet<String> getRoleDescription();


}
