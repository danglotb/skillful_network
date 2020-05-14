package fr.uca.cdr.skillful_network.repositories.simulation;

import fr.uca.cdr.skillful_network.entities.simulation.Simulation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SimulationRepository extends JpaRepository<Simulation, Long>{

	Optional<List<Simulation>> findByUserId(Long userId);
    Optional<Simulation> findByExamId(Long examId);

}

