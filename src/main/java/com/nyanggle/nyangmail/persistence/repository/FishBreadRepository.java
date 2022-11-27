package com.nyanggle.nyangmail.persistence.repository;

import com.nyanggle.nyangmail.persistence.entity.FishBread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FishBreadRepository extends JpaRepository<FishBread, Long> {
    Optional<FishBread> findByIdAndReceiverUid(Long fishId, String uUid);
}