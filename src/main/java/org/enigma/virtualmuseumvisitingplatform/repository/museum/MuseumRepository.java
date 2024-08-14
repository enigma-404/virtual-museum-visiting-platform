package org.enigma.virtualmuseumvisitingplatform.repository.museum;

import org.enigma.virtualmuseumvisitingplatform.entity.Museum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuseumRepository extends JpaRepository<Museum, Long> {
    @Query("SELECT m FROM Museum m WHERE m.state = false")
    List<Museum> findAllActive();
}

