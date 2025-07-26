package com.security.authentication.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private Long userId;
    private String username;
    private String jwtToken;
    private List<String> roles;

    public UserInfoResponse(Long userId, String username, List<String> roles) {

        this.userId = userId;
        this.username = username;
        this.roles = roles;

    }

}
