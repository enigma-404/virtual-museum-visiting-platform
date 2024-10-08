package org.enigma.virtualmuseumvisitingplatform.repository.museum;

import org.enigma.virtualmuseumvisitingplatform.entity.museums.Museum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuseumRepository extends JpaRepository<Museum, Long> {
}

