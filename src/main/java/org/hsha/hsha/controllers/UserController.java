package org.hsha.hsha.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.hsha.hsha.models.User;
import org.hsha.hsha.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.retrieveAllUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") Integer id) {
        return userService.retrieveUserById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user, HttpServletRequest request) throws ServerException{
        userService.saveUser(user);
        if (user != null) {
            URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();
            return ResponseEntity.created(location).body(user);
        } else {
            throw new ServerException("Error in creating user");
        }

    }


    @Transactional
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Integer id) {
        userService.deleteUserById(id);
    }
}
