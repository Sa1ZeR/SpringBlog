package me.sa1zer_.springblog.repository;

import me.sa1zer_.springblog.models.Comment;
import me.sa1zer_.springblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

    Comment findByIdAndUserId(Long id, Long userId);

}
