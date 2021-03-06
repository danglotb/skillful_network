package fr.uca.cdr.skillful_network.services.impl.user;

import fr.uca.cdr.skillful_network.entities.user.Perk;
import fr.uca.cdr.skillful_network.services.user.PerkService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class PerkServiceImpl<T  extends Perk> implements PerkService<T> {

    protected JpaRepository<T, Long> repository;

    protected String kindPerk;

    public PerkServiceImpl(JpaRepository<T, Long> repository, String kindPerk) {
        this.repository = repository;
        this.kindPerk = kindPerk;
    }

    @Override
    public Set<T> createNewAndUpdateGivenSet(Set<T> perks) {
        final Set<T> perksWithNew = new HashSet<>();
        perks.forEach(perk -> {
            if (perk.getId() >= 0) {
                perksWithNew.add(perk);
            } else {
                perksWithNew.add(this.createOrUpdate(perk.getName()));
            }
        });
        return perksWithNew;
    }

    @Override
    public List<T> getAll() {
        return this.repository.findAll();
    }

    @Override
    public T getById(long id) {
        return this.repository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("None " + this.getKindPerk() +" could be found with the id %d", id)));
    }

    @Override
    public void delete(long id) {
        this.repository.deleteById(id);
    }

    protected String getKindPerk() {
        return this.kindPerk;
    }

}
