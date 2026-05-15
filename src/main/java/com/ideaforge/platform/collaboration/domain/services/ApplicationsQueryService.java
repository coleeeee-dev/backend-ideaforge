package com.ideaforge.platform.collaboration.domain.services;

import com.ideaforge.platform.collaboration.domain.model.aggregates.ProjectApplication;
import com.ideaforge.platform.collaboration.domain.model.queries.*;

import java.util.List;

public interface ApplicationsQueryService {
    List<ProjectApplication> handle(GetApplicationsByIdeaIdQuery query);
    List<ProjectApplication> handle(GetApplicationsByApplicantIdQuery query);
}
