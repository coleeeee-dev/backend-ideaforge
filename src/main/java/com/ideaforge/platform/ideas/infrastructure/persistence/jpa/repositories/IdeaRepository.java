package com.ideaforge.platform.ideas.infrastructure.persistence.jpa.repositories;

import com.ideaforge.platform.ideas.domain.model.aggregates.Idea;
import com.ideaforge.platform.ideas.domain.model.valueobjects.IdeaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findByCreatorProfileId(Long creatorProfileId);
    List<Idea> findByStatus(IdeaStatus status);
}
