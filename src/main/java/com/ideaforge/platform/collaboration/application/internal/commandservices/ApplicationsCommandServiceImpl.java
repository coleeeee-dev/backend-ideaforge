package com.ideaforge.platform.collaboration.application.internal.commandservices;

import com.ideaforge.platform.collaboration.domain.exceptions.ApplicationNotFoundException;
import com.ideaforge.platform.collaboration.domain.model.aggregates.ProjectApplication;
import com.ideaforge.platform.collaboration.domain.model.aggregates.Team;
import com.ideaforge.platform.collaboration.domain.model.commands.*;
import com.ideaforge.platform.collaboration.domain.services.ApplicationsCommandService;
import com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories.ProjectApplicationRepository;
import com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationsCommandServiceImpl implements ApplicationsCommandService {
    private final ProjectApplicationRepository applicationRepository;
    private final TeamRepository teamRepository;
    public ApplicationsCommandServiceImpl(ProjectApplicationRepository applicationRepository, TeamRepository teamRepository) { this.applicationRepository = applicationRepository; this.teamRepository = teamRepository; }

    public Optional<ProjectApplication> handle(ApplyToIdeaCommand command) {
        if (applicationRepository.existsByIdeaIdAndApplicantProfileId(command.ideaId(), command.applicantProfileId())) throw new IllegalArgumentException("The profile already applied to this idea");
        return Optional.of(applicationRepository.save(new ProjectApplication(command)));
    }
    public Optional<ProjectApplication> handle(AcceptApplicationCommand command) {
        var application = applicationRepository.findById(command.applicationId()).orElseThrow(() -> new ApplicationNotFoundException(command.applicationId()));
        if (!application.isPending()) throw new IllegalStateException("Only pending applications can be accepted");
        application.accept();
        var saved = applicationRepository.save(application);
        var team = teamRepository.findByIdeaId(application.getIdeaId()).orElseGet(() -> new Team(application.getIdeaId(), "Idea Team #" + application.getIdeaId()));
        team.addMember(application.getApplicantProfileId(), application.getRequestedRole());
        teamRepository.save(team);
        return Optional.of(saved);
    }
    public Optional<ProjectApplication> handle(RejectApplicationCommand command) {
        var application = applicationRepository.findById(command.applicationId()).orElseThrow(() -> new ApplicationNotFoundException(command.applicationId()));
        if (!application.isPending()) throw new IllegalStateException("Only pending applications can be rejected");
        application.reject();
        return Optional.of(applicationRepository.save(application));
    }
}
