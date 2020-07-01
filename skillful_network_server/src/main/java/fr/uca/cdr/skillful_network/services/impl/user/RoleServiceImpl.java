package fr.uca.cdr.skillful_network.services.impl.user;

import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.repositories.user.RoleRepository;
import fr.uca.cdr.skillful_network.services.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(final Role.Name name) {
        return this.roleRepository.findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None role could be found with the name %s", name))
        );
    }

    @Override
    public EnumSet<Role.Name> getRoles() {
        System.out.println("roles: " + EnumSet.allOf(Role.Name.class));
        return EnumSet.allOf(Role.Name.class);
    }

    @Override
    public HashMap<Role.Name, String> getInfoRoles() {
        return Role.getNamesAndDescriptions();
    }

    @Override
    public HashSet<String> getRoleDescription() {
        return Role.getRoleDescriptions();
    }

}
