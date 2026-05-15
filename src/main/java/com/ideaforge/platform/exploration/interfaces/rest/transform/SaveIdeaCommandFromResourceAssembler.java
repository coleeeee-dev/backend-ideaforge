package com.ideaforge.platform.exploration.interfaces.rest.transform;

import com.ideaforge.platform.exploration.domain.model.commands.SaveIdeaCommand;
import com.ideaforge.platform.exploration.interfaces.rest.resources.SaveIdeaResource;

public class SaveIdeaCommandFromResourceAssembler { public static SaveIdeaCommand toCommandFromResource(SaveIdeaResource r) { return new SaveIdeaCommand(r.profileId(), r.ideaId()); } }
