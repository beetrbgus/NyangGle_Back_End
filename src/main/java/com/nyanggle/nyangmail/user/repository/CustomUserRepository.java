package com.nyanggle.nyangmail.user.repository;

import com.nyanggle.nyangmail.user.persistence.User;

import java.util.Optional;

public interface CustomUserRepository {
    Optional<User> findByNormalUser(String domesticId, String providerType);
    Optional<User> findByUserUid(String userId);
}
