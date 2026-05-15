package com.ideaforge.platform.ideas.application.internal.queryservices;

import com.ideaforge.platform.ideas.domain.model.aggregates.Idea;
import com.ideaforge.platform.ideas.domain.model.queries.*;
import com.ideaforge.platform.ideas.domain.model.valueobjects.IdeaStatus;
import com.ideaforge.platform.ideas.domain.services.IdeasQueryService;
import com.ideaforge.platform.ideas.infrastructure.persistence.jpa.repositories.IdeaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdeasQueryServiceImpl implements IdeasQueryService {
    private final IdeaRepository ideaRepository;
    public IdeasQueryServiceImpl(IdeaRepository ideaRepository) { this.ideaRepository = ideaRepository; }
    public List<Idea> handle(GetAllIdeasQuery query) { return ideaRepository.findAll(); }
    public List<Idea> handle(GetIdeasQuery query) { return ideaRepository.findAll(); }
    public Optional<Idea> handle(GetIdeaByIdQuery query) { return ideaRepository.findById(query.ideaId()); }
    public List<Idea> handle(GetIdeasByCreatorIdQuery query) { return ideaRepository.findByCreatorProfileId(query.creatorProfileId()); }
    public List<Idea> handle(GetIdeasByStatusQuery query) { return ideaRepository.findByStatus(IdeaStatus.valueOf(query.status().toUpperCase())); }
}
