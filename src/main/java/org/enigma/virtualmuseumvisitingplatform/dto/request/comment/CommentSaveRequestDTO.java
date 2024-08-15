package org.enigma.virtualmuseumvisitingplatform.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveRequestDTO {
    @NotBlank(message = "Comment text cannot be blank")
    private String text;
    @NotNull
    private Long userId;
    @NotNull
    private Long artifactId;
}
