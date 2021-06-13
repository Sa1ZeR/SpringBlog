package me.sa1zer_.springblog.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @Column(columnDefinition = "text", nullable = false)
    private String text;
    @Column(nullable = false)
    private long userId;
    @Column(updatable = false)
    private LocalDateTime localDateTime;

    @PrePersist
    public void onCreate() {
        localDateTime = LocalDateTime.now();
    }
}
