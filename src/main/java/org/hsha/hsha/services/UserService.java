package org.hsha.hsha.services;

import jakarta.transaction.Transactional;
import org.hsha.hsha.Repository.UserRepository;
import org.hsha.hsha.models.User;
import org.hsha.hsha.models.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public int retrieveUserIdByEmail(String email) throws Exception {
        User searchedUser = userRepository.findByEmail(email);
        if(searchedUser == null) {
            throw new Exception("User does not exist");
        }
        return searchedUser.getId();
    }

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> retrieveUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User retrieveUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User saveUser(User user) {
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        User searchedUser = userRepository.findByEmail(user.getEmail());
        if(searchedUser != null) {
            throw new IllegalStateException("User already exists");
        }
    }
    public void deleteUserById(Integer id) {
        userRepository.deleteUserById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User appUser = userRepository.findByEmail(email);

        if (appUser != null) {
            var springUser = org.springframework.security.core.userdetails.User.withUsername(appUser.getEmail())
                    .password(appUser.getPassword())
                    .roles(appUser.getRole())
                    .build();

            return springUser;
        }
        return null;
    }


}
