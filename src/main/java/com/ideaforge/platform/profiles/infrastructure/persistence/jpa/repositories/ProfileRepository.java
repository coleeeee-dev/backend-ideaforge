package com.ideaforge.platform.profiles.infrastructure.persistence.jpa.repositories;

import com.ideaforge.platform.profiles.domain.model.aggregates.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByAccountId(Long accountId);
    boolean existsByAccountId(Long accountId);
}
