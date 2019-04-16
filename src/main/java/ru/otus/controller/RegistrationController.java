package ru.otus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.otus.domain.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RegistrationController {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @PostMapping("/library/users")
    public ResponseEntity<Mono<String>> newUser(@RequestBody User newUserData) {

        if (userDetailsManager.userExists(newUserData.getLogin())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Mono.just("LoginName already in use"));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role;
        role = newUserData.isWantToBecomeALibraryMan() ? "ROLE_ADMIN" : "ROLE_USER";
        authorities.add(new SimpleGrantedAuthority(role));

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(newUserData.getLogin())
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(newUserData.getPassword()))
                .authorities(authorities).build();

        userDetailsManager.createUser(userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(Mono.empty());


    }
}
