package me.sa1zer_.springblog.models.repository;

import me.sa1zer_.springblog.models.Post;
import me.sa1zer_.springblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findAllByUserOrderByCreatedDateDesc(User user);

    Optional<Post> findAllByOrOrderByCreatedDateDesc(User user);

    Optional<Post> findByIdAndUser(Long id, User user);
}
