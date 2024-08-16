package org.enigma.virtualmuseumvisitingplatform.repository.artifact;

import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.ArtifactPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtifactPictureRepository extends JpaRepository<ArtifactPicture, Long> {

    @Query("SELECT ap.url FROM ArtifactPicture ap WHERE  ap.artifact.id = :id")
    List<String> findUrlsByArtifactId(long id);
}
