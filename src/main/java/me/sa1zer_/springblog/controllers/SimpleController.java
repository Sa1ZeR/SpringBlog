package me.sa1zer_.springblog.controllers;

import me.sa1zer_.springblog.payload.request.SimpleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/simple/")
public class SimpleController {

    @PostMapping("first")
    public ResponseEntity<?> simple(@Valid @RequestBody SimpleRequest request) {
        return ResponseEntity.ok("OK");
    }
}
