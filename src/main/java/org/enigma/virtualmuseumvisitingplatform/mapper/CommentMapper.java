package org.enigma.virtualmuseumvisitingplatform.mapper;

import org.enigma.virtualmuseumvisitingplatform.dto.request.comment.CommentRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.comment.CommentResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "museumId", target = "museum.id")
    @Mapping(source = "artifactId", target = "artifact.id")
    Comment commentRequestDtoToComment(CommentRequestDTO dto);

    @Mapping(source = "user.username", target = "username")
    CommentResponseDTO commentToCommentResponseDTO(Comment comment);
}
