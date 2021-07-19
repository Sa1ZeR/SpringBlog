package me.sa1zer_.springblog.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private Long id;
    private String username;
    private String title;
    private String desc;
    private String location;
    private Integer likes;
    private Set<String> userLikes;
}
