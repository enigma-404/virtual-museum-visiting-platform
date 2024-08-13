package org.enigma.virtualmuseumvisitingplatform.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoResponse {

    private long id;
    private String username;
    private String jwtToken;
    private List<String> roles;
}
