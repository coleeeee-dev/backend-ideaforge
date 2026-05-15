package com.ideaforge.platform.ideas.interfaces.rest.transform;

import com.ideaforge.platform.ideas.domain.model.commands.UpdateIdeaCommand;
import com.ideaforge.platform.ideas.interfaces.rest.resources.UpdateIdeaResource;

public class UpdateIdeaCommandFromResourceAssembler { public static UpdateIdeaCommand toCommandFromResource(Long id, UpdateIdeaResource r) { return new UpdateIdeaCommand(id, r.title(), r.shortDescription(), r.description(), r.problem(), r.solution(), r.category(), r.collaborationMode(), r.expectedCommitment()); } }
