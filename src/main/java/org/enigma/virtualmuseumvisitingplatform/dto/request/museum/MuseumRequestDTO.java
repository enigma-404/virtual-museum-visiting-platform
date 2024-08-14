package org.enigma.virtualmuseumvisitingplatform.dto.request.museum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MuseumRequestDTO {
    @NotBlank(message = "Museum name cannot be blank")
    @Size(min = 2, max = 100, message = "Museum name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;
}
