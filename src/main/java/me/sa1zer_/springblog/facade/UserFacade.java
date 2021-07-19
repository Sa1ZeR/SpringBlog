package me.sa1zer_.springblog.facade;

import me.sa1zer_.springblog.dto.UserDTO;
import me.sa1zer_.springblog.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setFirstname(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setBio(user.getBio());
        userDTO.setId(user.getId());

        return userDTO;
    }
}
