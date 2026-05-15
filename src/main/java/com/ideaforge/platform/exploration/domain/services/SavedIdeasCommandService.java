package com.ideaforge.platform.exploration.domain.services;

import com.ideaforge.platform.exploration.domain.model.aggregates.SavedIdea;
import com.ideaforge.platform.exploration.domain.model.commands.SaveIdeaCommand;
import com.ideaforge.platform.exploration.domain.model.commands.UnsaveIdeaCommand;

import java.util.Optional;

public interface SavedIdeasCommandService {
    Optional<SavedIdea> handle(SaveIdeaCommand command);
    void handle(UnsaveIdeaCommand command);
}
