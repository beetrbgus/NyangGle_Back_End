package com.nyanggle.nyangmail.persistence.repository;

import com.nyanggle.nyangmail.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDomesticIdAndProviderType(String domesticId, String providerType);
    Optional<User> findByUserUid(String userId);
}
