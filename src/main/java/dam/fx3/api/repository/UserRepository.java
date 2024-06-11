package dam.fx3.api.repository;


import dam.fx3.modelo.entities.League;
import org.springframework.data.jpa.repository.JpaRepository;

import dam.fx3.modelo.entities.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByName(String name);

}
