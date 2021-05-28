package me.sa1zer_.sprongblog.models;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class Comment {

    private long id;
    private Post post;
    private String text;
    private long userId;
    private LocalDateTime localDateTime;

    @PrePersist
    public void onCreate() {
        localDateTime = LocalDateTime.now();
    }
}
