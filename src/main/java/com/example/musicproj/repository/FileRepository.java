package com.example.musicproj.repository;

import com.example.musicproj.entity.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {
    File findByName(String name);
    File findById(String id);
}
