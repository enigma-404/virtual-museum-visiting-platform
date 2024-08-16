package org.enigma.virtualmuseumvisitingplatform.repository.user;

import org.enigma.virtualmuseumvisitingplatform.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
