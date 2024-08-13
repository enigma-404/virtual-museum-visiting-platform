package org.enigma.virtualmuseumvisitingplatform.repository.role;

import org.enigma.virtualmuseumvisitingplatform.entity.role.ERole;
import org.enigma.virtualmuseumvisitingplatform.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
