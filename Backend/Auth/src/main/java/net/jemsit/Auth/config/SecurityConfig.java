package net.jemsit.Auth.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class SecurityConfig {
    @Value("${jdbc.host}")
    private String host;

    @Value("${jdbc.port}")
    private String port;
    @Value("${jdbc.db}")
    private String db;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public Flyway executeMigrations() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://"+host+":"+port+"/"+db, username, password)
                .load();

        flyway.migrate();
        return flyway;
    }
    @Bean
    public UserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public ApplicationRunner createAdmin(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userDetailsManager.userExists("admin@mail.com")) {
                userDetailsManager.createUser(User.builder()
                        .username("admin@mail.com")
                        .password(passwordEncoder.encode("admin"))
                        .authorities("admin")
                        .build());
            }
        };
    }

}
