package com.nyanggle.nyangmail.user.repository;

import com.nyanggle.nyangmail.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDomesticIdAndProviderType(String domesticId, String providerType);
    Optional<User> findByUserUid(String userId);
}
