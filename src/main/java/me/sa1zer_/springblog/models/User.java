package me.sa1zer_.springblog.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(columnDefinition = "text")
    private String bio;
    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = Roles.class)
    //@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"))
    private Set<Roles> rolesSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime localDateTime;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @PrePersist
    public void onCreate() {
        localDateTime = LocalDateTime.now();
    }
}
