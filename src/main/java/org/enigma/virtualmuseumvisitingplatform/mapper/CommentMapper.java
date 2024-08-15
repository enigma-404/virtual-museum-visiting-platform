package org.enigma.virtualmuseumvisitingplatform.mapper;


import org.enigma.virtualmuseumvisitingplatform.dto.request.comment.CommentSaveRequestDTO;
import org.enigma.virtualmuseumvisitingplatform.dto.response.comment.CommentResponseDTO;
import org.enigma.virtualmuseumvisitingplatform.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommentMapper {
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "artifact.id", source = "artifactId")
    Comment commentSaveRequestDTOToComment(CommentSaveRequestDTO commentSaveRequestDTO);
    List<CommentResponseDTO> commentToCommentResponseDTOList(List<Comment> comments);
}
