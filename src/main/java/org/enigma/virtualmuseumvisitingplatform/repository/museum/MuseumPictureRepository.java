package org.enigma.virtualmuseumvisitingplatform.repository.museum;

import org.enigma.virtualmuseumvisitingplatform.entity.museums.MuseumPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MuseumPictureRepository extends JpaRepository<MuseumPicture, Long> {

    @Query("SELECT mp.url FROM MuseumPicture mp WHERE mp.museum.id = :museumId")
    List<String> findUrlsByMuseumId(long museumId);
}
