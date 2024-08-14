package org.enigma.virtualmuseumvisitingplatform.dto.response.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {
    private Long id;
    private String text;
    private String username;
}
