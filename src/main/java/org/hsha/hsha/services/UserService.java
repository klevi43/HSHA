package org.hsha.hsha.services;

import org.hsha.hsha.Repository.UserRepository;
import org.hsha.hsha.models.User;
import org.hsha.hsha.models.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> retrieveUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteUserById(id);
    }
}
