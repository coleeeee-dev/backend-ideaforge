package com.ideaforge.platform.collaboration.application.internal.outboundservices.acl;

import com.ideaforge.platform.ideas.domain.model.aggregates.Idea;
import com.ideaforge.platform.ideas.infrastructure.persistence.jpa.repositories.IdeaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalIdeasService {
    private final IdeaRepository ideaRepository;

    public ExternalIdeasService(IdeaRepository ideaRepository) { this.ideaRepository = ideaRepository; }

    public Optional<Idea> findById(Long ideaId) { return ideaRepository.findById(ideaId); }
}
