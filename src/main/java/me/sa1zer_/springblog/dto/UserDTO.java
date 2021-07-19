package me.sa1zer_.springblog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

//data transfer object
@Data
public class UserDTO {

    private Long id;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    private String username;
    private String bio;
}
