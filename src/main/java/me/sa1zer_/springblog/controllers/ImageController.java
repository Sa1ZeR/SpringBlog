package me.sa1zer_.springblog.controllers;

import me.sa1zer_.springblog.models.Image;
import me.sa1zer_.springblog.payload.response.MessageResponse;
import me.sa1zer_.springblog.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file,
                                                      Principal principal) throws IOException {
        imageService.uploadImageToUser(file, principal);
        return new ResponseEntity<>(new MessageResponse("image was uploaded"), HttpStatus.OK);
    }

    @PostMapping("/{postId}/upload")
    public ResponseEntity<MessageResponse> uploadFileToPost(@PathVariable("postId") String postId, @RequestParam("file") MultipartFile file,
                                                      Principal principal) throws IOException {
        imageService.uploadImageToPost(file, principal, Long.parseLong(postId));
        return new ResponseEntity<>(new MessageResponse("image was uploaded"), HttpStatus.OK);
    }

    @PostMapping("/profileImage")
    public ResponseEntity<Image> getImageForUser(Principal principal) {
        Image image = imageService.getImageToUser(principal);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping("/{postId}/image")
    public ResponseEntity<Image> getImageForPost(@PathVariable("postId") String postId) {
        Image image = imageService.getImageByPostId(Long.parseLong(postId));
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
