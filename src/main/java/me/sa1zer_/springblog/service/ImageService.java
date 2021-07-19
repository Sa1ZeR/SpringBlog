package me.sa1zer_.springblog.service;

import me.sa1zer_.springblog.models.Image;
import me.sa1zer_.springblog.models.Post;
import me.sa1zer_.springblog.models.User;
import me.sa1zer_.springblog.repository.ImageRepository;
import me.sa1zer_.springblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageService {

    public static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserService userService, PostService postService) {
        this.imageRepository = imageRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public Image uploadImageToUser(MultipartFile file, Principal principal) throws IOException {
        User user = userService.getUserByPrincipal(principal);
        logger.info("Uploading image for user: {} ", user.getUsername());

        Image userImage = getImageByUserId(user.getId());

        if(!ObjectUtils.isEmpty(userImage)) {
            imageRepository.delete(userImage);
        }

        Image image =  new Image();

        image.setUserId(user.getId());
        image.setImageBytes(compressBytes(file.getBytes()));
        image.setName(file.getOriginalFilename());

        return imageRepository.save(image);
    }

    public Image uploadImageToPost(MultipartFile file, Principal principal, Long postId) throws IOException {
        User user = userService.getUserByPrincipal(principal);
        Post post = user.getPostList().stream().filter(p -> p.getId().equals(postId)).collect(toSinglePostCollector());

        logger.info("Uploading image for post: {} ", user.getUsername());

        Image postImage = getImageByPostId(user.getId());
        if(!ObjectUtils.isEmpty(postImage)) {
            imageRepository.delete(postImage);
        }

        Image image =  new Image();
        image.setPostId(post.getId());
        image.setImageBytes(compressBytes(file.getBytes()));
        image.setName(file.getOriginalFilename());

        return imageRepository.save(image);
    }

    public Image getImageToUser(Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        Image image = getImageByUserId(user.getId());
        if(!ObjectUtils.isEmpty(image)) {
            image.setImageBytes(decompressBytes(image.getImageBytes()));
        }

        return image;
    }

    public Image getImageToPost(Principal principal, Long postId) {
        User user = userService.getUserByPrincipal(principal);

        Image image = getImageByPostId(postId);
        if(!ObjectUtils.isEmpty(image)) {
            image.setImageBytes(decompressBytes(image.getImageBytes()));
        }

        return image;
    }

    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            logger.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            logger.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }

    private <T> Collector<T, ?, T> toSinglePostCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    public Image getImageByUserId(Long userId) {
        return imageRepository.findImageByUserId(userId).orElse(null);
    }

    public Image getImageByPostId(Long postId) {
        return imageRepository.findImageByPostId(postId).orElse(null);
    }
}
