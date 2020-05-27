package fr.uca.cdr.skillful_network.services.application;

import fr.uca.cdr.skillful_network.entities.application.Application;

import java.util.List;

public interface ApplicationService<T extends Application> {

    T createOrUpdate(T application);

    List<T> getAll();

    T getById(long id);

    List<T> getByUserId(long userId);

    List<T> getByOfferId(long offerId);

    void delete(long id);

}
