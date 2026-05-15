package com.ideaforge.platform.exploration.application.internal.commandservices;

import com.ideaforge.platform.exploration.domain.exceptions.SavedIdeaNotFoundException;
import com.ideaforge.platform.exploration.domain.model.aggregates.SavedIdea;
import com.ideaforge.platform.exploration.domain.model.commands.SaveIdeaCommand;
import com.ideaforge.platform.exploration.domain.model.commands.UnsaveIdeaCommand;
import com.ideaforge.platform.exploration.domain.services.SavedIdeasCommandService;
import com.ideaforge.platform.exploration.infrastructure.persistence.jpa.repositories.SavedIdeaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SavedIdeasCommandServiceImpl implements SavedIdeasCommandService {
    private final SavedIdeaRepository repository;
    public SavedIdeasCommandServiceImpl(SavedIdeaRepository repository) { this.repository = repository; }
    public Optional<SavedIdea> handle(SaveIdeaCommand command) {
        if (repository.existsByProfileIdAndIdeaId(command.profileId(), command.ideaId())) throw new IllegalArgumentException("Idea already saved");
        return Optional.of(repository.save(new SavedIdea(command)));
    }
    public void handle(UnsaveIdeaCommand command) {
        var saved = repository.findByProfileIdAndIdeaId(command.profileId(), command.ideaId()).orElseThrow(() -> new SavedIdeaNotFoundException(command.profileId(), command.ideaId()));
        repository.delete(saved);
    }
}
