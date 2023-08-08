package com.example.musicproj.controller;

import com.example.musicproj.entity.UserEntity;
import com.example.musicproj.repository.UserRepository;
import com.example.musicproj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/users")
    public String registration(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        try {
            service.registration(username, password);
            return "redirect:/users";
        } catch (Exception e) {
            return "redirect:/users";
        }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            service.deleteUser(id);
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
