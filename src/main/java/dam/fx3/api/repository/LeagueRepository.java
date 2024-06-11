package dam.fx3.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dam.fx3.modelo.entities.League;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Integer> {

    @Query(value = "select * from league l where l.accesscode = ?", nativeQuery = true)
    Optional<League> findByAccesscode(String accesscode);
}
