package com.ideaforge.platform.collaboration.domain.services;

import com.ideaforge.platform.collaboration.domain.model.aggregates.ProjectApplication;
import com.ideaforge.platform.collaboration.domain.model.commands.*;

import java.util.Optional;

public interface ApplicationsCommandService {
    Optional<ProjectApplication> handle(ApplyToIdeaCommand command);
    Optional<ProjectApplication> handle(AcceptApplicationCommand command);
    Optional<ProjectApplication> handle(RejectApplicationCommand command);
}
