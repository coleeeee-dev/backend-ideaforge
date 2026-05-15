package com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories;

import com.ideaforge.platform.collaboration.domain.model.aggregates.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByIdeaId(Long ideaId);
}
