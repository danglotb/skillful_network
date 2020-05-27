package fr.uca.cdr.skillful_network.repositories.application;

import fr.uca.cdr.skillful_network.entities.application.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository<T extends Application> {

    Optional<List<T>> findByUserId(long userId);

    Optional<List<T>> findByOfferId(long offerId);

}
