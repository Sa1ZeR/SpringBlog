package me.sa1zer_.springblog.payload.request;

import lombok.Data;
import me.sa1zer_.springblog.annatations.PasswordMatches;
import me.sa1zer_.springblog.annatations.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {

    //@Email(message = "email is not valid")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter firstname")
    private String firstname;
    @NotEmpty(message = "Please enter lastname")
    private String lastname;
    @NotEmpty(message = "Please enter username")
    private String username;
    @NotEmpty(message = "Please enter password")
    @Size(min = 6)
    private String password;
    private String confPassword;
}
