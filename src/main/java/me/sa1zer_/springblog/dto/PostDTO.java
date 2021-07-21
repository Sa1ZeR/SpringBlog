package me.sa1zer_.springblog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class PostDTO {

    private Long id;
    private String username;
    @NotEmpty
    private String title;
    private String desc;
    private String location;
    private Integer likes;
    private Set<String> userLikes;
}
