package net.jemsit.Auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.jemsit.Auth.request.AuthRequest;
import net.jemsit.Auth.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("register")
    public ResponseEntity<Auth> registerUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok().body(authService.register(registerRequest));
    }

    @PostMapping("login")
    public ResponseEntity<Auth> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok().body(authService.login(authRequest));
    }

    @PostMapping("validate-token")
    public ResponseEntity<String> validateToken() {
        return ResponseEntity.ok().body(authService.validateToken());
    }
}
