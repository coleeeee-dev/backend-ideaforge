package com.ideaforge.platform.profiles.interfaces.rest.transform;

import com.ideaforge.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.ideaforge.platform.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler { public static CreateProfileCommand toCommandFromResource(CreateProfileResource r) { return new CreateProfileCommand(r.accountId(), r.firstName(), r.lastName(), r.headline(), r.bio(), r.avatarUrl(), r.experienceLevel(), r.skills(), r.interests()); } }
