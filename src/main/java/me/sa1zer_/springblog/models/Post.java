package me.sa1zer_.springblog.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String text;
    private int likes;

    @ElementCollection(targetClass = String.class)
    @Column
    private Set<String> likedUser = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void onCreate() {
        createdDate = LocalDateTime.now();
    }
}
