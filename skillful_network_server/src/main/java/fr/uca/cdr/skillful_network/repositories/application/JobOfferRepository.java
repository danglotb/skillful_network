package fr.uca.cdr.skillful_network.repositories.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.uca.cdr.skillful_network.entities.application.JobOffer;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

	Page<JobOffer> findByNameContainsOrCompanyContainsAllIgnoreCase(Pageable pageable, String keyword1, String keyword2);

}
