package net.jemsit.Auth.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthRequest {
    private String username;
    private String password;
}
