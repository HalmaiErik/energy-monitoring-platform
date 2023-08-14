package com.distributedsystems.energyplatform.dto;

import com.distributedsystems.energyplatform.entity.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {

    private String username;
    private String token;
    private Set<Role> roles;
}
