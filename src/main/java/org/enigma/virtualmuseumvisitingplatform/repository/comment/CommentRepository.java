package org.enigma.virtualmuseumvisitingplatform.repository.comment;

import org.enigma.virtualmuseumvisitingplatform.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArtifactId(Long artifactId);
}
