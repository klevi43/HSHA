package org.hsha.hsha.controllers;

import jakarta.transaction.Transactional;
import org.hsha.hsha.models.*;
import org.hsha.hsha.services.ExSetService;
import org.hsha.hsha.services.ExerciseService;
import org.hsha.hsha.services.UserService;
import org.hsha.hsha.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public String showAllUsersPage(Model model) {
        List<User> users = userService.retrieveAllUsers();
        model.addAttribute("users", users);
        return "user/allUsers";
    }
    @GetMapping("/users/register")
    public String showSignUpPage(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute("registerDto", registerDto);
        model.addAttribute("success", false);
        return "user/register";

    }


    // USER RELATED METHODS
    @PostMapping("/users/register")
    public String createUser(Model model, @ModelAttribute RegisterDto registerDto, BindingResult result) throws ServerException {
        // check that the fields on the register form are valid
        if(!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(
                    new FieldError("registerDto", "confirmPassword",
                            "Password and Confirm Password do not match")
            );


        }
        Optional<User> searchedUser = userService.retrieveUserByEmail(registerDto.getEmail());
        if (searchedUser.isPresent()) {
            result.addError(
                    new FieldError("registerDto", "email",
                            "Email address is already used")
            );
        }

        if(result.hasErrors()) {
            return "user/register";
        }
        try {
            // try to create a new user
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User newUser = new User();
            newUser.setFirstName(registerDto.getFirstName());
            newUser.setLastName(registerDto.getLastName());
            newUser.setEmail(registerDto.getEmail());
            newUser.setRole("USER");
            newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            // clear the register form
            model.addAttribute("registerDto", new RegisterDto());

            // display success message
            model.addAttribute("success", true);

            // save user to DB
            userService.saveUser(newUser);
        }
        catch(Exception ex) {
            result.addError(
                    new FieldError("registerDto", "firstName",
                            ex.getMessage())
            );
        }
        return "redirect:/users";


    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login/login";
    }

//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//
//        return userService.retrieveAllUsers();
//    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") Integer id) throws ServerException {
        return userService.retrieveUserById(id);
    }

    @Transactional
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Integer id) {
        userService.deleteUserById(id);
    }

//    @Modifying
//    @PutMapping("/users/{id}")
//    public User updateUserById(@PathVariable int id, @RequestBody User user) throws ServerException {
//        Optional<User> updatedUser = userService.retrieveUserById(id);
//        if(updatedUser.isEmpty()) {
//            throw new ServerException("User not found!");
//        }
//        if (user.getUsername() != null)
//            updatedUser.get().setUsername(user.getUsername());
//        if (user.getPassword() != null)
//            updatedUser.get().setPassword(user.getPassword());
//
//        userService.saveUser(updatedUser.get());
//        return user;
//    }












}
