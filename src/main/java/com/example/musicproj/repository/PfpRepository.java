package com.example.musicproj.repository;

import com.example.musicproj.entity.ProfilePicture;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PfpRepository extends CrudRepository<ProfilePicture, String> {

    Optional<ProfilePicture> findById(String id);
}
