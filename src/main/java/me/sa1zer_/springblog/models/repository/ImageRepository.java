package me.sa1zer_.springblog.models.repository;

import me.sa1zer_.springblog.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findImageByUserId(Long id);

    Optional<Image> findImageByPostId(Long id);
}
