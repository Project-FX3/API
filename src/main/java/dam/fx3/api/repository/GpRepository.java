package dam.fx3.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dam.fx3.modelo.entities.Gp;
import org.springframework.data.jpa.repository.query.Procedure;
import java.util.Optional;


public interface GpRepository extends JpaRepository<Gp, Integer> {
    @Procedure(name = "getnextgp")
    Optional<Gp> getNextGp();
}
