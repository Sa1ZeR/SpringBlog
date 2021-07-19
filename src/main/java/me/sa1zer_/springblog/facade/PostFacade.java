package me.sa1zer_.springblog.facade;

import me.sa1zer_.springblog.dto.PostDTO;
import me.sa1zer_.springblog.models.Post;
import org.springframework.stereotype.Component;

@Component
public class PostFacade {

    public PostDTO postToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();

        postDTO.setId(post.getId());
        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setDesc(post.getDescription());
        postDTO.setTitle(post.getTitle());
        postDTO.setLikes(post.getLikes());
        postDTO.setUserLikes(post.getLikedUser());
        postDTO.setLocation(post.getLocation());

        return postDTO;
    }
}
