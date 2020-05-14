package fr.uca.cdr.skillful_network.repositories.simulation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.uca.cdr.skillful_network.request.*;

@Repository
public interface SimulationFormRepository extends JpaRepository<SimulationForm,Long>{

}
