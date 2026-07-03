package com.ideaforge.platform.collaboration.application.internal.commandservices;

import com.ideaforge.platform.collaboration.application.internal.outboundservices.acl.ExternalIdeasService;
import com.ideaforge.platform.collaboration.domain.model.aggregates.Team;
import com.ideaforge.platform.collaboration.domain.model.commands.RemoveTeamMemberCommand;
import com.ideaforge.platform.collaboration.domain.services.TeamsCommandService;
import com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class TeamsCommandServiceImpl implements TeamsCommandService {
    private final TeamRepository teamRepository;
    private final ExternalIdeasService externalIdeasService;

    public TeamsCommandServiceImpl(TeamRepository teamRepository, ExternalIdeasService externalIdeasService) {
        this.teamRepository = teamRepository;
        this.externalIdeasService = externalIdeasService;
    }

    public Optional<Team> handle(RemoveTeamMemberCommand command) {
        var team = teamRepository.findById(command.teamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found with id: " + command.teamId()));
        var idea = externalIdeasService.findById(team.getIdeaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Idea not found with id: " + team.getIdeaId()));
        if (!idea.getCreatorProfileId().equals(command.ownerProfileId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the idea owner can remove team members");
        }
        if (!team.removeMember(command.memberId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team member not found with id: " + command.memberId());
        }
        return Optional.of(teamRepository.save(team));
    }
}
