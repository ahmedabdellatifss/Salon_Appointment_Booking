package com.roaa.controller;


import com.roaa.modal.User;
import com.roaa.repository.UserRepository;
import com.roaa.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<User> CreateUser(@RequestBody @Valid User user) {
        User CreatedUser = userService.createUser(user);
        return new ResponseEntity<>(CreatedUser, HttpStatus.CREATED);
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<User> getUserById( @PathVariable Long id) throws Exception{
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> UpdateUser(@PathVariable Long id, @RequestBody User user) throws Exception {
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id)throws Exception{
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.ACCEPTED);
    }

}
