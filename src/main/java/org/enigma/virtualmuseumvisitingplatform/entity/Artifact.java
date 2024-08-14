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
@Table(name = "artifact")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Artifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String era;
    private String dimensions;
    private String inventoryNumber;
    private String description;
    private int likeCount;
    private int dislikeCount;
    private boolean state = false;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;

    @ManyToOne
    @JoinColumn(name = "museum_id", nullable = false)
    private Museum museum;

    @OneToMany(mappedBy = "artifact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
}
