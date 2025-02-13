package com.knoettner.hhuddle.controller;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.repository.RoleRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import com.knoettner.hhuddle.security.modelsDtos.JwtResponse;
import com.knoettner.hhuddle.security.modelsDtos.LoginDto;
import com.knoettner.hhuddle.security.jwt.JwtUtils;
import com.knoettner.hhuddle.security.services.UserDetailsImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping()
    public ResponseEntity<?> loadUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || (authentication instanceof AnonymousAuthenticationToken) || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("No valid token");
        }
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //necessary to interate over a Collection of Authorities (with each Authorities has a field String role)(== roles) to make them a list of Strings
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        Optional<MyUser> maybeUser = userRepository.findById(userDetails.getId());
        MyUser user = maybeUser.get();
        Long houseId = null;
        try {
            houseId = user.getHouse().getId();}
        catch(Exception e) {

        };
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getMail(),
                roles,
                houseId));
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
    //throws exception if authentication didnt work out
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getMail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //necessary to interate over a Collection of Authorities (with each Authorities has a field String role)(== roles) to make them a list of Strings
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        Optional<MyUser> maybeUser = userRepository.findById(userDetails.getId());
        MyUser user = maybeUser.get();
        Long houseId = null;
        try {
        houseId = user.getHouse().getId();}
        catch(Exception e) {

        };
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getMail(),
                roles,
                houseId
                ));
    }
}
