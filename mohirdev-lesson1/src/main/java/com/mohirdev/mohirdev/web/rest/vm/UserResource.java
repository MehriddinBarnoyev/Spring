package com.mohirdev.mohirdev.web.rest.vm;

import com.mohirdev.mohirdev.domain.User;
import com.mohirdev.mohirdev.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity create(@RequestBody User user){
        User result = UserService.save(user);
        return ResponseEntity.ok(result);
    }
}
