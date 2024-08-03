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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @GetMapping("/users")
    public String showAllUsersPage(Model model) {
        List<User> users = userService.retrieveAllUsers();
        model.addAttribute("users", users);
        return "user/allUsers";
    }
    @GetMapping("/users/signup")
    public String showSignUpPage(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("userDto", user);
        return "user/signUp";

    }


    // USER RELATED METHODS
    @PostMapping("/users/signup")
    public String createUser(@ModelAttribute UserDto userDto, BindingResult result) {

        if(userDto.getUsername().isEmpty()) {
            result.addError(new FieldError("user", "username", "Please enter a username"));
        }

        if(userDto.getPassword().isEmpty()) {
            result.addError(new FieldError("user", "password", "Please enter a password"));
        }

        if(userDto.getEmail().isEmpty()) {
            result.addError(new FieldError("user", "email", "Please enter an email"));
        }

        if(result.hasErrors()) {
            return "user/signUp";
        }
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword());
        newUser.setEmail(userDto.getEmail());
        userService.saveUser(newUser);
        return "redirect:/users";


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
