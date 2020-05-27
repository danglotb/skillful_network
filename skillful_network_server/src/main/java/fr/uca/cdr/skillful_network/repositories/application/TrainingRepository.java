package fr.uca.cdr.skillful_network.repositories.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.uca.cdr.skillful_network.entities.application.Training;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

	Page<Training> findByNameContainsOrOrganizationContainsAllIgnoreCase(Pageable pageable, String keyword1, String keyword2);

}

