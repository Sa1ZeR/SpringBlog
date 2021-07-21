package me.sa1zer_.springblog.controllers;

import me.sa1zer_.springblog.dto.CommentDTO;
import me.sa1zer_.springblog.facade.CommentFacade;
import me.sa1zer_.springblog.models.Comment;
import me.sa1zer_.springblog.payload.response.MessageResponse;
import me.sa1zer_.springblog.service.CommentService;
import me.sa1zer_.springblog.validations.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;
    private final CommentFacade commentFacade;
    private final ResponseErrorValidator responseErrorValidator;

    @Autowired
    public CommentController(CommentService commentService, CommentFacade commentFacade, ResponseErrorValidator responseErrorValidator) {
        this.commentService = commentService;
        this.commentFacade = commentFacade;
        this.responseErrorValidator = responseErrorValidator;
    }

    @PostMapping("{postId}/create")
    public ResponseEntity<Object> createNewComment(@Valid @RequestBody CommentDTO commentDTO,
                                                   @PathVariable("postId") String postID, BindingResult bindingResult,
                                                   Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)) return errors;

        Comment comment = commentService.createNewComment(Long.parseLong(postID), commentDTO, principal);
        CommentDTO newComment = commentFacade.commentToCommentDTo(comment);

        return new ResponseEntity<>(newComment, HttpStatus.OK);
    }

    @GetMapping("{postId}/all")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable("postId") String postId) {
        List<CommentDTO> allComments = commentService.getAllCommentsForPost(Long.parseLong(postId)).stream()
                .map(
                        commentFacade::commentToCommentDTo
                ).collect(Collectors.toList());

        return new ResponseEntity<>(allComments, HttpStatus.OK);
    }

    @PostMapping("{commentId}/delete")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable("commentId") String commentId) {
        commentService.deleteComment(Long.parseLong(commentId));
        return new ResponseEntity<>(new MessageResponse("Comment was deleted"), HttpStatus.OK);
    }
}
