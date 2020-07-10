package fr.uca.cdr.skillful_network.services.impl.user;

import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.repositories.user.RoleRepository;
import fr.uca.cdr.skillful_network.services.user.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

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
        logger.debug("Roles : {}", EnumSet.allOf(Role.Name.class));
        return EnumSet.allOf(Role.Name.class);
    }

    @Override
    public Map<Role.Name, String> getInfoRoles() {
        return Role.getNamesAndDescriptions();
    }

    @Override
    public Set<String> getRoleDescription() {
        return Role.getRoleDescriptions();
    }

}
