package com.ideaforge.platform.ideas.interfaces.rest.transform;

import com.ideaforge.platform.ideas.domain.model.commands.UpdateIdeaStatusCommand;
import com.ideaforge.platform.ideas.interfaces.rest.resources.UpdateIdeaStatusResource;

public class UpdateIdeaStatusCommandFromResourceAssembler { public static UpdateIdeaStatusCommand toCommandFromResource(Long id, UpdateIdeaStatusResource r) { return new UpdateIdeaStatusCommand(id, r.status()); } }
