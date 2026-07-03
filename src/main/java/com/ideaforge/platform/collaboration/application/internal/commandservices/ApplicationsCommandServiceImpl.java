package com.ideaforge.platform.collaboration.application.internal.commandservices;

import com.ideaforge.platform.collaboration.application.internal.outboundservices.acl.ExternalIdeasService;
import com.ideaforge.platform.collaboration.domain.model.aggregates.ProjectApplication;
import com.ideaforge.platform.collaboration.domain.model.aggregates.Team;
import com.ideaforge.platform.collaboration.domain.model.commands.*;
import com.ideaforge.platform.collaboration.domain.services.ApplicationsCommandService;
import com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories.ProjectApplicationRepository;
import com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ApplicationsCommandServiceImpl implements ApplicationsCommandService {
    private final ProjectApplicationRepository applicationRepository;
    private final TeamRepository teamRepository;
    private final ExternalIdeasService externalIdeasService;
    public ApplicationsCommandServiceImpl(ProjectApplicationRepository applicationRepository, TeamRepository teamRepository, ExternalIdeasService externalIdeasService) {
        this.applicationRepository = applicationRepository;
        this.teamRepository = teamRepository;
        this.externalIdeasService = externalIdeasService;
    }

    public Optional<ProjectApplication> handle(ApplyToIdeaCommand command) {
        if (applicationRepository.existsByIdeaIdAndApplicantProfileId(command.ideaId(), command.applicantProfileId())) throw new IllegalArgumentException("The profile already applied to this idea");
        return Optional.of(applicationRepository.save(new ProjectApplication(command)));
    }
    public Optional<ProjectApplication> handle(AcceptApplicationCommand command) {
        var application = applicationRepository.findById(command.applicationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found with id: " + command.applicationId()));
        validateOwner(application, command.ownerProfileId());
        if (!application.isPending()) throw new IllegalStateException("Only pending applications can be accepted");
        application.accept();
        var saved = applicationRepository.save(application);
        var team = teamRepository.findByIdeaId(application.getIdeaId()).orElseGet(() -> new Team(application.getIdeaId(), "Idea Team #" + application.getIdeaId()));
        team.addMember(application.getApplicantProfileId(), application.getRequestedRole());
        teamRepository.save(team);
        return Optional.of(saved);
    }
    public Optional<ProjectApplication> handle(RejectApplicationCommand command) {
        var application = applicationRepository.findById(command.applicationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found with id: " + command.applicationId()));
        validateOwner(application, command.ownerProfileId());
        if (!application.isPending()) throw new IllegalStateException("Only pending applications can be rejected");
        application.reject();
        return Optional.of(applicationRepository.save(application));
    }

    private void validateOwner(ProjectApplication application, Long ownerProfileId) {
        var idea = externalIdeasService.findById(application.getIdeaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Idea not found with id: " + application.getIdeaId()));
        if (!idea.getCreatorProfileId().equals(ownerProfileId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the idea owner can manage applications");
        }
    }
}
