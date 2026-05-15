package com.ideaforge.platform.ideas.application.internal.commandservices;

import com.ideaforge.platform.ideas.domain.exceptions.IdeaNotFoundException;
import com.ideaforge.platform.ideas.domain.model.aggregates.Idea;
import com.ideaforge.platform.ideas.domain.model.commands.*;
import com.ideaforge.platform.ideas.domain.services.IdeasCommandService;
import com.ideaforge.platform.ideas.infrastructure.persistence.jpa.repositories.IdeaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdeasCommandServiceImpl implements IdeasCommandService {
    private final IdeaRepository ideaRepository;
    public IdeasCommandServiceImpl(IdeaRepository ideaRepository) { this.ideaRepository = ideaRepository; }
    public Optional<Idea> handle(CreateIdeaCommand command) { return Optional.of(ideaRepository.save(new Idea(command))); }
    public Optional<Idea> handle(UpdateIdeaCommand command) { var idea = ideaRepository.findById(command.ideaId()).orElseThrow(() -> new IdeaNotFoundException(command.ideaId())); idea.update(command); return Optional.of(ideaRepository.save(idea)); }
    public void handle(DeleteIdeaCommand command) { var idea = ideaRepository.findById(command.ideaId()).orElseThrow(() -> new IdeaNotFoundException(command.ideaId())); idea.deactivate(); ideaRepository.save(idea); }
    public Optional<Idea> handle(UpdateIdeaStatusCommand command) { var idea = ideaRepository.findById(command.ideaId()).orElseThrow(() -> new IdeaNotFoundException(command.ideaId())); idea.updateStatus(command.status()); return Optional.of(ideaRepository.save(idea)); }
    public Optional<Idea> handle(UpdateIdeaStageCommand command) { var idea = ideaRepository.findById(command.ideaId()).orElseThrow(() -> new IdeaNotFoundException(command.ideaId())); idea.updateStage(command.stage()); return Optional.of(ideaRepository.save(idea)); }
    public Optional<Idea> handle(AddRequiredRoleCommand command) { var idea = ideaRepository.findById(command.ideaId()).orElseThrow(() -> new IdeaNotFoundException(command.ideaId())); idea.addRequiredRole(command); return Optional.of(ideaRepository.save(idea)); }
}
