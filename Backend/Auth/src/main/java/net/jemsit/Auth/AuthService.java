package net.jemsit.Auth;

import lombok.extern.slf4j.Slf4j;
import net.jemsit.Auth.config.JwtService;
import net.jemsit.Auth.exception.UserAlreadyExistsException;
import net.jemsit.Auth.repository.UserRepository;
import net.jemsit.Auth.request.AuthRequest;
import net.jemsit.Auth.request.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsManager userDetailsManager;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    public AuthService(PasswordEncoder passwordEncoder, JwtService jwtService, UserDetailsManager userDetailsManager, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsManager = userDetailsManager;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }


    public Auth register(RegisterRequest registerRequest) {
            var user = User.builder()
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .authorities("user")
                    .build();
        if (userDetailsManager.userExists(user.getUsername())) throw new UserAlreadyExistsException();

        userDetailsManager.createUser(user);
        var jwt = jwtService.generateToken(Map.of(user.getUsername(), user.getAuthorities()),user);
        return  Auth.builder().token(jwt).build();
    }

    public Auth login(AuthRequest authRequest) {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
            ));
        } catch (Exception e) {
            log.error("Auth failed user:{}",e.getMessage());
            throw new RuntimeException("Invalid credentials", e);
        }
        var user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow();
        var jwt = jwtService.generateToken(Map.of(user.getUsername(), user.getAuthorities()),user);
        return  Auth.builder().token(jwt).build();
    }
}
