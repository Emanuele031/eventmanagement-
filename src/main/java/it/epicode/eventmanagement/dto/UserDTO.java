package it.epicode.eventmanagement.dto;

import it.epicode.eventmanagement.enumerated.Role;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private Role role;
}

