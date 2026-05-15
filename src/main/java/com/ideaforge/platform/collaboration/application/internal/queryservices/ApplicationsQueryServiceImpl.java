package com.ideaforge.platform.collaboration.application.internal.queryservices;

import com.ideaforge.platform.collaboration.domain.model.aggregates.ProjectApplication;
import com.ideaforge.platform.collaboration.domain.model.queries.*;
import com.ideaforge.platform.collaboration.domain.services.ApplicationsQueryService;
import com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories.ProjectApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationsQueryServiceImpl implements ApplicationsQueryService {
    private final ProjectApplicationRepository repository;
    public ApplicationsQueryServiceImpl(ProjectApplicationRepository repository) { this.repository = repository; }
    public List<ProjectApplication> handle(GetApplicationsByIdeaIdQuery query) { return repository.findByIdeaId(query.ideaId()); }
    public List<ProjectApplication> handle(GetApplicationsByApplicantIdQuery query) { return repository.findByApplicantProfileId(query.applicantProfileId()); }
}
