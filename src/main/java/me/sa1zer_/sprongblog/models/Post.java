package me.sa1zer_.sprongblog.models;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Post {

    private long id;
    private String title;
    private String description;
    private String text;
    private int likes;

    private Set<String> likedUser = new HashSet<>();
    private User userBy;
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime localDateTime;

    @PrePersist
    public void onCreate() {
        localDateTime = LocalDateTime.now();
    }
}
