package me.sa1zer_.springblog.service;

import javafx.geometry.Pos;
import me.sa1zer_.springblog.dto.PostDTO;
import me.sa1zer_.springblog.exceptions.PostNotFoundException;
import me.sa1zer_.springblog.models.Image;
import me.sa1zer_.springblog.models.Post;
import me.sa1zer_.springblog.models.User;
import me.sa1zer_.springblog.repository.ImageRepository;
import me.sa1zer_.springblog.repository.PostRepository;
import me.sa1zer_.springblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    public static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.userService = userService;
    }

    public Post createPost(PostDTO postDTO, Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        Post post = new Post();
        post.setUser(user);
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDesc());
        post.setLikes(0);

        logger.info("saving post for uset {}", user.getUsername());

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public List<Post> getAllPostsForUser(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Post likePost(Long postId, String username) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new PostNotFoundException("Post cannot be found for user"));

        Optional<String> userLiked = post.getLikedUser().stream().
                filter(u -> u.equals(username)).findAny();

        if(userLiked.isPresent()) {
            post.setLikes(post.getLikes()-1);
            post.getLikedUser().remove(username);
        } else {
            post.setLikes(post.getLikes()+1);
            post.getLikedUser().add(username);
        }
        return postRepository.save(post);
    }

    public void deletePost(Long postId, Principal principal) {
        Post post = getPostById(postId);

        Optional<Image> postImages = imageRepository.findImageByPostId(postId);
        postRepository.delete(post);

        postImages.ifPresent(imageRepository::delete);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(
                ()-> new PostNotFoundException("Post cannot be found"));
    }

    public Post findPostsById(Long id, Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        return postRepository.findByIdAndUser(id, user).orElseThrow(
                ()-> new PostNotFoundException("Post cannot be found for user"));
    }
}
