package com.nyanggle.nyangmail.persistence.repository;

import com.nyanggle.nyangmail.persistence.entity.User;

import java.util.Optional;

public interface CustomUserRepository {
    Optional<User> findByNormalUser(String domesticId, String providerType);
}
