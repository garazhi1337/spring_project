package com.example.musicproj.service;

import com.example.musicproj.entity.UserEntity;
import com.example.musicproj.exception.UserAlreadyExistsException;
import com.example.musicproj.exception.UserNotFoundException;
import com.example.musicproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity registration(String username, String password) throws UserAlreadyExistsException {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder(12).encode(password));
        user.setRole("USER");
        user.setRegDate(new Date().toString());

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException();
        }
        return userRepository.save(user);
    }

    public UserEntity findUser(Long id) throws UserNotFoundException {
        if (userRepository.findById(id) == null) {
            throw new UserNotFoundException();
        }
        return userRepository.findById(id).get();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void addUserPfpToModel(Model model) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
            if (userEntity.getProfilePicture() != null) {
                String photo = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(userEntity.getProfilePicture().getContent());
                model.addAttribute("pfp", photo);
            }
        } else {

        }
    }
}
