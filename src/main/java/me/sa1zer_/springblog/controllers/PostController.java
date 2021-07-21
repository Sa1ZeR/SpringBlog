package me.sa1zer_.springblog.controllers;

import me.sa1zer_.springblog.dto.PostDTO;
import me.sa1zer_.springblog.facade.PostFacade;
import me.sa1zer_.springblog.models.Post;
import me.sa1zer_.springblog.payload.response.MessageResponse;
import me.sa1zer_.springblog.service.PostService;
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
@RequestMapping("/api/post")
@CrossOrigin
public class PostController {

    private final PostService postService;
    private final PostFacade postFacade;
    private final ResponseErrorValidator responseErrorValidator;

    @Autowired
    public PostController(PostService postService, PostFacade postFacade, ResponseErrorValidator responseErrorValidator) {
        this.postService = postService;
        this.postFacade = postFacade;
        this.responseErrorValidator = responseErrorValidator;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createNewPost(@Valid @RequestBody PostDTO postDTO,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)) return errors;

        Post post = postService.createPost(postDTO, principal);
        PostDTO newPost = postFacade.postToPostDTO(post);

        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> allPosts = postService.getAllPosts().stream().map(
          postFacade::postToPostDTO
        ).collect(Collectors.toList());

        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/user/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(Principal principal) {
        List<PostDTO> allPosts = postService.getAllPostsForUser(principal).stream().map(
                postFacade::postToPostDTO
        ).collect(Collectors.toList());

        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable("postId") String postId,
                                            @PathVariable("username") String username) {
        Post post = postService.likePost(Long.parseLong(postId), username);
        PostDTO postDTO = postFacade.postToPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping("{postId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") String postId, Principal principal) {
        postService.deletePost(Long.parseLong(postId), principal);
        return new ResponseEntity<>(new MessageResponse("Post was deleted"), HttpStatus.OK);
    }
}
