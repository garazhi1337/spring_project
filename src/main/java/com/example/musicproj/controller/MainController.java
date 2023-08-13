package com.example.musicproj.controller;

import com.example.musicproj.entity.File;
import com.example.musicproj.repository.FileRepository;
import com.example.musicproj.repository.UserRepository;
import com.example.musicproj.service.FileService;
import com.example.musicproj.service.PfpService;
import com.example.musicproj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class MainController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PfpService pfpService;

    @RequestMapping("/")
    public String home() {
        return "home.html";
    }

    @RequestMapping("/addfile")
    public String addFile(Model model) {
        model.addAttribute("files", fileRepository.findAll());
        return "addfile.html";
    }

    @PostMapping("/addfile")
    public String uploadFile(@RequestParam(value = "addfile") MultipartFile multipartFile) throws IOException {
        fileService.uploadFile(multipartFile);
        return "redirect:/addfile";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/{fileid}")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileid") String id) {
        return fileService.downloadFile(id);
    }


    @GetMapping("/myprofile")
    public String myProfile(Model model) {
        userService.addUserPfpToModel(model);
        return "myprofile.html";
    }

    @PostMapping(value = "/users")
    public String registration(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        try {
            userService.registration(username, password);
            return "redirect:/users";
        } catch (Exception e) {
            return "redirect:/users";
        }
    }

    @PostMapping(value = "/myprofile")
    public String uploadPfp(@RequestParam(value = "addpic") MultipartFile pfp) {
        return pfpService.uploadPfp(pfp);
    }

    @RequestMapping(value = "/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users.html";
    }

    @GetMapping(value = "/editprofile")
    public String editProfile(Model model) {
        return "/editprofile.html";
    }
}
