package me.sa1zer_.springblog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String str) {
        super(str);
    }
}
