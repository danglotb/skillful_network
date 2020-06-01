package fr.uca.cdr.skillful_network.services.user;

import fr.uca.cdr.skillful_network.entities.user.Perk;

import java.util.List;

public interface PerkService<T extends Perk> {

    T createOrUpdate(String perk);
    List<T> getAll();
    List<T> getCandidates(String keyword);
    T getById(long id);
    T getByName(String name);
    void delete(long id);

}
