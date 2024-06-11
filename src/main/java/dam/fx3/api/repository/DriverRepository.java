package dam.fx3.api.repository;


import dam.fx3.modelo.entities.Driverpoints;
import org.springframework.data.jpa.repository.JpaRepository;
import dam.fx3.modelo.entities.Driver;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DriverRepository extends JpaRepository<Driver, Integer>{

    @Query(value = """
                      SELECT d.*\s
                      FROM driver d\s
                      INNER JOIN userleague u\s
                      ON d.driver_number = u.driver1number\s
                      OR d.driver_number = u.driver2number\s
                      OR d.driver_number = u.driver3number\s
                      OR d.driver_number = u.driver4number\s
                      WHERE u.iduser = ?1\s
                      AND u.idleague = ?2""", nativeQuery = true)
    List<Driver> findByIdUserIdLeague(int userId, int leagueId);
}
