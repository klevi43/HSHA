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

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public int retrieveUserIdByEmail(String email) throws ServerException {
        Optional<User> searchedUser = retrieveValidUserByEmail(email);
        return searchedUser.get().getId();
    }

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> retrieveUserById(Integer id) throws ServerException {
        Optional<User> searchedUser = retrieveValidUserById(id);
        return searchedUser;
    }

    public Optional<User> retrieveUserByEmail(String email) throws ServerException {
        Optional<User> searchedUser = retrieveValidUserByEmail(email);
        return searchedUser;
    }
    public User saveUser(User user) {
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        Optional<User> searchedUser = userRepository.findByEmail(user.getEmail());
        if(searchedUser.isPresent()) {
            throw new IllegalStateException("User already exists");
        }
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteUserById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> appUser = userRepository.findByEmail(email);

        if (appUser.isPresent()) {
            var springUser = org.springframework.security.core.userdetails.User.withUsername(appUser.get().getEmail())
                    .password(appUser.get().getPassword())
                    .roles(appUser.get().getRole())
                    .build();

            return springUser;
        }
        return null;
    }
    private Optional<User> retrieveValidUserByEmail(String email) throws ServerException {
        Optional<User> searchedUser = userRepository.findByEmail(email);
        if(searchedUser.isEmpty()) {
            throw new ServerException("User does not exist");
        }
        return searchedUser;
    }

    private Optional<User> retrieveValidUserById(Integer id) throws ServerException {
        Optional<User> searchedUser = userRepository.findById(id);
        if(searchedUser.isEmpty()) {
            throw new ServerException("User: " + id + " not found");
        }
        return searchedUser;
    }

}
