package com.ideaforge.platform.ideas.domain.services;

import com.ideaforge.platform.ideas.domain.model.aggregates.Idea;
import com.ideaforge.platform.ideas.domain.model.commands.*;

import java.util.Optional;

public interface IdeasCommandService {
    Optional<Idea> handle(CreateIdeaCommand command);
    Optional<Idea> handle(UpdateIdeaCommand command);
    void handle(DeleteIdeaCommand command);
    Optional<Idea> handle(UpdateIdeaStatusCommand command);
    Optional<Idea> handle(UpdateIdeaStageCommand command);
    Optional<Idea> handle(AddRequiredRoleCommand command);
}
