package com.jonghwan.typing.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserDTO {
    private String username;
    private String name;
    private String role;
}
