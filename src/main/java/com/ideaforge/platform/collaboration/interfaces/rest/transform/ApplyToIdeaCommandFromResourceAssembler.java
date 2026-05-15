package com.ideaforge.platform.collaboration.interfaces.rest.transform;

import com.ideaforge.platform.collaboration.domain.model.commands.ApplyToIdeaCommand;
import com.ideaforge.platform.collaboration.interfaces.rest.resources.ApplyToIdeaResource;

public class ApplyToIdeaCommandFromResourceAssembler { public static ApplyToIdeaCommand toCommandFromResource(Long ideaId, ApplyToIdeaResource r) { return new ApplyToIdeaCommand(ideaId, r.applicantProfileId(), r.requestedRole(), r.message()); } }
