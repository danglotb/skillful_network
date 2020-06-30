package fr.uca.cdr.skillful_network.controller.user;

import fr.uca.cdr.skillful_network.entities.user.Role;
import fr.uca.cdr.skillful_network.services.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    public RoleService roleService;

    @GetMapping("")
    public ResponseEntity<HashMap<Role.Name,String>> getRoles(){
        System.out.println(roleService.getInfoRoles());
        return new ResponseEntity<>(roleService.getInfoRoles(), HttpStatus.OK);
    }

    @GetMapping("/description")
    public ResponseEntity<HashSet<String>>getRolesDescription(){
        System.out.println(roleService.getRoleDescription());
        return new ResponseEntity<>(roleService.getRoleDescription(), HttpStatus.OK);
    }

}
