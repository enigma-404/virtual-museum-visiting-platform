package org.enigma.virtualmuseumvisitingplatform.dto.response.artifact;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtifactResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private int likeCount;
    private int dislikeCount;
}
