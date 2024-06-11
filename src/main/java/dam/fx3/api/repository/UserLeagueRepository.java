package dam.fx3.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dam.fx3.modelo.entities.Userleague;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserLeagueRepository extends JpaRepository<Userleague, Integer>{

    @Query(value = "select * from userleague u where u.idleague = ?1 ORDER BY u.puntuation DESC", nativeQuery = true)
    List<Userleague> findByLeagueId(int idleague);

    @Query(value = "select u.* from userleague u where u.iduser = ?1", nativeQuery = true)
    List<Userleague> findByUserId(int iduser);
    @Query(value = "select * from userleague u where u.iduser = ?1 and u.idleague = ?2", nativeQuery = true)
    Optional<Userleague> findByUserIdAndLeagueId(int iduser, int idleague);
}
