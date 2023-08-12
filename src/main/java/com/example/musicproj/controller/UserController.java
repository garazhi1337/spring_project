package com.example.musicproj.controller;

import com.example.musicproj.repository.PfpRepository;
import com.example.musicproj.repository.UserRepository;
import com.example.musicproj.service.PfpService;
import com.example.musicproj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PfpService pfpService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/users")
    public String registration(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        try {
            userService.registration(username, password);
            return "redirect:/users";
        } catch (Exception e) {
            return "redirect:/users";
        }
    }

    @PostMapping("/myprofile")
    public String uploadPfp(@RequestParam(value = "addpic") MultipartFile pfp) {
        return pfpService.uploadPfp(pfp);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Deleted!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users.html";
    }
}
