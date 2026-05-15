package com.ideaforge.platform.collaboration.interfaces.rest.transform;

import com.ideaforge.platform.collaboration.domain.model.aggregates.ProjectApplication;
import com.ideaforge.platform.collaboration.interfaces.rest.resources.ProjectApplicationResource;

public class ProjectApplicationResourceFromEntityAssembler { public static ProjectApplicationResource toResourceFromEntity(ProjectApplication e) { return new ProjectApplicationResource(e.getId(), e.getIdeaId(), e.getApplicantProfileId(), e.getRequestedRole(), e.getMessage(), e.getStatus().name()); } }
