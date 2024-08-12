package org.enigma.virtualmuseumvisitingplatform.repository;

import org.enigma.virtualmuseumvisitingplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
