package com.nyanggle.nyangmail.fishbread.repository;

import com.nyanggle.nyangmail.fishbread.persistence.FishBread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FishBreadRepository extends JpaRepository<FishBread, Long> {
    Optional<FishBread> findByIdAndReceiverUid(Long fishId, String uUid);
}