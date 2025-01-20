package com.knoettner.hhuddle.controller;

import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/home/{id}")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('RESIDENT')")
   Long getHouseIdByUser(@PathVariable("id") Long userId) {
      return userService.getHouseIdByUser(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/passwordcheck")
    Boolean asUserChangedFirstPW(Long userId) {
        return userService.hasUserChangedFirstPW(userId);
    }
}
