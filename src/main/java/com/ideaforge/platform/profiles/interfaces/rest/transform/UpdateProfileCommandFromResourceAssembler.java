package com.ideaforge.platform.profiles.interfaces.rest.transform;

import com.ideaforge.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.ideaforge.platform.profiles.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler { public static UpdateProfileCommand toCommandFromResource(Long id, UpdateProfileResource r) { return new UpdateProfileCommand(id, r.firstName(), r.lastName(), r.headline(), r.bio(), r.avatarUrl(), r.experienceLevel()); } }
