package com.example.musicproj.service;

import com.example.musicproj.entity.UserEntity;
import com.example.musicproj.exception.UserAlreadyExistsException;
import com.example.musicproj.exception.UserNotFoundException;
import com.example.musicproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public UserEntity registration(String username, String password) throws UserAlreadyExistsException {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder(12).encode(password));
        user.setRole("USER");

        if (repo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException();
        }
        return repo.save(user);
    }

    public UserEntity findUser(Long id) throws UserNotFoundException {
        if (repo.findById(id) == null) {
            throw new UserNotFoundException();
        }
        return repo.findById(id).get();
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}
