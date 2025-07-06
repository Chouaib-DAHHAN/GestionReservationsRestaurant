package res.restau.resres.repository;

import res.restau.resres.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import res.restau.resres.model.Role;

import java.util.List;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
     List<User> findByRolesContaining(Role role);
}