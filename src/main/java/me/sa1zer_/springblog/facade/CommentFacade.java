package me.sa1zer_.springblog.facade;

import me.sa1zer_.springblog.dto.CommentDTO;
import me.sa1zer_.springblog.models.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentFacade {

    public CommentDTO commentToCommentDTo(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setMessage(comment.getText());
        commentDTO.setUsername(comment.getUsername());

        return commentDTO;
    }
}
