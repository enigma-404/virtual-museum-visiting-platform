package org.enigma.virtualmuseumvisitingplatform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "museum")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Museum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int likeCount;
    private int dislikeCount;
    private boolean state = false;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "museum", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Artifact> artifacts;

    @OneToMany(mappedBy = "museum", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
}
