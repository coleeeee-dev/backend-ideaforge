package com.ideaforge.platform.ideas.interfaces.rest.transform;

import com.ideaforge.platform.ideas.domain.model.commands.UpdateIdeaStageCommand;
import com.ideaforge.platform.ideas.interfaces.rest.resources.UpdateIdeaStageResource;

public class UpdateIdeaStageCommandFromResourceAssembler { public static UpdateIdeaStageCommand toCommandFromResource(Long id, UpdateIdeaStageResource r) { return new UpdateIdeaStageCommand(id, r.stage()); } }
