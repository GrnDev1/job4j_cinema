package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;

    public SimpleUserService(UserRepository sql2oUserRepository) {
        this.userRepository = sql2oUserRepository;
    }

    @Override
    public User save(User user) {
        var userOptional = userRepository.save(user);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("User with this mail already exists");
        }
        return userOptional.get();
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        var userOptional = userRepository.findByEmailAndPassword(email, password);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("Either the username or the password is incorrect, please correct and try again");
        }
        return userOptional.get();
    }
}
