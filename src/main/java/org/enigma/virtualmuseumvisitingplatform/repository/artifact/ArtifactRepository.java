package org.enigma.virtualmuseumvisitingplatform.repository.artifact;

import org.enigma.virtualmuseumvisitingplatform.entity.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long> {
    @Query("SELECT a FROM Artifact a WHERE a.state = false")
    List<Artifact> findAllActive();
}

