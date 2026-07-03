package com.ideaforge.platform.collaboration.domain.services;

import com.ideaforge.platform.collaboration.domain.model.aggregates.Team;
import com.ideaforge.platform.collaboration.domain.model.commands.RemoveTeamMemberCommand;

import java.util.Optional;

public interface TeamsCommandService {
    Optional<Team> handle(RemoveTeamMemberCommand command);
}
