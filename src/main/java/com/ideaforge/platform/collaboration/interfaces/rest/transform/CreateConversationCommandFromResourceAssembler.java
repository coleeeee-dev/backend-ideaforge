package com.ideaforge.platform.collaboration.interfaces.rest.transform;

import com.ideaforge.platform.collaboration.domain.model.commands.CreateConversationCommand;
import com.ideaforge.platform.collaboration.interfaces.rest.resources.CreateConversationResource;

public class CreateConversationCommandFromResourceAssembler { public static CreateConversationCommand toCommandFromResource(CreateConversationResource r) { return new CreateConversationCommand(r.ideaId(), r.projectApplicationId(), r.creatorProfileId(), r.applicantProfileId()); } }
