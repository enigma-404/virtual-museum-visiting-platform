package org.enigma.virtualmuseumvisitingplatform.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    @NotBlank(message = "Comment text cannot be blank")
    private String text;
}
