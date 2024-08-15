package org.enigma.virtualmuseumvisitingplatform.dto.request.artifact;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtifactSaveRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotBlank(message = "Location cannot be blank")
    private String location;
    @NotNull
    private Long museumId;
}
