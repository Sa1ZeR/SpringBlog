package me.sa1zer_.springblog.controllers;

import me.sa1zer_.springblog.dto.UserDTO;
import me.sa1zer_.springblog.facade.UserFacade;
import me.sa1zer_.springblog.models.User;
import me.sa1zer_.springblog.service.UserService;
import me.sa1zer_.springblog.validations.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserFacade userFacade;
    private final ResponseErrorValidator responseErrorValidator;

    @Autowired
    public UserController(UserService userService, UserFacade userFacade, ResponseErrorValidator responseErrorValidator) {
        this.userService = userService;
        this.userFacade = userFacade;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String userId) {
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult result, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(result);
        if(!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);
        UserDTO userDTO1 = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO1, HttpStatus.OK);
    }
}
