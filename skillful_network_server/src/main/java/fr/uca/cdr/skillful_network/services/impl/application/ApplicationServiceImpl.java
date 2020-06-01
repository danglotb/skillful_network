package fr.uca.cdr.skillful_network.services.impl.application;

import fr.uca.cdr.skillful_network.entities.application.Application;
import fr.uca.cdr.skillful_network.repositories.application.ApplicationRepository;
import fr.uca.cdr.skillful_network.services.application.ApplicationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

public abstract class ApplicationServiceImpl<T extends Application> implements ApplicationService<T> {

    protected JpaRepository<T, Long> repository;

    public ApplicationServiceImpl(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public T create(T application) {
        return this.repository.save(application);
    }

    @Override
    public T update(long id, Application.ApplicationStatus status) {
        final T application = this.getById(id);
        application.setStatus(status);
        return application;
    }

    @Override
    public List<T> getAll() {
        return this.repository.findAll();
    }

    @Override
    public T getById(long id) {
        return this.repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None job application could be found with the id %d", id))
        );
    }

    // TODO idk what it is the best: throwing a ResponseStatusException or returning a empty list
    @Override
    public List<T> getByUserId(long id) {
        return ((ApplicationRepository<T>)this.repository).findByUserId(id).orElse(Collections.emptyList());
    }

    @Override
    public List<T> getByOfferId(long id) {
        return ((ApplicationRepository<T>)this.repository).findByOfferId(id).orElse(Collections.emptyList());
    }

    @Override
    public void delete(long id) {
        this.repository.deleteById(id);
    }

}
