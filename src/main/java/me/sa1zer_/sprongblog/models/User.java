package me.sa1zer_.sprongblog.models;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {

    private long id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String bio;
    private String password;

    private Set<Roles> rolesSet = new HashSet<>();
    private List<Post> postList = new ArrayList<>();

    private LocalDateTime localDateTime;

    @PrePersist
    public void onCreate() {
        localDateTime = LocalDateTime.now();
    }
}
