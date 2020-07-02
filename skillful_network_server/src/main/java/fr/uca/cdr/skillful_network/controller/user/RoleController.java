package fr.uca.cdr.skillful_network.controller.user;

import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.services.user.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/roles")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    public RoleService roleService;

    @GetMapping("")
    public ResponseEntity<Map<Role.Name,String>> getRoles(){
        logger.debug("Roles found : {}", roleService.getInfoRoles());
        return new ResponseEntity<>(roleService.getInfoRoles(), HttpStatus.OK);
    }

    @GetMapping("/description")
    public ResponseEntity<Set<String>>getRolesDescription(){
       logger.debug("Descriptions found : {}", roleService.getRoleDescription());
        return new ResponseEntity<>(roleService.getRoleDescription(), HttpStatus.OK);
    }

}
