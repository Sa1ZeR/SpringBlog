package me.sa1zer_.springblog.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "password cannot be empty")
    private String password;

}
