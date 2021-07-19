package me.sa1zer_.springblog.controllers;

import me.sa1zer_.springblog.payload.request.LoginRequest;
import me.sa1zer_.springblog.payload.request.SignupRequest;
import me.sa1zer_.springblog.payload.response.JWTTokenSuccessResponse;
import me.sa1zer_.springblog.payload.response.MessageResponse;
import me.sa1zer_.springblog.security.JWTTokenProvider;
import me.sa1zer_.springblog.security.SecurityConstants;
import me.sa1zer_.springblog.service.UserService;
import me.sa1zer_.springblog.validations.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;
    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registration(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
        ResponseEntity<Object> error = responseErrorValidator.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(error)) {
            return error;
        }

        userService.createUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}
