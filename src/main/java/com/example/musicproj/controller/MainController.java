package com.example.musicproj.controller;

import com.example.musicproj.entity.File;
import com.example.musicproj.repository.FileRepository;
import com.example.musicproj.service.FileService;
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
}
