package org.hsha.hsha.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.hsha.hsha.models.*;
import org.hsha.hsha.services.ExSetService;
import org.hsha.hsha.services.ExerciseService;
import org.hsha.hsha.services.UserService;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.rmi.ServerException;

import java.util.List;
import java.util.Optional;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private WorkoutService workoutService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private ExSetService exSetService;




    // USER RELATED METHODS
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

    @GetMapping("/users/new")
    public String userForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "user/userForm";

    }
//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//
//        return userService.retrieveAllUsers();
//    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") Integer id) {
        return userService.retrieveUserById(id);
    }

    @Transactional
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Integer id) {
        userService.deleteUserById(id);
    }

    @Modifying
    @PutMapping("/users/{id}")
    public User updateUserById(@PathVariable int id, @RequestBody User user) throws ServerException {
        Optional<User> updatedUser = userService.retrieveUserById(id);
        if(updatedUser.isEmpty()) {
            throw new ServerException("User not found!");
        }
        if (user.getUsername() != null)
            updatedUser.get().setUsername(user.getUsername());
        if (user.getPassword() != null)
            updatedUser.get().setPassword(user.getPassword());

        userService.saveUser(updatedUser.get());
        return user;
    }












}
