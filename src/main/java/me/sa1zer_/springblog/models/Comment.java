package me.sa1zer_.springblog.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @Column(columnDefinition = "text", nullable = false)
    private String text;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String username;
    @Column(updatable = false)
    private LocalDateTime localDateTime;

    public Comment() {

    }

    @PrePersist
    public void onCreate() {
        localDateTime = LocalDateTime.now();
    }
}
