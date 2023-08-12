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
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity registration(String username, String password) throws UserAlreadyExistsException {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder(12).encode(password));
        user.setRole("USER");

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

    public String updateUserPfp(MultipartFile pfp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        UserEntity user = userRepository.findByUsername(username);

        try {
            user.setContent(pfp.getBytes());
            user.setSize(pfp.getBytes().length);

            String[] filenameSplit = pfp.getName().split("");
            System.out.println(filenameSplit[filenameSplit.length-3] + filenameSplit[filenameSplit.length-2] + filenameSplit[filenameSplit.length-1]);
            if ((filenameSplit[filenameSplit.length-3] + filenameSplit[filenameSplit.length-2] + filenameSplit[filenameSplit.length-1]).equals("jpg")) {
                userRepository.save(user);
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/myprofile";
    }
}
