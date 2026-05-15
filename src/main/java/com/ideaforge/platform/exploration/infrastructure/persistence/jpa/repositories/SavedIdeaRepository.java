package com.ideaforge.platform.exploration.infrastructure.persistence.jpa.repositories;

import com.ideaforge.platform.exploration.domain.model.aggregates.SavedIdea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedIdeaRepository extends JpaRepository<SavedIdea, Long> {
    List<SavedIdea> findByProfileId(Long profileId);
    Optional<SavedIdea> findByProfileIdAndIdeaId(Long profileId, Long ideaId);
    boolean existsByProfileIdAndIdeaId(Long profileId, Long ideaId);
}
