package com.gramconnect.dto;

import com.gramconnect.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;
}