package com.example.musicproj.repository;

import com.example.musicproj.entity.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends CrudRepository<File, String> {
    File findByName(String name);
    Optional<File> findById(String id);
}
