package me.sa1zer_.springblog.service;


import me.sa1zer_.springblog.dto.CommentDTO;
import me.sa1zer_.springblog.models.Comment;
import me.sa1zer_.springblog.models.Post;
import me.sa1zer_.springblog.models.User;
import me.sa1zer_.springblog.repository.CommentRepository;
import me.sa1zer_.springblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    public static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostService postService, UserRepository userRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Comment createNewComment(Long postId, CommentDTO commentDTO, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Post post = postService.getPostById(postId);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setText(commentDTO.getMessage());

        logger.info("Saving comment for user {} and postId {}", user.getUsername(), post.getId());

        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsForPost(Long postId) {
        Post post = postService.getPostById(postId);

        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments;
    }

    public void deleteComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }

}
