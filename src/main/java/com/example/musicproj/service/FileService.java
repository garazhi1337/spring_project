package com.example.musicproj.service;

import com.example.musicproj.entity.File;
import com.example.musicproj.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public void uploadFile(MultipartFile multipartFile) throws IOException {
        File file = new File();
        file.setContent(multipartFile.getBytes());
        file.setName(multipartFile.getOriginalFilename());
        file.setSize(multipartFile.getBytes().length);
        file.setTimestamp(new Date());

        String[] filenameSplit = file.getName().split("");
        System.out.println(filenameSplit[filenameSplit.length-3] + filenameSplit[filenameSplit.length-2] + filenameSplit[filenameSplit.length-1]);
        if ((filenameSplit[filenameSplit.length-3] + filenameSplit[filenameSplit.length-2] + filenameSplit[filenameSplit.length-1]).equals("mp3")) {
            fileRepository.save(file);
        } else {

        }
    }

    public ResponseEntity<byte[]> downloadFile(String id) {
        Optional<File> file = fileRepository.findById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.get().getName())
                .body(file.get().getContent());
    }
}
