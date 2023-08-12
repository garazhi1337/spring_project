package com.example.musicproj.service;

import com.example.musicproj.entity.ProfilePicture;
import com.example.musicproj.entity.UserEntity;
import com.example.musicproj.repository.PfpRepository;
import com.example.musicproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PfpService {
    @Autowired
    private PfpRepository pfpRepository;
    @Autowired
    private UserRepository userRepository;

    public String uploadPfp(MultipartFile multipartFile) {
        ProfilePicture profilePicture = new ProfilePicture();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(userDetails.getUsername());
        profilePicture.setUser(user);
        try {
            profilePicture.setContent(multipartFile.getBytes());
            profilePicture.setSize(multipartFile.getBytes().length);
            profilePicture.setName(multipartFile.getOriginalFilename());

            String[] filenameSplit = profilePicture.getName().split("");
            System.out.println(profilePicture.getName());
            if ((filenameSplit[filenameSplit.length-3] + filenameSplit[filenameSplit.length-2] + filenameSplit[filenameSplit.length-1]).equals("jpg")) {
                pfpRepository.save(profilePicture);
            } else {

            }

            user.setProfilePicture(profilePicture);
            userRepository.deleteById(user.getId());
            userRepository.save(user);
        } catch (Exception e) {

        }

        return "redirect:/myprofile";
    }

}
